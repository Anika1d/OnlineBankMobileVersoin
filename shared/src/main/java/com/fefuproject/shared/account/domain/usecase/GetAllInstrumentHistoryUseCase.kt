package com.fefuproject.shared.account.domain.usecase

import com.fefuproject.shared.account.data.api.AccountApi
import com.fefuproject.shared.account.domain.repository.AccountRepository
import javax.inject.Inject

class GetAllInstrumentHistoryUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend operator fun invoke(token: String, operationCount: Int) =
        repository.getAllHistory(token,operationCount)
}