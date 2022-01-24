package com.fefuproject.shared.account.domain.usecase

import com.fefuproject.shared.account.domain.repository.AccountRepository
import javax.inject.Inject

class GetAllHistoryUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend operator fun invoke(token: String, pageNumber: Int, pageSize: Int) =
        repository.getAllHistory(token, pageNumber, pageSize)
}