package com.fefuproject.shared.account.domain.models


data class TemplateModel(
    val id: Int,
    val name: String,
    val source: String,
    val dest: String,
    val sum: Int,
)