package com.fefuproject.shared.account.domain.usecase

import com.fefuproject.shared.account.domain.repository.AccountRepository
import javax.inject.Inject

class GetCardEventsUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend operator fun invoke(cardNumber: String) = repository.getCardEvents(cardNumber)
}