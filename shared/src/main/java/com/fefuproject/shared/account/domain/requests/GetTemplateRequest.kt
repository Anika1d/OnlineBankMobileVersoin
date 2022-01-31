package com.fefuproject.shared.account.domain.requests

data class GetTemplateRequest(
    val token: String,
    val id: Int?
)