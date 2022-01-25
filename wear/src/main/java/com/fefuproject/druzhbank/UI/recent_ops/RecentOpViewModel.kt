package com.fefuproject.druzhbank.UI.recent_ops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.shared.account.domain.models.HistoryInstrumentModel
import com.fefuproject.shared.account.domain.usecase.GetAllInstrumentHistoryUseCase
import com.fefuproject.shared.account.domain.usecase.PayUniversalUseCase
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.extensions.addNullList
import com.fefuproject.druzhbank.extensions.mergeFromList
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

    private val pageSize = 5

    private var currentPage = 0
    private var _cardEvents = MutableStateFlow<List<HistoryInstrumentModel?>>(listOf())
    val cardEvents get() = _cardEvents.asStateFlow()
    private val _isRefreshing = MutableStateFlow(true)
    val isRefreshing get() = _isRefreshing.asStateFlow()

    var isPageLoading = false

    init {
        refresh()
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

    fun loadNextPage() {
        if(isPageLoading) return
        currentPage++
        viewModelScope.launch {
            isPageLoading = true
            val originalList = _cardEvents.value //caching in case of failure
            _cardEvents.value = _cardEvents.value.addNullList(pageSize)
            val temp = getAllInstrumentHistoryUseCase(
                preferenceProvider.token!!,
                currentPage,
                pageSize
            )?.historyList
            if (temp != null) _cardEvents.value = _cardEvents.value.mergeFromList(temp, pageSize)
            else _cardEvents.value = originalList
            _isRefreshing.value = false
            isPageLoading = false
        }
    }

    fun refresh() {
        _isRefreshing.value = true
        currentPage = 0
        loadNextPage()
    }
}