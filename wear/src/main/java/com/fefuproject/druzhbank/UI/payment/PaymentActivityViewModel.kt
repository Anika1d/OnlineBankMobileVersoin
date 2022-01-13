package com.fefuproject.druzhbank.UI.payment

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.shared.account.domain.enums.PayType
import com.fefuproject.shared.account.domain.usecase.PayCategoryUseCase
import com.fefuproject.shared.account.domain.usecase.PayUniversalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentActivityViewModel @Inject constructor(
    private val preferenceProvider: PreferenceProvider,
    private val payUniversalUseCase: PayUniversalUseCase
) : ViewModel() {
    private var _isRefreshing = MutableStateFlow(false)
    val isRefreshing get() = _isRefreshing.asStateFlow()

    val payments: List<PaymentModel>? = listOf(
        PaymentModel("Общага", 3990, "5238393521747493", PayType.onCard.ordinal, "ДВФУ"),
        PaymentModel("ЖКХ", 7000, "5238393521747493", PayType.onCard.ordinal, "ЖКХ"),
        PaymentModel("Связь", 200, "5238393521747493", PayType.onCard.ordinal, "Связь")
    )

    fun makePayment(payment: PaymentModel, context: Context) {
        viewModelScope.launch {
            _isRefreshing.value = true
            val res = payUniversalUseCase.invoke(
                payment.source,
                payment.id.toString(),
                payment.amount.toDouble(),
                payment.sourceType,
                PayType.onCategory.ordinal,
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

data class PaymentModel(
    val name: String,
    val amount: Int,
    val source: String,
    val sourceType: Int,
    val id: String,
)