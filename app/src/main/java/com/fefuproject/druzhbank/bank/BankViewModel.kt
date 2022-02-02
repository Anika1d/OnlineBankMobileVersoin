package com.fefuproject.druzhbank.bank

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fefuproject.druzhbank.databinding.ActivityBankBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import com.fefuproject.druzhbank.maps.MapsFragment
import com.fefuproject.shared.account.domain.models.BankomatModel
import com.fefuproject.shared.account.domain.usecase.GetBankomatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BankViewModel @Inject constructor(
    private val getBankomatsUseCase: GetBankomatsUseCase,
) : ViewModel() {
    lateinit var bank_list: List<BankomatModel>
    lateinit var fr: MapsFragment
    private val adapter = BanksAdapter(actionListener =
    object : BankActionListener {
        override fun onBankInMap(bank: BankomatModel) {
            super.onBankInMap(bank)
            fr.viewModel.focusInBank(bank.coordinates)
        }
    })

    fun drawBank(binding: ActivityBankBinding, context: Context) {
        viewModelScope.launch {
            binding.shimmerBank.startShimmer()
            bank_list = getBankomatsUseCase.invoke()
            adapter.addBankList(bank_list)
            binding.apply {
                recBank.layoutManager = LinearLayoutManager(context)
                recBank.addItemDecoration(CommonItemSpaceDecoration(5))
                shimmerBank.stopShimmer()
                recBank.adapter = adapter
                shimmerBank.visibility = View.GONE
                recBank.visibility = View.VISIBLE
            }

        }
    }

    fun pushFragment(_fr: MapsFragment) {
        fr = _fr
    }
}