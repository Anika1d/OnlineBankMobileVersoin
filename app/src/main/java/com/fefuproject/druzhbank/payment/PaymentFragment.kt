package com.fefuproject.druzhbank.payment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentPaymentBinding
import com.fefuproject.druzhbank.profile.dircard.CardFragment
import com.fefuproject.druzhbank.payment.viewmodels.PaymentViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PaymentFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!



    private val viewModel: PaymentViewModel by viewModels()
    lateinit var tmp_number_card: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tmp_number_card = arguments?.getString("myArg").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        binding.shimmerCardPayment.startShimmer()
        viewModel.initData(tmp_number_card, binding, this)
        return binding.root
    }

    @SuppressLint("FragmentBackPressedCallback", "ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(
            this@PaymentFragment,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val bundle = Bundle()
                    bundle.putString("myArg", tmp_number_card)
                    val fr = CardFragment()
                    fr.arguments = bundle
                    parentFragmentManager.beginTransaction().apply {
                        replace(
                            R.id.fragmentContainerViewProfile, fr,
                            "CardFragment"
                        )
                        commit()
                    }
                }
            })

    }
}