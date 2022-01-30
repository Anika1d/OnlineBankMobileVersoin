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
import com.fefuproject.druzhbank.databinding.FragmentPaymentToPayBinding
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.payment.PaymentToPayFragment
import com.fefuproject.shared.account.domain.models.CardModel
import com.fefuproject.shared.account.domain.models.CheckModel
import com.fefuproject.shared.account.domain.usecase.GetCardsByNumberUseCase
import com.fefuproject.shared.account.domain.usecase.GetChecksByNumberUseCase
import com.fefuproject.shared.account.domain.usecase.PayUniversalUseCase
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class PaymentToPayViewModel @Inject constructor(
    private val payUniversalUseCase: PayUniversalUseCase,
    private val preferenceProvider: PreferenceProvider,
    private val getCardUseCase: GetCardsByNumberUseCase,
    private val getPayUseCase: GetChecksByNumberUseCase,
) : ViewModel() {
    lateinit var carduser: CardModel
    lateinit var payfriend: CheckModel
    lateinit var binding: FragmentPaymentToPayBinding
    lateinit var paymentToPayFragment: PaymentToPayFragment

    @SuppressLint("SetTextI18n")
    fun initData(
        my_card_number: String,
        fr_pay_number: String,
        _binding: FragmentPaymentToPayBinding,
        _paymentToPayFragment: PaymentToPayFragment
    ) {
        viewModelScope.launch {
            carduser = getCardUseCase.invoke(preferenceProvider.token!!, my_card_number)!![0]
            payfriend = getPayUseCase.invoke(preferenceProvider.token!!, fr_pay_number)!![0]
            binding = _binding
            paymentToPayFragment = _paymentToPayFragment
            binding.includeItemUserCard.cardUserNumber.text =
                carduser.number.take(4) + "*".repeat(8) + carduser.number.takeLast(4)
            binding.includeItemFriendCard.cardFriendNumber.text =
                "*".repeat(9) + payfriend.number.takeLast(4)
            binding.includeItemUserCard.cardUserValue.text = carduser.count + " руб."
            binding.includeItemFriendCard.cardFriend.text = "Счет получателя"
            binding.shP.stopShimmer()
            binding.shP.visibility=View.GONE //layout_constraintTop_toBottomOf
        //    binding.textInputValue
            binding.includeItemFriendCard.root.visibility=View.VISIBLE
            binding.includeItemUserCard.root.visibility=View.VISIBLE
            binding.transactionToCard.setOnClickListener {
                if (binding.valueEditText.text.toString() == "") {
                    Toast.makeText(
                        paymentToPayFragment.context,
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

        val builder = paymentToPayFragment.context!!.let { AlertDialog.Builder(it) }
        val alert = builder.create();
        val promptsView: View =
            LayoutInflater.from(paymentToPayFragment.context!!)
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
                        payfriend.number,
                        c,
                        0,
                        1,
                        preferenceProvider.token!!
                    )

                }

                if (f)
                    Toast.makeText(
                        paymentToPayFragment.activity!!.applicationContext,
                        "Вы перевели " + binding.valueEditText.text + " руб",
                        Toast.LENGTH_SHORT
                    ).show()
                else Toast.makeText(
                    paymentToPayFragment.activity!!.applicationContext,
                    "Перевод не удался проверейте, правильность введенных данных",
                    Toast.LENGTH_SHORT
                ).show()
                alert.dismiss()
            }
        }
        alert.show()

    }
}