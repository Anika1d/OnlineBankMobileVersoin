package com.fefuproject.druzhbank.dirpayment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentPaymentBinding
import com.fefuproject.druzhbank.databinding.FragmentPaymentToCardBinding
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.dirmainpayment.MainPaymentFragment
import com.fefuproject.druzhbank.dirprofile.dirfragmentprofile.ProfileMainFragment
import com.fefuproject.shared.account.domain.models.CardModel
import com.fefuproject.shared.account.domain.usecase.GetCardsUseCase
import com.fefuproject.shared.account.domain.usecase.PayUniversalUseCase
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PaymentToCardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class PaymentToCardFragment(val carduser: CardModel, val cardfriends: CardModel) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentPaymentToCardBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var payUniversalUseCase: PayUniversalUseCase

    @Inject
    lateinit var preferenceProvider: PreferenceProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPaymentToCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("FragmentBackPressedCallback", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.includeItemUserCard.cardUserNumber.text =
            carduser.number[0].toString() + carduser.number[1].toString() +
                    carduser.number[2].toString() + carduser.number[3].toString() + "****" +
                    carduser.number[carduser.number.length - 4].toString() + carduser.number[carduser.number.length - 3].toString() +
                    carduser.number[carduser.number.length - 2].toString() + carduser.number[carduser.number.length - 1].toString()
        binding.includeItemFriendCard.cardFriendNumber.text =
            cardfriends.number[0].toString() + cardfriends.number[1].toString() +
                    cardfriends.number[2].toString() + cardfriends.number[3].toString() + "****" +
                    cardfriends.number[cardfriends.number.length - 4].toString() + cardfriends.number[cardfriends.number.length - 3].toString() +
                    cardfriends.number[cardfriends.number.length - 2].toString() + cardfriends.number[cardfriends.number.length - 1].toString()
        activity?.onBackPressedDispatcher?.addCallback(
            this@PaymentToCardFragment,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager.beginTransaction().apply {
                        replace(
                            R.id.fragmentContainerViewProfile, MainPaymentFragment(),
                            "MainPaymentFragment"
                        )
                        commit()
                    }
                }
            })
        binding.transactionToCard.setOnClickListener {
            if (binding.valueEditText.text.toString() == "") {
                Toast.makeText(
                    this.context,
                    "Вы ничего не ввели",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                AlertDialogTransaction()
            }
        }

    }

    @SuppressLint("UseRequireInsteadOfGet", "SetTextI18n")
    private fun AlertDialogTransaction() { //создание диалога авторизации
        val builder = this@PaymentToCardFragment.context!!.let { AlertDialog.Builder(it) }
        val alert = builder.create();
        val promptsView: View =
            LayoutInflater.from(this@PaymentToCardFragment.context!!)
                .inflate(R.layout.dialog_transaction_card, null)
        alert.setView(promptsView)
        alert.setCancelable(false)
        val textView = promptsView.findViewById<TextView>(R.id.how_value)
        textView.text = textView.text.toString() + " " + binding.valueEditText.text + " руб"
        val bun_cancel = promptsView.findViewById<MaterialButton>(R.id.cancel_tr)
        bun_cancel.setOnClickListener {
            alert.dismiss()
        }
        val bun_ch_d = promptsView.findViewById<MaterialButton>(R.id.accept_tr)

        bun_ch_d.setOnClickListener {
            var f = false;
            runBlocking {
                f = payUniversalUseCase.invoke(
                    carduser.number,
                    cardfriends.number,
                    binding.valueEditText.text.toString().toDouble(),
                    0,
                    0,
                    preferenceProvider.token!!
                )
            }
            if (f)
                Toast.makeText(
                    activity!!.applicationContext,
                    "Вы перевели " + binding.valueEditText.text + " руб",
                    Toast.LENGTH_SHORT
                ).show()
            else Toast.makeText(
                activity!!.applicationContext,
                "Перевод не удался " + binding.valueEditText.text + " руб",
                Toast.LENGTH_SHORT
            ).show()
            alert.dismiss()
        }
        alert.show()
    }
}