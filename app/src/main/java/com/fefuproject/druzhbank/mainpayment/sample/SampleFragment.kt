package com.fefuproject.druzhbank.mainpayment.sample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentSampleBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.mainpayment.MainPaymentFragment
import com.fefuproject.druzhbank.mainpayment.paymentcontract.PaymentContractFragment
import com.fefuproject.shared.account.domain.models.CategoryModel
import com.fefuproject.shared.account.domain.usecase.GetCategoryUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SampleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SampleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    @Inject
    lateinit var preferenceProvider: PreferenceProvider

    @Inject
    lateinit var getCategoryUseCase: GetCategoryUseCase
    lateinit var sample_list: List<CategoryModel>
    private var _binding: FragmentSampleBinding? = null
    private val binding get() = _binding!!


    private val adapter = SampleAdapter(
        object : SampleActionListener {
            override fun OnSampleDetails(categories: CategoryModel) {
                activity!!.supportFragmentManager.beginTransaction().apply {
                    val visibleFragment =
                        activity!!.supportFragmentManager.fragments.firstOrNull { !isHidden }
                    visibleFragment?.let {
                        hide(visibleFragment)
                    }
                    replace(
                        R.id.fragmentContainerViewProfile,
                        PaymentContractFragment(), "PaymentContractFragment"
                    )
                    commit()
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runBlocking {
            sample_list = getCategoryUseCase.invoke()!!
        }
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSampleBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("FragmentBackPressedCallback")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycleViewCategoriesVertical.adapter = adapter
        binding.recycleViewCategoriesVertical.addItemDecoration(
            CommonItemSpaceDecoration
                (15)
        )
        adapter.addCatList(sample_list)
        activity?.onBackPressedDispatcher?.addCallback(
            this,
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
    }
}