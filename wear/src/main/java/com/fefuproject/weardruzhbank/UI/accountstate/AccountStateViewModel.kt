package com.fefuproject.weardruzhbank.UI.accountstate

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.shared.account.domain.entity.CardEvent
import com.fefuproject.shared.account.domain.entity.CardSummary
import com.fefuproject.shared.account.domain.usecase.GetCardEventsUseCase
import com.fefuproject.shared.account.domain.usecase.GetCardsSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AccountStateViewModel @Inject constructor(
    private val getCardsSummaryUseCase: GetCardsSummaryUseCase,
    private val getCardEventsUseCase: GetCardEventsUseCase
) :
    ViewModel() {

    val dataFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("RU"))

    private val _isRefreshing = MutableStateFlow(true)
    val isRefreshing get() = _isRefreshing.asStateFlow()

    private val _cardInfo = MutableStateFlow<List<CardSummary>?>(null)
    val cardInfo get() = _cardInfo.asStateFlow()

    private val _cardEvents = MutableStateFlow<List<CardEvent>?>(null)
    val cardEvents get() = _cardEvents.asStateFlow()

    init {
        reschedule(0)
    }

    private fun reschedule(card: Int) {
        viewModelScope.launch {
            _cardEvents.value = null
            delay(1000)
            _cardInfo.value = getCardsSummaryUseCase.invoke()
            if (cardInfo.value!!.isNotEmpty()) _cardEvents.value = getCardEventsUseCase.invoke(
                _cardInfo.value!![card].number
            )
            _isRefreshing.value = false
        }
    }

    fun refresh(card: Int) {
        _isRefreshing.value = true
        reschedule(card)
    }
}