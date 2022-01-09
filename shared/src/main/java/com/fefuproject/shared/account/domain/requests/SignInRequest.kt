package com.fefuproject.shared.account.domain.requests

data class SignInRequest(
    val name: String,
    val login: String,
    val password: String,
)