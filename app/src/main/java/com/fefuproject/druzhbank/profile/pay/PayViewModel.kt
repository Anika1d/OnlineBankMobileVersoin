package com.fefuproject.druzhbank.profile.pay

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentPayBinding
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.profile.pay.historypay.HistoryPayFragment
import com.fefuproject.shared.account.domain.enums.InstrumetType
import com.fefuproject.shared.account.domain.models.CheckModel
import com.fefuproject.shared.account.domain.usecase.EditInstrumentNameUseCase
import com.fefuproject.shared.account.domain.usecase.GetChecksByNumberUseCase
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PayViewModel @Inject constructor(
    private val preferenceProvider: PreferenceProvider,
    private val getCheckUseCase: GetChecksByNumberUseCase,
    private val editInstrumentNameUseCase: EditInstrumentNameUseCase
) : ViewModel() {

    private lateinit var checkModel: CheckModel
    private lateinit var binding: FragmentPayBinding
    private lateinit var payFragment: PayFragment


    @SuppressLint("SetTextI18n")
    fun initData(tmpNumberPay: String, _binding: FragmentPayBinding, _payFragment: PayFragment) {
        binding = _binding
        payFragment = _payFragment
        viewModelScope.launch {

            checkModel = getCheckUseCase(preferenceProvider.token!!, tmpNumberPay)!![0]
            binding.includePay.fullNumberPay.text = "*********" + checkModel.number.takeLast(4)
            binding.includePay.valuePay.text = checkModel.count + " руб."
            binding.includePay.namePay.text = checkModel.name
            binding.shimmerFrameLayout.stopShimmer()
            binding.shimmerFrameLayout.visibility=View.GONE
            binding.includePay.root.visibility=View.VISIBLE
            binding.historyCardButton.visibility=View.VISIBLE
            binding.renameCard.visibility=View.VISIBLE
            binding.historyCardButton.setOnClickListener {
                payFragment.parentFragmentManager.beginTransaction().apply {
                    replace(
                        R.id.fragmentContainerViewProfile,
                        HistoryPayFragment(checkModel), "PayHistory"
                    )
                    commit()
                }
            }

            binding.renameCard.setOnClickListener {
                AlertDialogRenamePay()
            }

        }
    }

    private fun AlertDialogRenamePay() {
        val builder = AlertDialog.Builder(payFragment.requireActivity())
        val alert = builder.create();
        val promptsView: View =
            LayoutInflater.from(payFragment.requireActivity())
                .inflate(R.layout.dialog_rename_card, null)
        alert.setView(promptsView)
        alert.setCancelable(false)
        val bun_cancel = promptsView.findViewById<MaterialButton>(R.id.cancel_button_rename)
        bun_cancel.setOnClickListener {
            Toast.makeText(
                payFragment.requireActivity().applicationContext,
                "Операция отменена",
                Toast.LENGTH_SHORT
            ).show()
            alert.dismiss()
        }
        val newName = promptsView.findViewById<EditText>(R.id.name_card_edit_text)
        val bun_bl_d = promptsView.findViewById<MaterialButton>(R.id.rename_button_card)
        bun_bl_d.setOnClickListener {
            viewModelScope.launch {
                if (newName.text.toString() != "") {
                    Toast.makeText(
                        payFragment.requireActivity().applicationContext,
                        "Вы переименовали счёт",
                        Toast.LENGTH_SHORT
                    ).show()

                    editInstrumentNameUseCase.invoke(
                        preferenceProvider.token!!,
                        newName.text.toString(),
                        checkModel.number,
                        InstrumetType.Check,
                    )
                    binding.includePay.namePay.text = newName.text
                    alert.dismiss()
                } else {

                    Toast.makeText(
                        payFragment.requireActivity().applicationContext,
                        "Вы не ввели новое имя карты",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        alert.show()

    }
}