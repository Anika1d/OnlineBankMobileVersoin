package com.fefuproject.shared.account.domain.usecase

import com.fefuproject.shared.account.domain.repository.AccountRepository
import javax.inject.Inject

class GetAllInstrumentsUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend operator fun invoke(token: String) = repository.getAllInstruments(token)
}