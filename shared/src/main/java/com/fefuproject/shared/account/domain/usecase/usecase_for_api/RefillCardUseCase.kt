package com.fefuproject.shared.account.domain.usecase.usecase_for_api

import com.fefuproject.shared.account.data.repository.AccountApiRepositoryWeb
import java.text.DecimalFormat
import javax.inject.Inject

class RefillCardUseCase @Inject constructor(private val repository: AccountApiRepositoryWeb) {
    suspend operator fun invoke(
        cardSource: String,
        cardDest: String,
        sum: DecimalFormat,
        token: String,
    ) = repository.RefillCard(cardSource, cardDest, sum, token)
}