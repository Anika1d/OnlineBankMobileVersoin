package com.fefuproject.shared.account.domain.usecase

import com.fefuproject.shared.account.domain.enums.PayType
import com.fefuproject.shared.account.domain.models.InstrumentModel
import com.fefuproject.shared.account.domain.repository.AccountRepository
import javax.inject.Inject

class PayUniversalUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend operator fun invoke(
        cardSource: String,
        destination: String,
        sum: Double,
        sourceType: Int,
        targetType: Int,
        token: String,
    ): Boolean {
        val res: Boolean
        if (sourceType == PayType.onCard.ordinal) {
            res = repository.PayByCard(
                cardSource,
                destination,
                sum,
                token,
                targetType
            )
        } else {
            res = repository.PayByCheck(
                cardSource,
                destination,
                sum,
                token,
                targetType
            )
        }
        return res
    }
}