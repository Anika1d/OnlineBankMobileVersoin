package com.fefuproject.druzhbank.UI.accountstate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.shared.account.domain.models.CardModel
import com.fefuproject.shared.account.domain.models.HistoryInstrumentModel
import com.fefuproject.shared.account.domain.usecase.BlockCardUseCase
import com.fefuproject.shared.account.domain.usecase.GetCardHistoryUseCase
import com.fefuproject.shared.account.domain.usecase.GetCardsUseCase
import com.fefuproject.druzhbank.di.PreferenceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountStateViewModel @Inject constructor(
    private val getCardUseCase: GetCardsUseCase,
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
            val buf = getCardUseCase.invoke(preferenceProvider.token!!)?.toMutableList()
            if (cardInfo.value!!.isNotEmpty()) _cardEvents.value = getCardHistoryUseCase.invoke(
                number = _cardInfo.value!![card].number,
                token = preferenceProvider.token!!,
                10
            )
            buf?.removeAll { cardModel -> cardModel.is_blocked }
            if (buf != null) if (buf.size > 3) buf.take(3)
            _cardInfo.value = buf
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