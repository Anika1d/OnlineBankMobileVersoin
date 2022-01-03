package com.fefuproject.shared.account.domain.models

import com.google.gson.annotations.SerializedName

data class ResponseModel<T>(
    val success: Boolean,
    val data: T
)