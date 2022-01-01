package com.fefuproject.shared.account.domain.usecase

import com.fefuproject.shared.account.data.api.AccountApi
import javax.inject.Inject

class GetBankomatsUseCase @Inject constructor(private val repository: AccountApi) {
    suspend operator fun invoke() = repository.getBankomats()
}