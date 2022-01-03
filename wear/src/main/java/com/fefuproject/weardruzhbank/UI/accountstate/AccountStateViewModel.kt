package com.fefuproject.weardruzhbank.UI.accountstate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.shared.account.domain.models.CardModel
import com.fefuproject.shared.account.domain.models.HistoryInstrumentModel
import com.fefuproject.shared.account.domain.usecase.BlockCardUseCase
import com.fefuproject.shared.account.domain.usecase.GetCardHistoryUseCase
import com.fefuproject.shared.account.domain.usecase.GetCardsSummaryUseCase
import com.fefuproject.weardruzhbank.Model.PreferenceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountStateViewModel @Inject constructor(
    private val getCardsSummaryUseCase: GetCardsSummaryUseCase,
    private val getCardHistoryUseCase: GetCardHistoryUseCase,
    private val blockCardUseCase: BlockCardUseCase,
    private val preferenceProvider: PreferenceProvider
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(true)
    val isRefreshing get() = _isRefreshing.asStateFlow()

    private val _cardInfo = MutableStateFlow<List<CardModel>?>(null)
    val cardInfo get() = _cardInfo.asStateFlow()

    private val _cardEvents = MutableStateFlow<List<HistoryInstrumentModel>?>(null)
    val cardEvents get() = _cardEvents.asStateFlow()

    private val _selectedCard = MutableStateFlow(0)
    val selectedCard get() = _selectedCard.asStateFlow()

    private val _cardBeingBlocked = MutableStateFlow<CardModel?>(null)
    val cardBeingBlocked get() = _cardBeingBlocked.asStateFlow()

    init {
        reschedule(selectedCard.value)
    }

    private fun reschedule(card: Int) {
        viewModelScope.launch {
            _cardEvents.value = null
            _cardInfo.value = getCardsSummaryUseCase.invoke(preferenceProvider.token!!)
            if (cardInfo.value!!.isNotEmpty()) _cardEvents.value = getCardHistoryUseCase.invoke(
                number = _cardInfo.value!![card].number,
                token = preferenceProvider.token!!
            )
            _isRefreshing.value = false
        }
    }

    suspend fun blockCard() {
        if (cardBeingBlocked.value != null) return
        _cardBeingBlocked.value = cardInfo.value!![_selectedCard.value]
        blockCardUseCase.invoke(
            _cardBeingBlocked.value!!.number,
            preferenceProvider.token!!
        )
        _cardBeingBlocked.value = null
        reschedule(_selectedCard.value)
    }

    fun refresh(card: Int) {
        _selectedCard.value = card
        _isRefreshing.value = true
        reschedule(card)
    }
}