package com.fefuproject.druzhbank.mainpayment.sample

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentSampleBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.mainpayment.paymentcontract.PaymentContractFragment
import com.fefuproject.shared.account.domain.models.CategoryModel
import com.fefuproject.shared.account.domain.usecase.GetCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val getCategoryUseCase: GetCategoryUseCase,
) : ViewModel() {

    private val adapter = SampleAdapter(
        object : SampleActionListener {
            @SuppressLint("UseRequireInsteadOfGet")
            override fun OnSampleDetails(categories: CategoryModel) {
                sampleFragment.activity!!.supportFragmentManager.beginTransaction().apply {
                    replace(
                        R.id.fragmentContainerViewProfile,
                        PaymentContractFragment(), "PaymentContractFragment"
                    )
                    commit()
                }
            }
        }
    )
    private lateinit var sample_list: List<CategoryModel>
    private lateinit var sampleFragment: SampleFragment
    private lateinit var binding: FragmentSampleBinding
    fun initData(_binding: FragmentSampleBinding, _sampleFragment: SampleFragment) {
        binding = _binding
        sampleFragment = _sampleFragment
        viewModelScope.launch {
            sample_list = getCategoryUseCase.invoke()!!
            binding.recycleViewCategoriesVertical.adapter = adapter
            binding.recycleViewCategoriesVertical.addItemDecoration(CommonItemSpaceDecoration(15))
            adapter.addCatList(sample_list)
        }
    }
}