package com.fefuproject.druzhbank.dirpayment

import android.annotation.SuppressLint
import android.graphics.ColorSpace
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentPaymentBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import com.fefuproject.druzhbank.dirpayment.diradapters.*
import com.fefuproject.druzhbank.dirprofile.dircard.CardFragment
import com.fefuproject.druzhbank.dirprofile.dircard.Cards
import com.fefuproject.druzhbank.dirprofile.dirfragmentprofile.ProfileMainFragment
import com.fefuproject.druzhbank.dirprofile.dirpay.Pays

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PaymentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PaymentFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    private val adapterCard = PaymentCardAdapter(
        object : PaymentCardsActionListener {
        override fun onCardDetails(card: Cards) {
            super.onCardDetails(card)
            activity!!.supportFragmentManager.beginTransaction().apply {
                val visibleFragment =
                    activity!!.supportFragmentManager.fragments.firstOrNull { !isHidden }
                visibleFragment?.let {
                    hide(visibleFragment)
                }
                replace(
                    R.id.fragmentContainerViewProfile,
                    PaymentToCardFragment(), "PaymentToCardFragment"
                )
                commit()
            }
        }
    })


    private val adapterPay = PaymentPayAdapter(
        object : PaymentPaysActionListener {
            override fun onPayDetails(pay: Pays) {
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
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private val cards = Cards(
        typeCard = "Кредитка",
        valueCard = "2140000 рублей", whatBank = "mir",
        numberCard = "2145 1245 **** ****", isBlocked = false
    )

    private val cards1 = Cards(
        typeCard = "Зарплатная ",
        valueCard = "40000 рублей", whatBank = "mir",
        numberCard = "3333 9999 **** ****", isBlocked = false
    )


    private val pays = Pays(
        namePay = "Пенсия", valuePay = "12000 рублей", numberPay = "****9999"
    )
    private val pays1 = Pays(
        namePay = "Накопления", valuePay = "912000 рублей", numberPay = "****9888"
    )

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
        adapterCard.addPaymentCard(cards)
        adapterCard.addPaymentCard(cards1)
        adapterCard.addPaymentCard(cards)
        adapterCard.addPaymentCard(cards1)
        adapterCard.addPaymentCard(cards)
        adapterPay.addPaymentPay(pays)
        adapterPay.addPaymentPay(pays1)
        adapterPay.addPaymentPay(pays)
        adapterPay.addPaymentPay(pays1)
        adapterPay.addPaymentPay(pays)
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
                            R.id.fragmentContainerViewProfile, ProfileMainFragment(),
                            "FragmentProfileMain"
                        )
                        commit()
                    }
                }
            })

    }
}