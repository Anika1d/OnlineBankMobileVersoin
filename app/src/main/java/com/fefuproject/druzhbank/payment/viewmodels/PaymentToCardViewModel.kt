package com.fefuproject.druzhbank.payment.viewmodels

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.payment.PaymentToCardFragment
import com.fefuproject.shared.account.domain.usecase.GetCardsByNumberUseCase
import com.fefuproject.shared.account.domain.usecase.PayUniversalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import com.fefuproject.druzhbank.databinding.FragmentPaymentToCardBinding
import com.fefuproject.shared.account.domain.models.CardModel
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class PaymentToCardViewModel @Inject constructor(
    private val payUniversalUseCase: PayUniversalUseCase,
    private val preferenceProvider: PreferenceProvider,
    private val getCardUseCase: GetCardsByNumberUseCase,
) : ViewModel() {
    lateinit var cardfriends: CardModel
    lateinit var carduser: CardModel
    lateinit var binding: FragmentPaymentToCardBinding
    lateinit var paymentToCardFragment: PaymentToCardFragment

    @SuppressLint("SetTextI18n")
    fun initData(
        myCardNumber: String,
        frCardNumber: String,
        _binding: FragmentPaymentToCardBinding,
        _paymentToCardFragment: PaymentToCardFragment
    ) {
        binding = _binding
        paymentToCardFragment = _paymentToCardFragment
        viewModelScope.launch {
            carduser = getCardUseCase.invoke(preferenceProvider.token!!, myCardNumber)!![0]
            cardfriends = getCardUseCase.invoke(preferenceProvider.token!!, frCardNumber)!![0]
            binding.includeItemUserCard.cardUserNumber.text =
                carduser.number.take(4) + "*".repeat(8) + carduser.number.takeLast(4)
            binding.includeItemFriendCard.cardFriendNumber.text =
                cardfriends.number.take(4) + "*".repeat(8) + cardfriends.number.takeLast(4)
            binding.includeItemUserCard.cardUserValue.text = carduser.count + " руб."
            binding.shP.stopShimmer()
            binding.shP.visibility=View.GONE
            binding.includeItemFriendCard.root.visibility=View.VISIBLE
            binding.includeItemUserCard.root.visibility=View.VISIBLE
            binding.transactionToCard.setOnClickListener {
                if (binding.valueEditText.text.toString() == "") {
                    Toast.makeText(
                        paymentToCardFragment.context,
                        "Вы ничего не ввели",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    AlertDialogTransaction()
                }
            }
        }
    }


    @SuppressLint("UseRequireInsteadOfGet", "SetTextI18n")
    private fun AlertDialogTransaction() { //создание диалога авторизации

        val builder = paymentToCardFragment.context!!.let { AlertDialog.Builder(it) }
        val alert = builder.create();
        val promptsView: View =
            LayoutInflater.from(paymentToCardFragment.context!!)
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
                viewModelScope.launch {
                var f = false;
                val c = binding.valueEditText.text.toString().toDoubleOrNull()
                if (c != null) {

                    f = payUniversalUseCase.invoke(
                        carduser.number,
                        cardfriends.number,
                        c,
                        0,
                        0,
                        preferenceProvider.token!!
                    )
                }
                if (f) {
                    Toast.makeText(
                        paymentToCardFragment.activity!!.applicationContext,
                        "Вы перевели " + binding.valueEditText.text + " руб",
                        Toast.LENGTH_SHORT
                    ).show()
                    alert.dismiss()
                } else Toast.makeText(
                    paymentToCardFragment.activity!!.applicationContext,
                    "Перевод не удался проверейте, правильность введенных данных",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        alert.show()

    }
}