package com.fefuproject.shared.account.domain.usecase

import com.fefuproject.shared.account.data.api.AccountApi
import com.fefuproject.shared.account.domain.repository.AccountRepository
import javax.inject.Inject

class GetCheckHistoryUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend operator fun invoke(number: String, token: String,pageNumber:Int, pageSize: Int) =
        repository.getCheckHistory(number, token,pageNumber,pageSize)
}