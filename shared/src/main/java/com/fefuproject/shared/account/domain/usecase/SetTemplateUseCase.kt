package com.fefuproject.shared.account.domain.usecase

import com.fefuproject.shared.account.data.api.AccountApi
import com.fefuproject.shared.account.domain.enums.InstrumetType
import com.fefuproject.shared.account.domain.enums.PayType
import com.fefuproject.shared.account.domain.repository.AccountRepository
import javax.inject.Inject

class SetTemplateUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend operator fun invoke(
        token: String,
        source: String,
        dest: String,
        source_type: Int,
        dest_type: Int,
        name: String,
        sum: Double
    ) = repository.setTemplate(token, source, dest,source_type,dest_type, name, sum)
}