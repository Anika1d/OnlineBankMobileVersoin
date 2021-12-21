package com.fefuproject.shared.account.domain.entity

import java.util.*

data class CardEvent(
    val type: String,
    val amount: Double,
    val date: Date
)