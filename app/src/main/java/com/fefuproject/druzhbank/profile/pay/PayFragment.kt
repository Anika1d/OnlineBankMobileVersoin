package com.fefuproject.druzhbank.profile.pay

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentPayBinding
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.profile.fragmentprofile.ProfileMainFragment
import com.fefuproject.shared.account.domain.usecase.EditInstrumentNameUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
@AndroidEntryPoint
class PayFragment() : Fragment() {
    private var _binding: FragmentPayBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PayViewModel by viewModels()

    @Inject
    lateinit var editInstrumentNameUseCase: EditInstrumentNameUseCase

    @Inject
    lateinit var preferenceProvider: PreferenceProvider

    private lateinit var tmp_number_pay: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tmp_number_pay = arguments?.getString("myArg").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPayBinding.inflate(inflater, container, false)
        binding.shimmerFrameLayout.startShimmer()
        viewModel.initData(tmp_number_pay, binding, this)
        return binding.root
    }

    @SuppressLint("FragmentBackPressedCallback", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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




}