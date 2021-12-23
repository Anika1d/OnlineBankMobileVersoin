package com.fefuproject.shared.account.domain.models

data class CardModel(
    val id: Int,
    val user_id: Int?,
    val name: String,
    val number: String,
    val hash_cvv: String?,
    val count: String,
    val is_blocked: Boolean,
    val expairy_date: String,
)