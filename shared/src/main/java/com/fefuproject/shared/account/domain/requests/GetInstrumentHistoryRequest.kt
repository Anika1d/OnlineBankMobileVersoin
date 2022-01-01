package com.fefuproject.shared.account.domain.requests

data class GetInstrumentHistoryRequest(
    val token: String,
    val number: String,
)