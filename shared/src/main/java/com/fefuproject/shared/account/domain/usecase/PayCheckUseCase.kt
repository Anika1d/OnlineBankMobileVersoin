package com.fefuproject.shared.account.domain.usecase

import com.fefuproject.shared.account.data.api.AccountApi
import java.text.DecimalFormat
import javax.inject.Inject

class PayCheckUseCase @Inject constructor(private val repository: AccountApi) {
    suspend operator fun invoke(
        cardSource: String,
        cardDest: String,
        sum: DecimalFormat,
        token: String,
    ) = repository.payCheck(cardSource, cardDest, sum, token)
}