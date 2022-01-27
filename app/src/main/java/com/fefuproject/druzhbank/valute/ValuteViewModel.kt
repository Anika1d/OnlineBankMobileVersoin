package com.fefuproject.druzhbank.valute

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fefuproject.druzhbank.databinding.ActivityBankBinding
import com.fefuproject.druzhbank.databinding.ActivityValuteBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import com.fefuproject.shared.account.domain.models.BankomatModel
import com.fefuproject.shared.account.domain.models.ValuteModel
import com.fefuproject.shared.account.domain.models.ValuteResponseModel
import com.fefuproject.shared.account.domain.usecase.GetBankomatsUseCase
import com.fefuproject.shared.account.domain.usecase.GetValuteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ValuteViewModel @Inject constructor(
    private val getValuteUseCase: GetValuteUseCase
) : ViewModel() {
    lateinit var   valute_res: ValuteResponseModel
    lateinit var valute_list:List<ValuteModel>
    private val adapter = ValuteAdapter()
    fun drawValute(binding: ActivityValuteBinding,context: Context) {
        viewModelScope.launch {
            valute_res = getValuteUseCase.invoke()
            valute_list=valute_res.ValCurs.Valute
            adapter.addValuteList(valute_list)
            binding.apply {
                recValute.layoutManager = LinearLayoutManager(context)
                recValute.addItemDecoration(CommonItemSpaceDecoration(5))
                shimmerValute.stopShimmer()
                recValute.adapter = adapter
                shimmerValute.visibility=View.GONE
                recValute.visibility=View.VISIBLE
            }

        }
    }
}