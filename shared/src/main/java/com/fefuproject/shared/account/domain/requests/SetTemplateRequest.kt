package com.fefuproject.shared.account.domain.requests

data class SetTemplateRequest(
    val token: String,
    val name: String,
    val source: String,
    val dest: String,
    val sum: Double,
)