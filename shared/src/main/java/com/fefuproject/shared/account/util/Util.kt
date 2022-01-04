package com.fefuproject.shared.account.util

enum class CardType {
    VISA,
    Mastercard,
    MIR
}

object Util {
    fun getCardIssuer(cardNumber: String): CardType {
        val prefix = cardNumber.take(2).toInt()
        when (prefix) {
            in 20..29 -> return CardType.MIR
            in 40..49 -> return CardType.VISA
            in 51..55 -> return CardType.Mastercard
            else -> return CardType.VISA
        }
    }
}