package com.fefuproject.shared.account.domain.repository

import com.fefuproject.shared.account.domain.entity.CardEvent
import com.fefuproject.shared.account.domain.entity.CardSummary
import com.fefuproject.shared.account.domain.models.*
import java.text.DecimalFormat

interface AccountRepository {
    suspend fun getCardsSummary(
    ): List<CardSummary>

    suspend fun getCardEvents(
        cardNumber: String
    ): List<CardEvent>

    suspend fun getBankomats(
    ): List<BankomatModel>

    suspend fun getValute(
    ): ValuteModel

    suspend fun getCards(
    ): List<CardModel>

    suspend fun getChecks(
    ): List<CheckModel>

    suspend fun getCredits(
    ): List<CreditModel>

    suspend fun getCheckHistory(number: String): List<HistoryInstrumentModel>

    suspend fun BlockCard(number: String): Boolean

    suspend fun RefillCard(
        cardSource: String,
        cardDest: String,
        sum: DecimalFormat,
    ): Boolean

    suspend fun PayCheck(
        cardSource: String,
        cardDest: String,
        sum: DecimalFormat,
    ): Boolean

    suspend fun GetCategory(): List<CategoryModel>

    suspend fun SignIn(
        name: String,
        username: String,
        password: String,
        salt: String,
    ): Boolean

    suspend fun LogIn(
        username: String,
        password: String,
    ): UserModel

    suspend fun getUser(): UserModel

    suspend fun changePassword(
        old_password: String,
        new_password: String,
    ): Boolean

    suspend fun changeUsername(
        username: String,
    ): Boolean

    suspend fun GetLoginHistory(): List<LoginHistoryModel>

}