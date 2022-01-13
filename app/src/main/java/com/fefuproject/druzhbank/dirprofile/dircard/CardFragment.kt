package com.fefuproject.druzhbank.dirprofile.dircard

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.compose.ui.graphics.Color
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentCardBinding
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.dirmoneytransfer.MoneyTransferFragment
import com.fefuproject.druzhbank.dirpayment.PaymentFragment
import com.fefuproject.druzhbank.dirprofile.dircard.dircardhistory.CardHistoryFragment
import com.fefuproject.druzhbank.dirprofile.dirfragmentprofile.ProfileMainFragment
import com.fefuproject.shared.account.domain.models.CardModel
import com.fefuproject.shared.account.domain.usecase.BlockCardUseCase
import com.fefuproject.shared.account.domain.usecase.GetCardsUseCase
import com.fefuproject.shared.account.domain.usecase.UnblockCardUseCase
import com.fefuproject.shared.account.util.CardType
import com.fefuproject.shared.account.util.Util
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class CardFragment(cards: CardModel) : Fragment() {
    private var card = cards
    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var preferenceProvider: PreferenceProvider

    @Inject
    lateinit var getCardsUseCase: GetCardsUseCase

    @Inject
    lateinit var useCaseBlockCard: BlockCardUseCase

    @Inject
    lateinit var useCaseUnblockCard: UnblockCardUseCase

    lateinit var card_list: List<CardModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        runBlocking {
            card_list = getCardsUseCase.invoke(preferenceProvider.token!!)!!
        }
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        runBlocking {
            card_list = getCardsUseCase.invoke(preferenceProvider.token!!)!!
        }
    }

    private fun blockingCard(binding: FragmentCardBinding) {
        with(binding) {
            runBlocking {
                useCaseBlockCard.invoke(card.number, preferenceProvider.token!!)
            }
            historyCardButton.isEnabled = false
            renameCard.isEnabled  = false
            topUpButton.isEnabled  = false
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

    private fun unBlockingCard(binding: FragmentCardBinding) {

        with(binding) {
            runBlocking {
                useCaseUnblockCard.invoke(card.number, preferenceProvider.token!!)
            }
            historyCardButton.isEnabled = true
            renameCard.isEnabled  = true
            topUpButton.isEnabled  = true
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

    @SuppressLint("FragmentBackPressedCallback", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (card.is_blocked) {
            blockingCard(binding = binding)
        } else {
            unBlockingCard(binding = binding)
        }
        val typeCard = Util.getCardIssuer(cardNumber = card.number)
        with(binding.include) {
            when (typeCard) {
                CardType.MIR -> {
                    cardImage.setImageResource(R.drawable.ic_mir)
                }
                CardType.Mastercard -> {
                    cardImage.setImageResource(R.drawable.ic_mastercard)
                }
                CardType.VISA -> {
                    cardImage.setImageResource(R.drawable.ic_visa)
                }
            }
            balansCard.text = card.count + " руб."
            numberCard.text = card.number[0].toString() + card.number[1].toString() +
                    card.number[2].toString() + card.number[3].toString() + "****" +
                    card.number[card.number.length - 4].toString() + card.number[card.number.length - 3].toString() +
                    card.number[card.number.length - 2].toString() + card.number[card.number.length - 1].toString()
        }

        binding.blockCardButton.setOnClickListener {
            AlertDialogBlockingOrUnCard()
        }
        binding.renameCard.setOnClickListener {
            AlertDialogRenameCard()
        }
        binding.historyCardButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                val visibleFragment =
                    parentFragmentManager.fragments.firstOrNull { !isHidden }
                visibleFragment?.let {
                    hide(visibleFragment)
                }
                replace(
                    R.id.fragmentContainerViewProfile,
                    CardHistoryFragment(card), "CardHistory"
                )
                commit()
            }
        }
        binding.topUpButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                val visibleFragment =
                    parentFragmentManager.fragments.firstOrNull { !isHidden }
                visibleFragment?.let {
                    hide(visibleFragment)
                }
                replace(
                    R.id.fragmentContainerViewProfile,
                    PaymentFragment(card), "PaymentFragment"
                )
                commit()
            }
        }
        binding.transferCard.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                val visibleFragment =
                    parentFragmentManager.fragments.firstOrNull { !isHidden }
                visibleFragment?.let {
                    hide(visibleFragment)
                }
                replace(
                    R.id.fragmentContainerViewProfile,
                    MoneyTransferFragment(card), " MoneyTransferFragment"
                )
                commit()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(
            this@CardFragment,
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

    @SuppressLint("SetTextI18n")
    private fun AlertDialogBlockingOrUnCard() {
        val tagChange: String = if (card.is_blocked) "Разблокировать"
        else "Заблокировать"
        val builder = AlertDialog.Builder(this@CardFragment.requireActivity())
        val alert = builder.create();
        val promptsView: View =
            LayoutInflater.from(this@CardFragment.requireActivity())
                .inflate(R.layout.dialog_blocking_card_or_un, null)
        alert.setView(promptsView)
        alert.setCancelable(false)
        val textView = promptsView.findViewById<TextView>(R.id.block_your_card_text)
        textView.text = tagChange + textView.text
        val bun_cancel = promptsView.findViewById<MaterialButton>(R.id.cancel_button_blocking)
        bun_cancel.setOnClickListener {
            Toast.makeText(
                this@CardFragment.requireActivity().applicationContext,
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
                    this@CardFragment.requireActivity().applicationContext,
                    "Вы заблокировали карту",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                unBlockingCard(binding = binding)
                Toast.makeText(
                    this@CardFragment.requireActivity().applicationContext,
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
    private fun AlertDialogRenameCard() {
        val builder = AlertDialog.Builder(this@CardFragment.requireActivity())
        val alert = builder.create();
        val promptsView: View =
            LayoutInflater.from(this@CardFragment.requireActivity())
                .inflate(R.layout.dialog_rename_card, null)
        alert.setView(promptsView)
        alert.setCancelable(false)
        val bun_cancel = promptsView.findViewById<MaterialButton>(R.id.cancel_button_rename)
        bun_cancel.setOnClickListener {
            Toast.makeText(
                this@CardFragment.requireActivity().applicationContext,
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
                    this@CardFragment.requireActivity().applicationContext,
                    "Вы переименовали карту",
                    Toast.LENGTH_SHORT
                ).show()
                alert.dismiss()
            } else {

                Toast.makeText(
                    this@CardFragment.requireActivity().applicationContext,
                    "Вы не ввели новое имя карты",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        alert.show()

    }

}