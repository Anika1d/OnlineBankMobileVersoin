package com.fefuproject.shared.account.domain.requests

data class ChangeUsernameRequest(
    val token: String,
    val name: String,
)