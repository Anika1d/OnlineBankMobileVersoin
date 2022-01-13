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
import com.fefuproject.druzhbank.dirmainpayment.MainPaymentFragment
import com.fefuproject.druzhbank.dirprofile.dirfragmentprofile.ProfileMainFragment
import com.fefuproject.shared.account.domain.models.CardModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PaymentToCardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PaymentToCardFragment(card:CardModel) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentPaymentToCardBinding? = null
    private val binding get() = _binding!!
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

    @SuppressLint("FragmentBackPressedCallback")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        textView.text = textView.text.toString()+ " " + binding.valueEditText.text+ " руб"
        val bun_cancel = promptsView.findViewById<MaterialButton>(R.id.cancel_tr)
        bun_cancel.setOnClickListener {
            alert.dismiss()
        }
        val bun_ch_d = promptsView.findViewById<MaterialButton>(R.id.accept_tr)

        bun_ch_d.setOnClickListener {
            Toast.makeText(
                activity!!.applicationContext,
                "Вы перевели "+ binding.valueEditText.text+ " руб",
                Toast.LENGTH_SHORT
            ).show()
            alert.dismiss()
        }
        alert.show()
    }
}