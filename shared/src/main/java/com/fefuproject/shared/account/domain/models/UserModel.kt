package com.fefuproject.shared.account.domain.models

data class UserModel(
    val name: String?,
    val username: String?,
    val password: String?,
    val salt: String?,
    val token: String?,
)