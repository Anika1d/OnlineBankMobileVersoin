package com.fefuproject.shared.account.domain.requests

data class SignInRequest(
    val name: String,
    val username: String,
    val password: String,
)