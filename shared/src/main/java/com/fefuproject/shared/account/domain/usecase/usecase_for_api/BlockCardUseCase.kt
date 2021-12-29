package com.fefuproject.shared.account.domain.usecase.usecase_for_api

import com.fefuproject.shared.account.domain.repository.AccountRepository
import javax.inject.Inject

class BlockCardUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend operator fun invoke(number: String, token: String) = repository.blockCard(number, token)
}