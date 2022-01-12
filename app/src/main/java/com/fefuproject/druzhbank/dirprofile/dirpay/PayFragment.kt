package com.fefuproject.druzhbank.dirprofile.dirpay

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentPayBinding
import com.fefuproject.druzhbank.dirprofile.dircard.dircardhistory.CardHistoryFragment
import com.fefuproject.druzhbank.dirprofile.dirfragmentprofile.ProfileMainFragment
import com.fefuproject.druzhbank.dirprofile.dirpay.dirhistorypay.HistoryPayFragment
import com.google.android.material.button.MaterialButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

//startPosition:Int
class PayFragment: Fragment() {
 ///   private val pos=startPosition
    private var _binding: FragmentPayBinding? = null
    private val binding get() = _binding!!

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
        // Inflate the layout for this fragment
        _binding = FragmentPayBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("FragmentBackPressedCallback")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PayImageAdapter(this)//,pos
        binding.recycleViewPayFull.adapter = adapter
        adapter.addPay(pays1)
        adapter.addPay(pays)
        adapter.addPay(pays1)
        adapter.addPay(pays)
        adapter.addPay(pays1)
        binding.historyCardButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                val visibleFragment =
                    parentFragmentManager.fragments.firstOrNull { !isHidden }
                visibleFragment?.let {
                    hide(visibleFragment)
                }
                replace(
                    R.id.fragmentContainerViewProfile,
                    HistoryPayFragment(), "PayHistory"
                )
                commit()
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(
            this@PayFragment,
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
    private fun AlertDialogRenamePay() {
        val builder = AlertDialog.Builder(this@PayFragment.requireActivity())
        val alert = builder.create();
        val promptsView: View =
            LayoutInflater.from(this@PayFragment.requireActivity())
                .inflate(R.layout.dialog_rename_card, null)
        alert.setView(promptsView)
        alert.setCancelable(false)
        val bun_cancel = promptsView.findViewById<MaterialButton>(R.id.cancel_button_rename)
        bun_cancel.setOnClickListener {
            Toast.makeText(
                this@PayFragment.requireActivity().applicationContext,
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
                    this@PayFragment.requireActivity().applicationContext,
                    "Вы переименовали счёт",
                    Toast.LENGTH_SHORT
                ).show()
              ///тут нужно изменить объект
                binding.recycleViewPayFull.currentItem
                alert.dismiss()
            }
            else {

                Toast.makeText(
                    this@PayFragment.requireActivity().applicationContext,
                    "Вы не ввели новое имя карты",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        alert.show()

    }


}