package com.fefuproject.druzhbank.dirpayment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentPaymentBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.dirpayment.diradapters.*
import com.fefuproject.druzhbank.dirprofile.dircard.CardFragment
import com.fefuproject.shared.account.domain.models.CardModel
import com.fefuproject.shared.account.domain.models.CheckModel
import com.fefuproject.shared.account.domain.usecase.GetCardsUseCase
import com.fefuproject.shared.account.domain.usecase.GetChecksUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match


@AndroidEntryPoint
class PaymentFragment(val cardModel: CardModel) : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var getCardsUseCase: GetCardsUseCase

    @Inject
    lateinit var preferenceProvider: PreferenceProvider

    @Inject
    lateinit var getCheckUseCase: GetChecksUseCase
    lateinit var check_list: List<CheckModel>
    lateinit var card_list: List<CardModel>;
    private val adapterCard = PaymentCardAdapter(
        object : PaymentCardsActionListener {
            override fun onCardDetails(card: CardModel) {
                super.onCardDetails(card)
                if (!card.is_blocked)
                    activity!!.supportFragmentManager.beginTransaction().apply {
                        val visibleFragment =
                            activity!!.supportFragmentManager.fragments.firstOrNull { !isHidden }
                        visibleFragment?.let {
                            hide(visibleFragment)
                        }
                        replace(
                            R.id.fragmentContainerViewProfile,
                            PaymentToCardFragment(cardfriends =card,carduser=cardModel), "PaymentToCardFragment"
                        )
                        commit()
                    }
                else makeText(
                    this@PaymentFragment.context,
                    "Карта заблокирована",
                    Toast.LENGTH_LONG
                ).show()
            }
        })


    private val adapterPay = PaymentPayAdapter(
        object : PaymentPaysActionListener {
            override fun onPayDetails(pay: CheckModel) {
                super.onPayDetails(pay)
                Toast.makeText(
                    this@PaymentFragment.context,
                    "счета",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runBlocking {
            card_list = getCardsUseCase.invoke(preferenceProvider.token!!)!!
            check_list = getCheckUseCase.invoke(preferenceProvider.token!!)!!
        }

    }

    override fun onResume() {
        super.onResume()
        runBlocking {
            card_list = getCardsUseCase.invoke(preferenceProvider.token!!)!!
            check_list = getCheckUseCase.invoke(preferenceProvider.token!!)!!
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("FragmentBackPressedCallback", "ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterCard.addPaymentCardList(card_list)
        adapterPay.addPaymentPayList(check_list)
        binding.recycleViewPayment.adapter = adapterCard
        binding.recycleViewPayment.addItemDecoration(CommonItemSpaceDecoration(25))
        LinearLayoutManager(this.context).also {
            binding.recycleViewPayment.layoutManager = it
        }
        binding.paymentPay.setOnClickListener {
            with(binding) {
                paymentPay.setBackgroundTintList(
                    ContextCompat.getColorStateList(
                        activity?.applicationContext!!,
                        R.color.holo_green_dark
                    )
                );
                paymentPay.setTextColor(
                    ContextCompat.getColorStateList(
                        activity?.applicationContext!!,
                        R.color.white
                    )
                )
                paymentCard.setBackgroundTintList(
                    ContextCompat.getColorStateList(
                        activity?.applicationContext!!,
                        R.color.white
                    )
                );
                paymentCard.setTextColor(
                    ContextCompat.getColorStateList(
                        activity?.applicationContext!!,
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
                        activity?.applicationContext!!,
                        R.color.holo_green_dark
                    )
                );
                paymentCard.setTextColor(
                    ContextCompat.getColorStateList(
                        activity?.applicationContext!!,
                        R.color.white
                    )
                )
                recycleViewPayment.adapter = adapterCard
                paymentPay.setBackgroundTintList(
                    ContextCompat.getColorStateList(
                        activity?.applicationContext!!,
                        R.color.white
                    )
                )
                paymentPay.setTextColor(
                    ContextCompat.getColorStateList(
                        activity?.applicationContext!!,
                        R.color.holo_green_dark
                    )
                )
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(
            this@PaymentFragment,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager.beginTransaction().apply {
                        replace(
                            R.id.fragmentContainerViewProfile, CardFragment(cardModel),
                            "CardFragment"
                        )
                        commit()
                    }
                }
            })

    }
}