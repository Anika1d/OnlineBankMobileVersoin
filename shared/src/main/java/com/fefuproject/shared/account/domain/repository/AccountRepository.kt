package com.fefuproject.shared.account.domain.repository

import com.fefuproject.shared.account.domain.enums.InstrumetType
import com.fefuproject.shared.account.domain.enums.PayType
import com.fefuproject.shared.account.domain.models.*
import java.util.*

interface AccountRepository {
    suspend fun getBankomats(
    ): List<BankomatModel>

    suspend fun getValute(
    ): ValuteResponseModel

    suspend fun getCards(
        token: String,
        number: String? = null
    ): List<CardModel>?

    suspend fun getChecks(
        token: String,
        number: String? = null
    ): List<CheckModel>?

    suspend fun getCredits(
        token: String,
        number: String? = null
    ): List<CreditModel>?

    suspend fun getAllInstruments(
        token: String
    ): List<InstrumentModel>?

    suspend fun getCardHistory(
        number: String,
        token: String,
        pageNumber: Int,
        pageSize: Int
    ): PageListModel<HistoryInstrumentModel>?

    suspend fun getCheckHistory(
        number: String,
        token: String,
        pageNumber: Int,
        pageSize: Int
    ): PageListModel<HistoryInstrumentModel>?

    suspend fun getAllHistory(
        token: String,
        pageNumber: Int,
        pageSize: Int,
        findByString: String? = null,
    ): PageListModel<HistoryInstrumentModel>?

    suspend fun blockCard(
        number: String,
        token: String
    ): Boolean

    suspend fun unblockCard(
        number: String,
        token: String
    ): Boolean

    suspend fun editInstrumentName(
        name: String,
        token: String,
        number: String,
        instrument: InstrumetType,//todo check working of it
    ): Boolean

    suspend fun PayByCard(
        cardSource: String,
        cardDest: String,
        sum: Double,
        token: String,
        payType: Int
    ): Boolean

    suspend fun PayByCheck(
        cardSource: String,
        cardDest: String,
        sum: Double,
        token: String,
        payType: Int
    ): Boolean

    suspend fun PayCategory(
        cardSource: String,
        Categorydest: String,
        sum: Double,
        token: String
    ): Boolean

    suspend fun GetCategory(): List<CategoryModel>?

    suspend fun signIn(
        name: String,
        username: String,
        password: String,
    ): String?

    suspend fun addDevice(
        token: String,
        deviceToken: String
    ): Boolean

    suspend fun logIn(
        username: String,
        password: String,
    ): UserModel?

    suspend fun getUser(token: String): UserModel?

    suspend fun getTemplate(
        token: String,
        id: Int? = null
    ): List<TemplateModel>?

    suspend fun setTemplate(
        token: String,
        source: String,
        dest: String,
        source_type: Int,
        dest_type: Int,
        name: String,
        sum: Double,
    ): Boolean

    suspend fun deleteTemplate(
        token: String,
        id: Int
    ): Boolean

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