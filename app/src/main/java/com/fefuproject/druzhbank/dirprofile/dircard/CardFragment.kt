package com.fefuproject.druzhbank.dirprofile.dircard

import android.annotation.SuppressLint
import android.graphics.Color
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
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentCardBinding
import com.fefuproject.druzhbank.dirprofile.dircard.dircardhistory.CardHistoryFragment
import com.fefuproject.druzhbank.dirprofile.dirfragmentprofile.ProfileMainFragment
import com.google.android.material.button.MaterialButton

class CardFragment(cards: Cards) : Fragment() {
    private var card = cards
    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun blockingCard(binding: FragmentCardBinding) {
        with(binding) {

            include.blockedCard.setTextColor(Color.RED)
            blockCardButton.text = "Разблокировать"
            historyCardButton.setBackgroundColor(Color.DKGRAY)
            historyCardButton.isClickable = false
            renameCard.setBackgroundColor(Color.DKGRAY)
            renameCard.isClickable = false
        }
    }

    private fun unBlockingCard(binding: FragmentCardBinding) {

        with(binding) {
            include.blockedCard.setTextColor(Color.TRANSPARENT)
            blockCardButton.text = "Заблокировать"
            historyCardButton.setBackgroundColor(Color.WHITE)
            historyCardButton.isClickable = true
            renameCard.setBackgroundColor(Color.WHITE)
            renameCard.isClickable = true
        }
    }

    @SuppressLint("FragmentBackPressedCallback")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (card.isBlocked) {
            blockingCard(binding = binding)
        } else {
            unBlockingCard(binding = binding)
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
        activity?.onBackPressedDispatcher?.addCallback(
            this@CardFragment,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager.beginTransaction().apply {
                        replace(
                            R.id.fragmentContainerViewProfile, ProfileMainFragment(),
                            "FragmentProfileMainBinding"
                        )
                        commit()
                    }
                }
            })


    }

    @SuppressLint("SetTextI18n")
    private fun AlertDialogBlockingOrUnCard() {
        val tagChange: String = if (card.isBlocked) "Разблокировать"
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
            if (!card.isBlocked) {
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
            card.isBlocked = !card.isBlocked

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
            if (newName.text.toString()!="") {
                Toast.makeText(
                    this@CardFragment.requireActivity().applicationContext,
                    "Вы переименовали карту",
                    Toast.LENGTH_SHORT
                ).show()
                card.typeCard = newName.text.toString()
                binding.include.typecard.text = card.typeCard
                alert.dismiss()
            }
            else {

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