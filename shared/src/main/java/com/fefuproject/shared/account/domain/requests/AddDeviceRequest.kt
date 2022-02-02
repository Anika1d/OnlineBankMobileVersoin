package com.fefuproject.shared.account.domain.requests

data class AddDeviceRequest(
    val token: String,
    val deviceToken: String,
)