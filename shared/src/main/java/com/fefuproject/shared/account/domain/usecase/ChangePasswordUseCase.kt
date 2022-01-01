package com.fefuproject.shared.account.domain.usecase

import com.fefuproject.shared.account.data.api.AccountApi
import com.fefuproject.shared.account.domain.repository.AccountRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend operator fun invoke(old_password: String,new_password: String, token: String) =
        repository.changePassword(old_password,new_password, token)
}