package com.fefuproject.shared.account.domain.usecase.usecase_for_api

import com.fefuproject.shared.account.data.repository.AccountApiRepositoryWeb
import javax.inject.Inject

class GetValuteUseCase @Inject constructor(private val repository: AccountApiRepositoryWeb) {
    suspend operator fun invoke() = repository.getValute()
}