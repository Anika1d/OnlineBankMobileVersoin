package com.fefuproject.shared.account.domain.usecase.usecase_for_api

import com.fefuproject.shared.account.data.repository.AccountApiRepositoryWeb
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val repository: AccountApiRepositoryWeb) {
    suspend operator fun invoke(name: String, username: String, password: String) =
        repository.SignIn(name, username, password)
}