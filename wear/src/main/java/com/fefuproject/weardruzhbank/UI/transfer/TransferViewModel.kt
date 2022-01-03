package com.fefuproject.weardruzhbank.UI.transfer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.shared.account.domain.models.InstrumentModel
import com.fefuproject.shared.account.domain.usecase.GetAllInstrumentsUseCase
import com.fefuproject.weardruzhbank.di.PreferenceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    preferenceProvider: PreferenceProvider,
    getAllInstrumentsUseCase: GetAllInstrumentsUseCase
) : ViewModel() {
    private var _isLoading = MutableStateFlow(true)
    val isLoading get() = _isLoading.asStateFlow()
    private var _sourceCards = MutableStateFlow<List<InstrumentModel>?>(null)
    val sourceCards get() = _sourceCards.asStateFlow()

    init {
        viewModelScope.launch {
            _sourceCards.value = getAllInstrumentsUseCase.invoke(preferenceProvider.token!!)
            _isLoading.value = false
        }
    }

    fun startTransaction() {
    }
}