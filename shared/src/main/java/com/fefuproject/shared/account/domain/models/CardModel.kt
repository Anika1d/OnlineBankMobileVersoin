package com.fefuproject.shared.account.domain.models

import java.util.*

data class CardModel(
    val id: Int,
    val user_id: Int?,
    val name: String,
    val number: String,
    val hash_cvv: String?,
    val count: String,
    var is_blocked: Boolean,
    val expairy_date: Date,
)