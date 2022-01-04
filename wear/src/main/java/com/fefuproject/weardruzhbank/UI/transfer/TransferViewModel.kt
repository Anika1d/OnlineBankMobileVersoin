package com.fefuproject.weardruzhbank.UI.transfer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.shared.account.domain.enums.InstrumetType
import com.fefuproject.shared.account.domain.models.InstrumentModel
import com.fefuproject.shared.account.domain.usecase.*
import com.fefuproject.weardruzhbank.di.PreferenceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    private val preferenceProvider: PreferenceProvider,
    private val getAllInstrumentsUseCase: GetAllInstrumentsUseCase,
    private val payUniversalUseCase: PayUniversalUseCase
) : ViewModel() {
    private var _isLoading = MutableStateFlow(true)
    val isLoading get() = _isLoading.asStateFlow()
    private var _sourceCards = MutableStateFlow<List<InstrumentModel>?>(null)
    val sourceCards get() = _sourceCards.asStateFlow()

    var transactionBundle: PaymentRequestBundle? = null

    init {
        viewModelScope.launch {
            _sourceCards.value = getAllInstrumentsUseCase.invoke(preferenceProvider.token!!)
            _isLoading.value = false
        }
    }

    fun startTransaction(
        amount: Int,
        successCallback: (() -> Unit)? = null
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            val rVal: Boolean = payUniversalUseCase.invoke(
                transactionBundle!!.src.number!!,
                transactionBundle!!.target.number!!,
                amount.toDouble(),
                transactionBundle!!.src.instrument_type!!,
                transactionBundle!!.target.instrument_type!!,
                preferenceProvider.token!!
            )
            if (rVal) {
                successCallback?.invoke()
            }
            _isLoading.value = false
        }
    }
}

data class PaymentRequestBundle(
    val src: InstrumentModel,
    val target: InstrumentModel
)