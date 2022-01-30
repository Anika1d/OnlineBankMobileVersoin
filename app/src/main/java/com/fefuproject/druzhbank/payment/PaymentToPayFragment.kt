package com.fefuproject.druzhbank.payment

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
import androidx.fragment.app.viewModels
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentPaymentToPayBinding
import com.fefuproject.druzhbank.payment.viewmodels.PaymentToPayViewModel
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PaymentToPayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class PaymentToPayFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var my_card_number: String
    lateinit var fr_pay_number: String
    private var _binding: FragmentPaymentToPayBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        my_card_number = arguments?.getString("myCard").toString()
        fr_pay_number = arguments?.getString("frPay").toString()
    }


    private val viewModel: PaymentToPayViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentToPayBinding.inflate(inflater, container, false)
        binding.shP.startShimmer()
        viewModel.initData(my_card_number, fr_pay_number, binding, this)
        return binding.root
    }

    @SuppressLint("FragmentBackPressedCallback", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(
            this@PaymentToPayFragment,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val bundle = Bundle()
                    bundle.putString("myArg", my_card_number)
                    val fr = PaymentFragment()
                    fr.arguments = bundle
                    parentFragmentManager.beginTransaction().apply {
                        replace(
                            R.id.fragmentContainerViewProfile, fr,
                            "PaymentFragment"
                        )
                        commit()
                    }
                }
            })

    }


}