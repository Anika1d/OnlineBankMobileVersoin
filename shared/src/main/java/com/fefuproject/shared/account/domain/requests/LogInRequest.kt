package com.fefuproject.shared.account.domain.requests

data class LogInRequest(
    val login: String,
    val password: String,
)