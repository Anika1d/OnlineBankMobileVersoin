package com.fefuproject.shared.account.domain.models


data class ValuteResponseModel(
    val ValCurs: ValCursModel,
)

data class ValCursModel(
    val Valute: List<ValuteModel>
)

data class ValuteModel(
    val CharCode: String,
    val Value: String,
    val Name: String,
)