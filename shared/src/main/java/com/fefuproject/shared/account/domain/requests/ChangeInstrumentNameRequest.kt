package com.fefuproject.shared.account.domain.requests

import com.fefuproject.shared.account.domain.enums.InstrumetType

data class ChangeInstrumentNameRequest(
    val token: String,
    val name: String,
    val number: String,
    val instrument: Int,
)