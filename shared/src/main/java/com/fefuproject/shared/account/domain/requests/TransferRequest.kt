package com.fefuproject.shared.account.domain.requests

import com.fefuproject.shared.account.domain.enums.PayType

data class TransferRequest(
    val token: String,
    val source: String,
    val dest: String,
    val sum: Double,
    val payType: Int
)