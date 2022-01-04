package com.fefuproject.druzhbank.UI.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.shared.account.domain.usecase.PayCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentActivityViewModel @Inject constructor(
) : ViewModel() {

    init {
        
    }
}