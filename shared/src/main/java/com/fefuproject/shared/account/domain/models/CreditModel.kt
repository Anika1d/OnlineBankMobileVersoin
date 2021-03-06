package com.fefuproject.shared.account.domain.models

import java.util.*

data class CreditModel(
    val id: Int,
    val user_id: Int?,
    val name: String,
    val number: String,
    val count: String,
    val payment_date: Date,
)