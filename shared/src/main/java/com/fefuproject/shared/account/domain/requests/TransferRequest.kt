package com.fefuproject.shared.account.domain.requests

data class TransferRequest(
    val token: String,
    val source: String,
    val dest: String,
    val sum: Double,
)