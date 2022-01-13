package com.fefuproject.shared.account.domain.models

import java.sql.Time

data class BankomatModel (
    val id: Long,
    val adress: String,
  //  val time_start: Time,
   // val time_end: Time,
    val coordinates: String,
    val is_atm: Boolean,
    val is_working:Boolean?
        )