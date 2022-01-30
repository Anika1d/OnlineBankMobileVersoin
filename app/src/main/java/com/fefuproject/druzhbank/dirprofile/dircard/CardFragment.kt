package com.fefuproject.druzhbank.dirprofile.dircard

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentCardBinding
import com.fefuproject.druzhbank.moneytransfer.MoneyTransferFragment
import com.fefuproject.druzhbank.payment.PaymentFragment
import com.fefuproject.druzhbank.dirprofile.dircard.dircardhistory.CardHistoryFragment
import com.fefuproject.druzhbank.dirprofile.fragmentprofile.ProfileMainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardFragment : Fragment() {
    private val viewModel: CardViewModel by viewModels()
    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!
    lateinit var tmp_number_card: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tmp_number_card = arguments?.get("myArg") as String
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        binding.shimmerCard.startShimmer()
        viewModel.initCard(tmp_number_card, binding)

        return binding.root
    }


    @SuppressLint("FragmentBackPressedCallback", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.blockCardButton.setOnClickListener {
            viewModel.AlertDialogBlockingOrUnCard(this, binding)
        }
        binding.renameCard.setOnClickListener {
            viewModel.AlertDialogRenameCard(this, binding)
        }

        binding.historyCardButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                val bundle = Bundle()
                bundle.putString("myArg", tmp_number_card)
                val fr = CardHistoryFragment()
                fr.arguments = bundle
                val visibleFragment =
                    parentFragmentManager.fragments.firstOrNull { !isHidden }
                visibleFragment?.let {
                    hide(visibleFragment)
                }
                replace(
                    R.id.fragmentContainerViewProfile,
                    fr, "CardHistory"
                )
                commit()
            }
        }
        binding.topUpButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("myArg", tmp_number_card)
            val fr = PaymentFragment()
            fr.arguments = bundle
            parentFragmentManager.beginTransaction().apply {
                val visibleFragment =
                    parentFragmentManager.fragments.firstOrNull { !isHidden }
                visibleFragment?.let {
                    hide(visibleFragment)
                }
                replace(
                    R.id.fragmentContainerViewProfile,
                    fr, "PaymentFragment"
                )
                commit()
            }
        }
        binding.transferCard.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("myArg", tmp_number_card)
            val fr = MoneyTransferFragment()
            fr.arguments = bundle
            parentFragmentManager.beginTransaction().apply {
                val visibleFragment =
                    parentFragmentManager.fragments.firstOrNull { !isHidden }
                visibleFragment?.let {
                    hide(visibleFragment)
                }
                replace(
                    R.id.fragmentContainerViewProfile,
                    fr, " MoneyTransferFragment"
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


}