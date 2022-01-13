package com.fefuproject.shared.account.domain.usecase

import com.fefuproject.shared.account.domain.repository.AccountRepository
import javax.inject.Inject

class UnblockCardUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend operator fun invoke(number: String, token: String) = repository.unblockCard(number, token)
}