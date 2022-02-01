package com.fefuproject.druzhbank.mainpayment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentMainPaymentBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.mainpayment.paymentcontract.PaymentContractFragment
import com.fefuproject.druzhbank.mainpayment.paymentcontract.adapters.CategoriesActionListener
import com.fefuproject.druzhbank.mainpayment.paymentcontract.adapters.CategoriesActionListenerH
import com.fefuproject.druzhbank.mainpayment.paymentcontract.adapters.CategoriesAdapterHorizotal
import com.fefuproject.druzhbank.mainpayment.paymentcontract.adapters.CategoriesAdapterVertical
import com.fefuproject.shared.account.domain.models.CategoryModel
import com.fefuproject.shared.account.domain.models.TemplateModel
import com.fefuproject.shared.account.domain.usecase.GetCategoryUseCase
import com.fefuproject.shared.account.domain.usecase.GetTemplatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPaymentViewModel @Inject constructor(
    private val preferenceProvider: PreferenceProvider,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getTemplatesUseCase: GetTemplatesUseCase,
) : ViewModel() {
    lateinit var templates_list: List<TemplateModel>
    lateinit var categories_list: List<CategoryModel>
    private val adapterV = CategoriesAdapterVertical(
        object : CategoriesActionListener {
            @SuppressLint("UseRequireInsteadOfGet")
            override fun OnCategoriesDetails(categories: CategoryModel) {
                super.OnCategoriesDetails(categories)
                mainPaymentFragment.activity!!.supportFragmentManager.beginTransaction().apply {
                    replace(
                        R.id.fragmentContainerViewProfile,
                        PaymentContractFragment(), "PaymentContractFragment"
                    )
                    commit()
                }
            }
        }
    )


    private val adapterH = CategoriesAdapterHorizotal(
        object : CategoriesActionListenerH {
            @SuppressLint("UseRequireInsteadOfGet")
            override fun OnCategoriesDetails(categories: TemplateModel) {
                super.OnCategoriesDetails(categories)
                mainPaymentFragment.activity!!.supportFragmentManager.beginTransaction().apply {
                    val bundle = Bundle()
                    bundle.putString("templates", categories.id.toString())
                    val fr = PaymentContractFragment()
                    fr.arguments = bundle
                    replace(
                        R.id.fragmentContainerViewProfile,
                        fr, "PaymentContractFragment"
                    )
                    commit()
                }
            }
        }
    )
    private lateinit var binding: FragmentMainPaymentBinding
    private lateinit var mainPaymentFragment: MainPaymentFragment
    fun initData(_binding: FragmentMainPaymentBinding, _mainPaymentFragment: MainPaymentFragment) {
        binding = _binding
        mainPaymentFragment = _mainPaymentFragment
        viewModelScope.launch {
            categories_list = getCategoryUseCase.invoke()!!
            templates_list = getTemplatesUseCase.invoke(preferenceProvider.token!!)!!
            adapterV.addCatList(categories_list)
            adapterH.addCatList(templates_list)
            binding.recycleViewCategoriesVertical.addItemDecoration(CommonItemSpaceDecoration(16))
            binding.recycleViewCategoriesVertical.adapter = adapterV
            binding.recycleViewCategoriesHorizotal.adapter = adapterH
            binding.shimmerCircle.stopShimmer()
            binding.shimmerRect.stopShimmer()
            binding.shimmerCircle.visibility = View.GONE
            binding.shimmerRect.visibility = View.GONE
            binding.recycleViewCategoriesVertical.visibility = View.VISIBLE
            binding.recycleViewCategoriesHorizotal.visibility = View.VISIBLE
        }
    }
}