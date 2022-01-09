package com.fefuproject.shared.account.domain.models

import com.google.gson.annotations.SerializedName

data class UserModel(
    val name: String?,
    @SerializedName("login")
    val username: String?,
    val token: String?,
)