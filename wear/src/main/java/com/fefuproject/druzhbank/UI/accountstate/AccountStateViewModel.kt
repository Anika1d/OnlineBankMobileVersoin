package com.fefuproject.druzhbank.UI.accountstate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.shared.account.domain.models.CardModel
import com.fefuproject.shared.account.domain.models.HistoryInstrumentModel
import com.fefuproject.shared.account.domain.usecase.BlockCardUseCase
import com.fefuproject.shared.account.domain.usecase.GetCardHistoryUseCase
import com.fefuproject.shared.account.domain.usecase.GetCardsUseCase
import com.fefuproject.druzhbank.di.PreferenceProvider
import libs.addNullList
import libs.mergeFromList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
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

    private val pageSize = 5

    private val _isRefreshing = MutableStateFlow(true)
    val isRefreshing get() = _isRefreshing.asStateFlow()

    private val _cardInfo = MutableStateFlow<List<CardModel>?>(null)
    val cardInfo get() = _cardInfo.asStateFlow()

    private val _cardEvents = MutableStateFlow<List<HistoryInstrumentModel?>>(listOf())
    val cardEvents get() = _cardEvents.asStateFlow()

    private val _selectedCard = MutableStateFlow(0)
    val selectedCard get() = _selectedCard.asStateFlow()

    private val _cardBeingBlocked = MutableStateFlow<CardModel?>(null)
    val cardBeingBlocked get() = _cardBeingBlocked.asStateFlow()

    var currentPage = 0
    var isPageLoading = false

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            currentPage = 0
            var buf = getCardUseCase.invoke(preferenceProvider.token!!)?.toMutableList()
            buf?.removeAll { cardModel -> cardModel.is_blocked }
            if (buf != null) if (buf.size > 4) buf = buf.take(4).toMutableList()
            _cardInfo.value = buf
            _cardEvents.value = listOf()
            if (cardInfo.value!!.isNotEmpty()) loadNextHistoryPage(this)
            _isRefreshing.value = false
        }
    }

    fun loadNextHistoryPage(coroutineScope: CoroutineScope = viewModelScope) {
        if (isPageLoading) return
        if (currentPage == -1) return // we've reached the lowest point
        coroutineScope.launch {
            isPageLoading = true
            val originalList = _cardEvents.value
            _cardEvents.value = _cardEvents.value.addNullList(pageSize)
            currentPage++
            val temp = getCardHistoryUseCase.invoke(
                number = _cardInfo.value!![_selectedCard.value].number,
                token = preferenceProvider.token!!,
                currentPage,
                pageSize
            )?.historyList
            if (temp != null) _cardEvents.value = _cardEvents.value.mergeFromList(temp, pageSize)
            else _cardEvents.value = originalList
            if (cardEvents.value.size - originalList.size < pageSize) currentPage = -1
            isPageLoading = false
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
        refresh()
    }

    fun changeCard(newCard: Int) {
        _selectedCard.value = newCard
        refresh()
    }
}