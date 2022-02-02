package com.fefuproject.shared.account.domain.requests

import java.util.*


data class GetAllInstrumentHistoryRequest(
    val token: String,
    val pageNumber: Int,
    val pageSize: Int,
    val findByString: String?,

)