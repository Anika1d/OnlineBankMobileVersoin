package com.fefuproject.shared.account.domain.models

import android.provider.ContactsContract
import java.util.*

data class HistoryInstrumentModel(
    val id: Int,
    val type: Int,
    val instrument_type: Int?,
    val count: String,
    val date: Date,
    val dest: String,
    val source: String,
    var pay_type: String?,
)