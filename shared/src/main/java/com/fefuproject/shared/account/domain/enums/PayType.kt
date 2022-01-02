package com.fefuproject.shared.account.domain.enums

enum class PayType(val type: String) {
    onCard("Перевод на карту"),
    onCheck("Перевод на счет"),
    onCategory("Перевод по категории")
}