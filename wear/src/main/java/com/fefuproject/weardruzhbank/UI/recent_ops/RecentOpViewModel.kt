package com.fefuproject.weardruzhbank.UI.recent_ops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.shared.account.domain.models.HistoryInstrumentModel
import com.fefuproject.shared.account.domain.usecase.GetAllInstrumentHistoryUseCase
import com.fefuproject.weardruzhbank.di.PreferenceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentOpViewModel @Inject constructor(
    private val preferenceProvider: PreferenceProvider,
    private val getAllInstrumentHistoryUseCase: GetAllInstrumentHistoryUseCase
) : ViewModel() {

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
            _cardEvents.value = getAllInstrumentHistoryUseCase(preferenceProvider.token!!, 10)
            _isRefreshing.value = false
        }
    }

    fun refresh() {
        _isRefreshing.value = true
        reschedule()
    }
}