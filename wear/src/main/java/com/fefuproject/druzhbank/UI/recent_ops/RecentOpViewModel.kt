package com.fefuproject.druzhbank.UI.recent_ops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.shared.account.domain.models.HistoryInstrumentModel
import com.fefuproject.shared.account.domain.usecase.GetAllInstrumentHistoryUseCase
import com.fefuproject.shared.account.domain.usecase.PayUniversalUseCase
import com.fefuproject.druzhbank.di.PreferenceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.absoluteValue

@HiltViewModel
class RecentOpViewModel @Inject constructor(
    private val preferenceProvider: PreferenceProvider,
    private val getAllInstrumentHistoryUseCase: GetAllInstrumentHistoryUseCase,
    private val payUniversalUseCase: PayUniversalUseCase
) : ViewModel() {

    private val recentOpPageSize = 10

    private var _cardEvents = MutableStateFlow<List<HistoryInstrumentModel>?>(null)
    val cardEvents get() = _cardEvents.asStateFlow()
    private val _isRefreshing = MutableStateFlow(true)
    val isRefreshing get() = _isRefreshing.asStateFlow()

    init {
        reschedule()
    }

    private fun reschedule() {
        viewModelScope.launch {
            _cardEvents.value = null
            _cardEvents.value = getAllInstrumentHistoryUseCase(
                preferenceProvider.token!!,
                10,
                recentOpPageSize
            )?.historyList
            _isRefreshing.value = false
        }
    }

    fun repeatPayment(payment: HistoryInstrumentModel) {
        val cnt = payment.count.toDouble()
        if (isRefreshing.value || cnt > 0) return
        viewModelScope.launch {
            _isRefreshing.value = true
            payUniversalUseCase.invoke(
                payment.source,
                payment.dest,
                cnt.absoluteValue,
                payment.sourceType,
                payment.destType,
                preferenceProvider.token!!
            )
            refresh()
        }
    }

    fun refresh() {
        _isRefreshing.value = true
        reschedule()
    }
}