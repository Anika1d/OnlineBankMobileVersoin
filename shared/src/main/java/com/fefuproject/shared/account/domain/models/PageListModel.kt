package com.fefuproject.shared.account.domain.models

import com.google.gson.annotations.SerializedName

data class PageListModel<T> (
    @SerializedName("data")
    var historyList :List<T>,
    val currentPage: Int,
    @SerializedName("isNext")
    val hasNext: Boolean,
    val countPage: Int
    )