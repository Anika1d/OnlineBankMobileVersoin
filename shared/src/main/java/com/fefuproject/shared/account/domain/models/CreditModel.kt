package com.fefuproject.shared.account.domain.models

data class CreditModel(
    val id: Int,
    val user_id: Int?,
    val name: String,
    val number: String,
    val count: String,
    val payment_date: String,
)