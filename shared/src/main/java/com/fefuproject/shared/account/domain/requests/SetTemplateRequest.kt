package com.fefuproject.shared.account.domain.requests

data class SetTemplateRequest(
    val token: String,
    val name: String,
    val source: String,
    val dest: String,
    val source_type: Int,
    val dest_type: Int,
    val sum: Double,
)