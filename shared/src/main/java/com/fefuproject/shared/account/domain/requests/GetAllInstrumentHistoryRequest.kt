package com.fefuproject.shared.account.domain.requests

data class GetAllInstrumentHistoryRequest(
    val token: String,
    val operationCount: Int,
)