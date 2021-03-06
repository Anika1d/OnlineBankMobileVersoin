package com.fefuproject.druzhbank.mainpayment.paymentcontract

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.druzhbank.databinding.FragmentPaymentContractBinding
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.shared.account.domain.models.CardModel
import com.fefuproject.shared.account.domain.models.TemplateModel
import com.fefuproject.shared.account.domain.usecase.GetCardsUseCase
import com.fefuproject.shared.account.domain.usecase.GetTemplatesByIdUseCase
import com.fefuproject.shared.account.domain.usecase.PayUniversalUseCase
import com.fefuproject.shared.account.domain.usecase.SetTemplateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentContactViewModel @Inject constructor(
    private val useCase: GetTemplatesByIdUseCase,
    private val preferenceProvider: PreferenceProvider,
    private val getCardsUseCase: GetCardsUseCase,
    private val setTemplateUseCase: SetTemplateUseCase,
    private val payUniversalUseCase: PayUniversalUseCase,
) : ViewModel() {
    private lateinit var binding: FragmentPaymentContractBinding
    private lateinit var paymentContractFragment: PaymentContractFragment
    lateinit var card_list: List<CardModel>
    lateinit var template: TemplateModel
    fun makePayment(card: CardModel) {
        viewModelScope.launch {
            val res = payUniversalUseCase.invoke(
                card.number,
                binding.codeContractEditText.text.toString(),
                binding.codeValueEditText.text.toString().toDouble(),
                0,
                0,
                preferenceProvider.token!!
            )
            makeText(
                paymentContractFragment.context,
                if (res) "???????????? ?????????????? ????????????????" else "?????? ???????????????????? ?????????????? ?????????????????? ????????????...",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun initData(
        tmpTemplatesId: String?,
        tmpadd: String?,
        _binding: FragmentPaymentContractBinding,
        _paymentContractFragment: PaymentContractFragment
    ) {
        viewModelScope.launch {
            card_list = getCardsUseCase.invoke(preferenceProvider.token!!)!!
            binding = _binding
            paymentContractFragment = _paymentContractFragment
            binding.textCard.text = "???????????????? ??????????"
            val nameCard = ArrayList<String>(card_list.size)
            var tmpCard: CardModel? = null
            for (card in card_list) {
                val string = card.number[0].toString() + card.number[1].toString() +
                        card.number[2].toString() + card.number[3].toString() + "****" +
                        card.number[card.number.length - 4].toString() + card.number[card.number.length - 3].toString() +
                        card.number[card.number.length - 2].toString() + card.number[card.number.length - 1].toString()
                nameCard.add(string)
            }
            binding.spinner.setSelection(0, false)
            binding.spinner.adapter = ArrayAdapter(
                paymentContractFragment.requireActivity().applicationContext,
                R.layout.simple_list_item_1,
                nameCard
            )
            binding.spinner.onItemSelectedListener = object : AdapterView.OnItemClickListener,
                AdapterView.OnItemSelectedListener {
                override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    TODO("Not yet implemented")
                }

                @SuppressLint("SetTextI18n")
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    binding.textCard.text = "?????????????? ?????????? " + nameCard[position]
                    tmpCard = card_list[position]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    binding.textCard.text = "???????????????? ??????????"
                }
            }
            binding.materialButton.setOnClickListener {
                makePayment(tmpCard!!)
            }
            if (tmpTemplatesId != null) {
                template =
                    useCase.invoke(preferenceProvider.token!!, tmpTemplatesId.toInt())?.get(0)!!
                binding.codeContractEditText.setText(template.dest)
                binding.codeValueEditText.setText(template.sum.toString())
                binding.spinner.setAutofillHints(template.source)
            }
            if (tmpadd != null) {
                with(binding) {
                    textInputName.visibility = View.VISIBLE
                    materialButton.text = "C????????????????"
                    materialButton.isClickable = true
                    materialButton.setOnClickListener {
                        if (codeContractEditText.text.toString() != "" &&
                            codeValueEditText.text.toString() != "" &&
                            nameContractEditText.text.toString() != ""
                        ) {
                            ///TODO()?????????? ??????????
                            viewModelScope.launch {
                                setTemplateUseCase.invoke(
                                    preferenceProvider.token!!,
                                    tmpCard!!.number,
                                    codeContractEditText.text.toString(),
                                    0,
                                    0, nameContractEditText.text.toString(),
                                    codeValueEditText.text.toString().toDouble()
                                )
                                makeText(
                                    paymentContractFragment.context,
                                    "???????????? ????????????????",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            makeText(
                                paymentContractFragment.context,
                                "?????????????????? ?????? ????????",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                }
            }

        }
    }
}