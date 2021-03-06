package com.fefuproject.shared.account.domain.models

import java.sql.Time

data class BankomatModel(
    val id: Long,
    val adress: String,
    val time_start: String,
    val time_end: String,
    val coordinates: String,
    val is_atm: Boolean,
    val is_working: Boolean?
)