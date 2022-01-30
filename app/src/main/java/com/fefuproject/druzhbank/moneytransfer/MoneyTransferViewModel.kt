package com.fefuproject.druzhbank.moneytransfer

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentMoneyTransferBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.moneytransfer.adapter.CardTransferAdapter
import com.fefuproject.druzhbank.moneytransfer.adapter.CardsActionListenerT
import com.fefuproject.druzhbank.payment.PaymentToCardFragment
import com.fefuproject.shared.account.domain.models.CardModel
import com.fefuproject.shared.account.domain.usecase.GetCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoneyTransferViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase,
    private val preferenceProvider: PreferenceProvider
) : ViewModel() {
    lateinit var tmpNumberCard: String
    lateinit var binding: FragmentMoneyTransferBinding
    lateinit var transferFragment: MoneyTransferFragment
    val adapter = CardTransferAdapter(
        object : CardsActionListenerT {
            override fun onCardDetails(card: CardModel) {
                super.onCardDetails(card)
                val bundle = Bundle()
                bundle.putString("myCard", tmpNumberCard)
                bundle.putString("frCard", card.number)
                bundle.putString("parentFragment","Transfer")
                val fr = PaymentToCardFragment()
                fr.arguments = bundle
                if (!card.is_blocked)
                    transferFragment.requireActivity().supportFragmentManager.beginTransaction()
                        .apply {
                            replace(
                                R.id.fragmentContainerViewProfile,
                                fr, "PaymentToCardFragment"
                            )
                            commit()
                        }
                else Toast.makeText(
                    transferFragment.context,
                    "Карта заблокирована",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    lateinit var card_list: List<CardModel>;
    fun initData(
        _tmpNumberCard: String,
        _binding: FragmentMoneyTransferBinding,
        _moneyTransferFragment: MoneyTransferFragment
    ) {
        tmpNumberCard = _tmpNumberCard
        binding = _binding
        transferFragment = _moneyTransferFragment
        viewModelScope.launch {
            card_list = getCardsUseCase.invoke(preferenceProvider.token!!)!!
            LinearLayoutManager(transferFragment.context).also {
                binding.recycleViewPayment.layoutManager = it
            }
            adapter.addListCard(card_list)
            binding.recycleViewPayment.addItemDecoration(CommonItemSpaceDecoration(25))
            binding.recycleViewPayment.adapter = adapter
            binding.shimmerCardPayment.stopShimmer()
            binding.shimmerCardPayment.visibility = View.GONE
            binding.recycleViewPayment.visibility = View.VISIBLE
        }
    }

}