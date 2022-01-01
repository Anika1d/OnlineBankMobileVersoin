package com.fefuproject.shared.account.domain.usecase

import com.fefuproject.shared.account.data.api.AccountApi
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: AccountApi) {
    suspend operator fun invoke(name: String, username: String, password: String) =
        repository.signIn(name, username, password)
}