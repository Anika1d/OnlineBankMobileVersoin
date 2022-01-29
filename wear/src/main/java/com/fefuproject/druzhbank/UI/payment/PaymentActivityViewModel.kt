package com.fefuproject.druzhbank.UI.payment

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.extensions.createNullList
import com.fefuproject.shared.account.domain.enums.PayType
import com.fefuproject.shared.account.domain.models.TemplateModel
import com.fefuproject.shared.account.domain.usecase.GetTemplatesUseCase
import com.fefuproject.shared.account.domain.usecase.PayUniversalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentActivityViewModel @Inject constructor(
    private val preferenceProvider: PreferenceProvider,
    private val payUniversalUseCase: PayUniversalUseCase,
    private val getTemplatesUseCase: GetTemplatesUseCase
) : ViewModel() {
    private var _isRefreshing = MutableStateFlow(false)
    val isRefreshing get() = _isRefreshing.asStateFlow()

    private var _payments = MutableStateFlow<List<TemplateModel?>>(createNullList(3))
    val payments get() = _payments.asStateFlow()

    init {
        viewModelScope.launch {
            _payments.value =
                getTemplatesUseCase.invoke(preferenceProvider.token!!) ?: _payments.value
        }
    }

    fun makePayment(payment: TemplateModel, context: Context) {
        viewModelScope.launch {
            _isRefreshing.value = true
            val res = payUniversalUseCase.invoke(
                payment.source,
                payment.dest,
                payment.sum.toDouble(),
                payment.source_type,
                payment.dest_type,
                preferenceProvider.token!!
            )
            Toast.makeText(
                context,
                if (res) "Платёж успешно совершён" else "При совершении платежа произошла ошибка...",
                Toast.LENGTH_SHORT
            ).show()
            _isRefreshing.value = false
        }
    }
}