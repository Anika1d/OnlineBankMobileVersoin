package com.fefuproject.druzhbank.profile.dircard.dircardhistory

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentCardHistoryBinding
import com.fefuproject.druzhbank.profile.dircard.CardFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardHistoryFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private val viewModel: CardHistoryViewModel by viewModels()
    private var _binding: FragmentCardHistoryBinding? = null
    private val binding get() = _binding!!
    lateinit var numberCard: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        numberCard = arguments?.get("myArg") as String
        viewModel.initData(numberCard,binding, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardHistoryBinding.inflate(inflater, container, false)
        binding.shimmerCardHistory.startShimmer()
        return binding.root
    }

    @SuppressLint("FragmentBackPressedCallback")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(
            this@CardHistoryFragment,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val bundle = Bundle()
                    bundle.putString("myArg", numberCard)
                    val fr = CardFragment()
                    fr.arguments = bundle
                    parentFragmentManager.beginTransaction().apply {
                        replace(
                            R.id.fragmentContainerViewProfile,
                            fr,
                            "CardFragment"
                        )
                        commit()
                    }
                }
            })
    }

}