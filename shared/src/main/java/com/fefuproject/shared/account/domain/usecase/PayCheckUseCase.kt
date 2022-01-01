package com.fefuproject.shared.account.domain.usecase

import com.fefuproject.shared.account.data.api.AccountApi
import com.fefuproject.shared.account.domain.repository.AccountRepository
import java.text.DecimalFormat
import javax.inject.Inject

class PayCheckUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend operator fun invoke(
        cardSource: String,
        cardDest: String,
        sum: Double,
        token: String,
    ) = repository.PayCheck(cardSource, cardDest, sum, token)
}