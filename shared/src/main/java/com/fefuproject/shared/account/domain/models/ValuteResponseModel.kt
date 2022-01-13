package com.fefuproject.shared.account.domain.models


data class ValuteResponseModel(
    val date: String,
    val rates: List<ValuteModel>,
)

data class ValuteModel(
    val currencyCode: String,
    val currencyRate: Double,
)