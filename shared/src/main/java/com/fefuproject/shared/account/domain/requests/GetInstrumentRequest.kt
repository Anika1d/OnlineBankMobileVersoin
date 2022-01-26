package com.fefuproject.shared.account.domain.requests

data class GetInstrumentRequest(
    val token: String,
    val number: String? = null,
)