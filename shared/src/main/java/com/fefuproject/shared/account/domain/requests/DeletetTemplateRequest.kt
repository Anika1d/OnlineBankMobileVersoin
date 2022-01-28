package com.fefuproject.shared.account.domain.requests

data class DeletetTemplateRequest(
    val token: String,
    val id: Int
)