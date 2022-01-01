package com.fefuproject.shared.account.domain.usecase

import com.fefuproject.shared.account.data.api.AccountApi
import javax.inject.Inject

class GetCardHistoryUseCase @Inject constructor(private val repository: AccountApi) {
    suspend operator fun invoke(number: String, token: String) =
        repository.getCardHistory(number, token)
}