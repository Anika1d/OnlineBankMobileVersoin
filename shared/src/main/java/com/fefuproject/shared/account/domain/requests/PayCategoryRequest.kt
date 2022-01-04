package com.fefuproject.shared.account.domain.requests

data class PayCategoryRequest(
    val token: String,
    val source: String,
    val dest: String,
    val sum: Double,
)