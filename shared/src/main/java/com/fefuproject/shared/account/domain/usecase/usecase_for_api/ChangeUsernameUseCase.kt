package com.fefuproject.shared.account.domain.usecase.usecase_for_api

import com.fefuproject.shared.account.data.repository.AccountApiRepositoryWeb
import javax.inject.Inject

class ChangeUsernameUseCase @Inject constructor(private val repository: AccountApiRepositoryWeb) {
    suspend operator fun invoke(username: String, token: String) =
        repository.LogIn(username, token)
}