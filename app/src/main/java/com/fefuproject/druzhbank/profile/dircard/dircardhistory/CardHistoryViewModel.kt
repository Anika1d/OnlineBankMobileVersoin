package com.fefuproject.druzhbank.profile.dircard.dircardhistory

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fefuproject.druzhbank.databinding.FragmentCardHistoryBinding
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.shared.account.domain.models.HistoryInstrumentModel
import com.fefuproject.shared.account.domain.models.PageListModel
import com.fefuproject.shared.account.domain.usecase.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CardHistoryViewModel @Inject constructor(
    private val preferenceProvider: PreferenceProvider,
    private val getCardHistoryUseCase: GetCardHistoryUseCase
) : ViewModel() {
    private var adapter = CardHistoryAdapter()
    lateinit var cardHistoryList: PageListModel<HistoryInstrumentModel>
    fun initData(
        number: String,
        binding: FragmentCardHistoryBinding,
        cardHistoryFragment: CardHistoryFragment
    ) {
        viewModelScope.launch {
            cardHistoryList =
                getCardHistoryUseCase.invoke(number, preferenceProvider.token!!, 1, 10)!!
            binding.recycleViewCardsHistory.adapter = adapter
            LinearLayoutManager(cardHistoryFragment.context).also {
                binding.recycleViewCardsHistory.layoutManager = it
            }
               adapter.addCardHistoryList(cardHistoryList.historyList)
            binding.shimmerCardHistory.visibility= View.GONE
            binding.recycleViewCardsHistory.visibility=View.VISIBLE }
    }
}