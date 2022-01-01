package com.fefuproject.shared.account.domain.requests

data class LogInRequest(
    val username: String,
    val password: String,
)