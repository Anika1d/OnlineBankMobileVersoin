package com.fefuproject.druzhbank.UI.recent_ops

import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.shared.account.domain.usecase.GetAllInstrumentHistoryUseCase
import com.fefuproject.shared.account.domain.usecase.PayUniversalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import viewModel.RecentOpCommonViewModel
import javax.inject.Inject

@HiltViewModel
class RecentOpViewModel @Inject constructor(
    preferenceProvider: PreferenceProvider,
    private val getAllInstrumentHistoryUseCase: GetAllInstrumentHistoryUseCase,
    private val payUniversalUseCase: PayUniversalUseCase,
) : RecentOpCommonViewModel(
    preferenceProvider,
    getAllInstrumentHistoryUseCase,
    payUniversalUseCase,
    5
)