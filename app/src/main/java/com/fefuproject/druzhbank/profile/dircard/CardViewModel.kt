package com.fefuproject.druzhbank.profile.dircard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentCardBinding
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.shared.account.domain.enums.InstrumetType
import com.fefuproject.shared.account.domain.models.CardModel
import com.fefuproject.shared.account.domain.usecase.BlockCardUseCase
import com.fefuproject.shared.account.domain.usecase.EditInstrumentNameUseCase
import com.fefuproject.shared.account.domain.usecase.GetCardsByNumberUseCase
import com.fefuproject.shared.account.domain.usecase.UnblockCardUseCase
import com.fefuproject.shared.account.util.Util
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val preferenceProvider: PreferenceProvider,
    private val getCardUseCase: GetCardsByNumberUseCase,
    private val useCaseBlockCard: BlockCardUseCase,
    private val useCaseUnblockCard: UnblockCardUseCase,
    private val editInstrumentNameUseCase: EditInstrumentNameUseCase,
) : ViewModel(
) {
    lateinit var card: CardModel

    fun initCard(number: String, binding: FragmentCardBinding) {
        viewModelScope.launch {
            card = getCardUseCase.invoke(preferenceProvider.token!!, number)!![0]
            if (card.is_blocked) {
                blockingCard(binding = binding)
            } else {
                unBlockingCard(binding = binding)
            }
            val typeCard = Util.getCardIssuer(cardNumber = card.number)
            with(binding.include) {
                when (typeCard) {
                    com.fefuproject.shared.account.util.CardType.MIR -> {
                        cardImage.setImageResource(com.fefuproject.druzhbank.R.drawable.ic_mir)
                    }
                    com.fefuproject.shared.account.util.CardType.Mastercard -> {
                        cardImage.setImageResource(com.fefuproject.druzhbank.R.drawable.ic_mastercard)
                    }
                    com.fefuproject.shared.account.util.CardType.VISA -> {
                        cardImage.setImageResource(com.fefuproject.druzhbank.R.drawable.ic_visa)
                    }
                }
                typecard.text = card.name
                balansCard.text = card.count + " руб."
                numberCard.text = card.number.take(4) + "*".repeat(8) + card.number.takeLast(4)
            }
            binding.shimmerCard.stopShimmer()
            binding.shimmerCard.visibility = View.GONE
            binding.include.root.visibility = View.VISIBLE
        }


    }

    fun setBlockedCard(b: Boolean, number: String) {
        viewModelScope.launch {
            if (b)
                useCaseBlockCard.invoke(number, preferenceProvider.token!!)
            else
                useCaseUnblockCard.invoke(number, preferenceProvider.token!!)
        }
    }


    fun blockingCard(binding: FragmentCardBinding) {
        viewModelScope.launch {
            with(binding) {
                setBlockedCard(true, card.number)
                historyCardButton.isEnabled = false
                renameCard.isEnabled = false
                topUpButton.isEnabled = false
                transferCard.isEnabled = false
                renameCard.isClickable = false
                topUpButton.isClickable = false
                transferCard.isClickable = false
                historyCardButton.isClickable = false
                blockCardButton.text = "Разблокировать"
                topUpButton.setBackgroundColor(android.graphics.Color.DKGRAY)
                transferCard.setBackgroundColor(android.graphics.Color.DKGRAY)
                include.blockedCard.setTextColor(android.graphics.Color.RED)
                historyCardButton.setBackgroundColor(android.graphics.Color.DKGRAY)
                renameCard.setBackgroundColor(android.graphics.Color.DKGRAY)
            }
        }
    }

    fun unBlockingCard(binding: FragmentCardBinding) {
        viewModelScope.launch {
            with(binding) {
                setBlockedCard(false, card.number)
                historyCardButton.isEnabled = true
                renameCard.isEnabled = true
                topUpButton.isEnabled = true
                transferCard.isEnabled = true
                historyCardButton.isClickable = true
                renameCard.isClickable = true
                topUpButton.isClickable = true
                transferCard.isClickable = true
                include.blockedCard.setTextColor(android.graphics.Color.TRANSPARENT)
                blockCardButton.text = "Заблокировать"
                historyCardButton.setBackgroundColor(android.graphics.Color.WHITE)
                renameCard.setBackgroundColor(android.graphics.Color.WHITE)
                topUpButton.setBackgroundColor(android.graphics.Color.parseColor("#ABFB3C3C"))
                transferCard.setBackgroundColor(android.graphics.Color.parseColor("#AB5CF164"))
            }
        }
    }


    @SuppressLint("SetTextI18n")
    fun AlertDialogBlockingOrUnCard(cardFragment: CardFragment, binding: FragmentCardBinding) {
        val tagChange: String = if (card.is_blocked) "Разблокировать"
        else "Заблокировать"
        val builder = AlertDialog.Builder(cardFragment.requireActivity())
        val alert = builder.create();
        val promptsView: View =
            LayoutInflater.from(cardFragment.requireActivity())
                .inflate(R.layout.dialog_blocking_card_or_un, null)
        alert.setView(promptsView)
        alert.setCancelable(false)
        val textView = promptsView.findViewById<TextView>(R.id.block_your_card_text)
        textView.text = tagChange + textView.text
        val bun_cancel = promptsView.findViewById<MaterialButton>(R.id.cancel_button_blocking)
        bun_cancel.setOnClickListener {
            Toast.makeText(
                cardFragment.requireActivity().applicationContext,
                "Операция отменена",
                Toast.LENGTH_SHORT
            ).show()
            alert.dismiss()
        }
        val bun_bl_d = promptsView.findViewById<MaterialButton>(R.id.blocking_button_card)
        bun_bl_d.text = tagChange
        bun_bl_d.setOnClickListener {
            if (!card.is_blocked) {
                blockingCard(binding = binding)
                Toast.makeText(
                    cardFragment.requireActivity().applicationContext,
                    "Вы заблокировали карту",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                unBlockingCard(binding = binding)
                Toast.makeText(
                    cardFragment.requireActivity().applicationContext,
                    "Вы разблокировали карту",
                    Toast.LENGTH_SHORT
                ).show()
            }
            card.is_blocked = !card.is_blocked
            alert.dismiss()
        }
        alert.show()
    }

    @SuppressLint("SetTextI18n")
    fun AlertDialogRenameCard(cardFragment: CardFragment, binding: FragmentCardBinding) {

        val builder = AlertDialog.Builder(cardFragment.requireActivity())
        val alert = builder.create();
        val promptsView: View =
            LayoutInflater.from(cardFragment.requireActivity())
                .inflate(R.layout.dialog_rename_card, null)
        alert.setView(promptsView)
        alert.setCancelable(false)
        val bun_cancel = promptsView.findViewById<MaterialButton>(R.id.cancel_button_rename)
        bun_cancel.setOnClickListener {
            Toast.makeText(
                cardFragment.requireActivity().applicationContext,
                "Операция отменена",
                Toast.LENGTH_SHORT
            ).show()
            alert.dismiss()
        }
        val newName = promptsView.findViewById<EditText>(R.id.name_card_edit_text)
        val bun_bl_d = promptsView.findViewById<MaterialButton>(R.id.rename_button_card)
        bun_bl_d.setOnClickListener {
            if (newName.text.toString() != "") {
                Toast.makeText(
                    cardFragment.requireActivity().applicationContext,
                    "Вы переименовали карту",
                    Toast.LENGTH_SHORT
                ).show()
                viewModelScope.launch {
                    editInstrumentNameUseCase.invoke(
                        preferenceProvider.token!!,
                        newName.text.toString(), card.number, InstrumetType.Card
                    )
                    binding.include.typecard.text = newName.text
                }
                alert.dismiss()
            } else {
                Toast.makeText(
                    cardFragment.requireActivity().applicationContext,
                    "Вы не ввели новое имя карты",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        alert.show()

    }
}


//   lateinit var card_list: List<CardModel>

/*    runBlocking {
    card_list = getCardsUseCase.invoke(preferenceProvider.token!!)!!
}*/