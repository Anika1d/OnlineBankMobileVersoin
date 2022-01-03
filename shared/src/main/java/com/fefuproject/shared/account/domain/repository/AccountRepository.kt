package com.fefuproject.shared.account.domain.repository

import com.fefuproject.shared.account.domain.entity.CardEvent
import com.fefuproject.shared.account.domain.entity.CardSummary
import com.fefuproject.shared.account.domain.models.*
import java.text.DecimalFormat

interface AccountRepository {
    suspend fun getBankomats(
    ): List<BankomatModel>

    suspend fun getValute(
    ): ValuteModel

    suspend fun getCards(
        token: String
    ): List<CardModel>

    suspend fun getChecks(
        token: String
    ): List<CheckModel>

    suspend fun getCredits(
        token: String
    ): List<CreditModel>

    suspend fun getAllInstrumets(
        token: String
    ): List<InstrumentModel>

    suspend fun getCardHistory(
        number: String,
        token: String,
    ): List<HistoryInstrumentModel>

    suspend fun getCheckHistory(
        number: String,
        token: String,
    ): List<HistoryInstrumentModel>

    suspend fun getAllHistory(
        token: String,
        operationCount: Int,
    ): List<HistoryInstrumentModel>

    suspend fun blockCard(
        number: String,
        token: String
    ): Boolean

    suspend fun refillCard(
        cardSource: String,
        cardDest: String,
        sum: Double,
        token: String
    ): Boolean

    suspend fun PayCheck(
        cardSource: String,
        cardDest: String,
        sum: Double,
        token: String
    ): Boolean

    suspend fun PayCategory(
        cardSource: String,
        dest_id: Int,
        sum: Double,
        token: String
    ): Boolean

    suspend fun GetCategory(): List<CategoryModel>

    suspend fun signIn(
        name: String,
        username: String,
        password: String,
    ): String?

    suspend fun logIn(
        username: String,
        password: String,
    ): UserModel?

    suspend fun getUser(token: String): UserModel?

    suspend fun changePassword(
        old_password: String,
        new_password: String,
        token: String
    ): String?

    suspend fun changeUsername(
        username: String,
        token: String
    ): Boolean

    suspend fun getLoginHistory(token: String): List<LoginHistoryModel>?

}