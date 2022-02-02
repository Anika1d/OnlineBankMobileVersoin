package com.fefuproject.shared.account.domain.requests

import java.util.*


data class GetAllInstrumentHistoryRequest(
    val token: String,
    val pageNumber: Int,
    val pageSize: Int,
    val findByDest: String?,
    val findBySum: Double?,
    val findByDate: Date?

)