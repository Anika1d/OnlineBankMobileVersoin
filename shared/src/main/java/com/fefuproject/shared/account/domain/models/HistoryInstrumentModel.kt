package com.fefuproject.shared.account.domain.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class HistoryInstrumentModel(
    val id: Int,
    @SerializedName("type")
    val destType: Int,
    @SerializedName("instrument_type")
    val sourceType: Int,
    val count: String,
    val date: Date,
    val dest: String,
    val source: String,
    var pay_type: String?,
)