package com.fefuproject.druzhbank.payment.viewmodels

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentPaymentBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.payment.PaymentFragment
import com.fefuproject.druzhbank.payment.PaymentToCardFragment
import com.fefuproject.druzhbank.payment.PaymentToPayFragment
import com.fefuproject.druzhbank.payment.adapters.PaymentCardAdapter
import com.fefuproject.druzhbank.payment.adapters.PaymentCardsActionListener
import com.fefuproject.druzhbank.payment.adapters.PaymentPayAdapter
import com.fefuproject.druzhbank.payment.adapters.PaymentPaysActionListener
import com.fefuproject.shared.account.domain.models.CardModel
import com.fefuproject.shared.account.domain.models.CheckModel
import com.fefuproject.shared.account.domain.usecase.GetCardsUseCase
import com.fefuproject.shared.account.domain.usecase.GetChecksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase,
    private val preferenceProvider: PreferenceProvider,
    private val getCheckUseCase: GetChecksUseCase,

    ) : ViewModel() {
    lateinit var check_list: List<CheckModel>
    lateinit var card_list: List<CardModel>
    lateinit var tmp_number_card: String
    private val adapterCard = PaymentCardAdapter(
        object : PaymentCardsActionListener {
            @SuppressLint("UseRequireInsteadOfGet")
            override fun onCardDetails(card: CardModel) {
                super.onCardDetails(card)
                val bundle = Bundle()
                bundle.putString("myCard", tmp_number_card)
                bundle.putString("frCard", card.number)
                bundle.putString("parentFragment","Payment")
                val fr = PaymentToCardFragment()
                fr.arguments = bundle
                if (!card.is_blocked)
                    paymentFragment.activity!!.supportFragmentManager.beginTransaction().apply {
                        replace(
                            R.id.fragmentContainerViewProfile,
                            fr,
                            "PaymentToCardFragment"
                        )
                        commit()
                    }
                else Toast.makeText(
                    paymentFragment.context,
                    "Карта заблокирована",
                    Toast.LENGTH_LONG
                ).show()
            }
        })


    private val adapterPay = PaymentPayAdapter(
        object : PaymentPaysActionListener {
            @SuppressLint("UseRequireInsteadOfGet")
            override fun onPayDetails(pay: CheckModel) {
                super.onPayDetails(pay)
                val bundle = Bundle()
                bundle.putString("myCard", tmp_number_card)
                bundle.putString("frPay", pay.number)
                val fr = PaymentToPayFragment()
                fr.arguments = bundle
                paymentFragment.activity!!.supportFragmentManager.beginTransaction().apply {
                    replace(
                        R.id.fragmentContainerViewProfile,
                        fr,
                        "PaymentToCardFragment"
                    )
                    commit()
                }
            }
        }
    )
    lateinit var paymentFragment: PaymentFragment
    lateinit var binding: FragmentPaymentBinding

    @SuppressLint("UseRequireInsteadOfGet")
    fun initData(
        number: String,
        _binding: FragmentPaymentBinding,
        _paymentFragment: PaymentFragment
    ) {
        tmp_number_card = number
        binding = _binding
        paymentFragment = _paymentFragment
        viewModelScope.launch {
            card_list = getCardsUseCase.invoke(preferenceProvider.token!!)!!
            for (i in 0..card_list.size) {
                if (card_list[i].number == tmp_number_card) {
                    val c = card_list.toMutableList()
                    c.removeAt(i)
                    card_list = c.toList()
                    break
                }
            }
            check_list = getCheckUseCase.invoke(preferenceProvider.token!!)!!
            adapterCard.addPaymentCardList(card_list)
            adapterPay.addPaymentPayList(check_list)
            binding.recycleViewPayment.adapter = adapterCard
            binding.recycleViewPayment.addItemDecoration(CommonItemSpaceDecoration(25))
            binding.paymentPay.isClickable = true
            LinearLayoutManager(paymentFragment.context).also {
                binding.recycleViewPayment.layoutManager = it
            }
            binding.paymentPay.setOnClickListener {
                with(binding) {
                    paymentPay.setBackgroundTintList(
                        ContextCompat.getColorStateList(
                            paymentFragment.activity?.applicationContext!!,
                            R.color.holo_green_dark
                        )
                    );
                    paymentPay.setTextColor(
                        ContextCompat.getColorStateList(
                            paymentFragment.activity?.applicationContext!!,
                            R.color.white
                        )
                    )
                    paymentCard.setBackgroundTintList(
                        ContextCompat.getColorStateList(
                            paymentFragment.activity?.applicationContext!!,
                            R.color.white
                        )
                    );
                    paymentCard.setTextColor(
                        ContextCompat.getColorStateList(
                            paymentFragment.activity?.applicationContext!!,
                            R.color.holo_green_dark
                        )
                    )
                    recycleViewPayment.adapter = adapterCard

                    recycleViewPayment.adapter = adapterPay
                }
            }
            binding.paymentCard.setOnClickListener {
                with(binding) {
                    paymentCard.setBackgroundTintList(
                        ContextCompat.getColorStateList(
                            paymentFragment.activity?.applicationContext!!,
                            R.color.holo_green_dark
                        )
                    );
                    paymentCard.setTextColor(
                        ContextCompat.getColorStateList(
                            paymentFragment.activity?.applicationContext!!,
                            R.color.white
                        )
                    )
                    recycleViewPayment.adapter = adapterCard
                    paymentPay.setBackgroundTintList(
                        ContextCompat.getColorStateList(
                            paymentFragment.activity?.applicationContext!!,
                            R.color.white
                        )
                    )
                    paymentPay.setTextColor(
                        ContextCompat.getColorStateList(
                            paymentFragment.activity?.applicationContext!!,
                            R.color.holo_green_dark
                        )
                    )
                }
            }
            binding.shimmerCardPayment.visibility = View.GONE
            binding.recycleViewPayment.visibility = View.VISIBLE
        }
    }
}