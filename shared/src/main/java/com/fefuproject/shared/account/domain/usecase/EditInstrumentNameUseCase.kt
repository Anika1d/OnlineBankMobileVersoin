package com.fefuproject.shared.account.domain.usecase

import com.fefuproject.shared.account.data.api.AccountApi
import com.fefuproject.shared.account.domain.enums.InstrumetType
import com.fefuproject.shared.account.domain.enums.PayType
import com.fefuproject.shared.account.domain.repository.AccountRepository
import java.text.DecimalFormat
import javax.inject.Inject

class EditInstrumentNameUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend operator fun invoke(
        token: String,
        name: String,
        number: String,
        instrumentType: InstrumetType
    ) = repository.editInstrumentName(name, token, number,instrumentType)
}