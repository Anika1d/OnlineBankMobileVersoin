package com.fefuproject.shared.account.domain.requests

data class ChangePasswordRequest(
    val token: String,
    val old_password: String,
    val new_password: String,
)