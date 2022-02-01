package com.fefuproject.druzhbank.paymentHistory

import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.shared.account.domain.usecase.GetAllInstrumentHistoryUseCase
import com.fefuproject.shared.account.domain.usecase.PayUniversalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import viewModel.RecentOpCommonViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentHistoryViewModel @Inject constructor(
    private val preferenceProvider: PreferenceProvider,
    getAllInstrumentHistoryUseCase: GetAllInstrumentHistoryUseCase,
    payUniversalUseCase: PayUniversalUseCase,
) : RecentOpCommonViewModel(
    preferenceProvider,
    getAllInstrumentHistoryUseCase,
    payUniversalUseCase,
    15
)