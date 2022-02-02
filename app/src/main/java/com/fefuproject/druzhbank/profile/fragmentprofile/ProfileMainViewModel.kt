package com.fefuproject.druzhbank.profile.fragmentprofile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentProfileMainBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.profile.credit.CreditsAdapter
import com.fefuproject.druzhbank.profile.dircard.CardFragment
import com.fefuproject.druzhbank.profile.dircard.CardsActionListener
import com.fefuproject.druzhbank.profile.dircard.CardsAdapter
import com.fefuproject.druzhbank.profile.pay.PayFragment
import com.fefuproject.druzhbank.profile.pay.PaysActionListener
import com.fefuproject.druzhbank.profile.pay.PaysAdapter
import com.fefuproject.shared.account.domain.models.CardModel
import com.fefuproject.shared.account.domain.models.CheckModel
import com.fefuproject.shared.account.domain.models.CreditModel
import com.fefuproject.shared.account.domain.usecase.GetCardsUseCase
import com.fefuproject.shared.account.domain.usecase.GetChecksUseCase
import com.fefuproject.shared.account.domain.usecase.GetCreditsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileMainViewModel @Inject constructor(
    private val preferenceProvider: PreferenceProvider,
    private val getCheckUseCase: GetChecksUseCase,
    private val getCreditsUseCase: GetCreditsUseCase,
    private val getCardsUseCase: GetCardsUseCase,
) : ViewModel() {

    private var cardsAdapter = CardsAdapter(
        object : CardsActionListener {
            @SuppressLint("UseRequireInsteadOfGet")
            override fun onCardDetails(card: CardModel) {
                val bundle = Bundle()
                bundle.putString("myArg", card.number)
                val fr = CardFragment()
                fr.arguments = bundle
                profileMainFragment.activity!!.supportFragmentManager.beginTransaction().apply {
                    replace(
                        R.id.fragmentContainerViewProfile,
                        fr, "CardFragment"
                    )
                    commit()
                }
            }
        }

    )
    private val paysAdapter = PaysAdapter(
        object : PaysActionListener {
            @SuppressLint("UseRequireInsteadOfGet")
            override fun onPayDetails(pay: CheckModel) {
                super.onPayDetails(pay)
                val bundle = Bundle()
                bundle.putString("myArg", pay.number)
                val fr = PayFragment()
                fr.arguments = bundle
                profileMainFragment.activity!!.supportFragmentManager.beginTransaction().apply {

                    replace(
                        R.id.fragmentContainerViewProfile,
                        fr, "PayFragment"
                    )
                    commit()
                }
            }
        }
    )

    private val creditsAdapter = CreditsAdapter()

    private lateinit var card_list: List<CardModel>
    private lateinit var credits_list: List<CreditModel>
    private lateinit var check_list: List<CheckModel>
    private lateinit var binding: FragmentProfileMainBinding
    private lateinit var profileMainFragment: ProfileMainFragment
    fun initData(_binding: FragmentProfileMainBinding, _profileMainFragment: ProfileMainFragment) {
        binding = _binding
        profileMainFragment = _profileMainFragment
        viewModelScope.launch {
            card_list = getCardsUseCase.invoke(preferenceProvider.token!!)!!
            credits_list = getCreditsUseCase.invoke(preferenceProvider.token!!)!!
            check_list = getCheckUseCase.invoke(preferenceProvider.token!!)!!
            val recyclerView: RecyclerView = binding.recycleViewCards
            LinearLayoutManager(profileMainFragment.context).also {
                binding.recycleViewCards.layoutManager = it
            }
            cardsAdapter.addListCard(card_list)
            recyclerView.adapter = cardsAdapter
            initDataRec()
            creditsAdapter.addCreditList(credits_list)
            paysAdapter.addPayList(check_list)
            binding.shimmerCard.stopShimmer()
            binding.shimmerCredits.stopShimmer()
            binding.shimmerPay.stopShimmer()
            binding.textView6.visibility= View.GONE
            binding.textView7.visibility= View.GONE
            binding.textView9.visibility= View.GONE
            binding.shimmerCard.visibility=View.GONE
            binding.shimmerPay.visibility=View.GONE
            binding.shimmerCredits.visibility=View.GONE
            binding.csv.visibility=View.VISIBLE
        }
    }

    private fun initDataRec() {
        binding.apply {

            LinearLayoutManager(profileMainFragment.context).also {
                recycleViewCredits.layoutManager = it
            }
            recycleViewCredits.adapter = creditsAdapter
            LinearLayoutManager(profileMainFragment.context).also {
                recycleViewPay.layoutManager = it
            }
            recycleViewPay.addItemDecoration(CommonItemSpaceDecoration(15))
            recycleViewCredits.addItemDecoration(CommonItemSpaceDecoration(15))
            recycleViewCards.addItemDecoration(CommonItemSpaceDecoration(15))
            recycleViewPay.adapter = paysAdapter
        }
    }
}