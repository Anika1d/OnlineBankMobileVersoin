package com.fefuproject.shared.account.domain.usecase.usecase_for_api

import com.fefuproject.shared.account.data.api.AccountApi
import javax.inject.Inject

class GetLoginHistoryUseCase @Inject constructor(private val repository: AccountApi) {
    suspend operator fun invoke( token: String) =
        repository.getLoginHistory(token)
}