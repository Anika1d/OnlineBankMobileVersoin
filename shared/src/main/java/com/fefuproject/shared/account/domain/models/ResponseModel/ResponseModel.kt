package com.fefuproject.shared.account.domain.models.ResponseModel

import com.google.gson.annotations.SerializedName

data class ResponseModel<T>(
    @SerializedName("Result")//todo check correct name
    val success: Boolean?,
    val data: T
)