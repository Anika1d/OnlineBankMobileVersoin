package com.fefuproject.shared.account.data.repository

import com.fefuproject.shared.account.data.api.AccountApi
import com.fefuproject.shared.account.domain.enums.InstrumetType
import com.fefuproject.shared.account.domain.enums.PayType
import com.fefuproject.shared.account.domain.enums.ResultType
import com.fefuproject.shared.account.domain.models.*
import com.fefuproject.shared.account.domain.repository.AccountRepository
import com.fefuproject.shared.account.domain.requests.*
import java.text.DecimalFormat
import javax.inject.Inject

class AccountRepositoryApiImpl @Inject constructor(private val accountApi: AccountApi) :
    AccountRepository {
    override suspend fun getBankomats(): List<BankomatModel> =
        accountApi.getBankomats()

    override suspend fun getValute(): ValuteModel = accountApi.getValute()

    override suspend fun getCards(token: String): List<CardModel> =
        accountApi.getCards(TokenRequest(token))

    override suspend fun getChecks(token: String): List<CheckModel> =
        accountApi.getChecks(TokenRequest(token))

    override suspend fun getCredits(token: String): List<CreditModel> =
        accountApi.getCredits(TokenRequest(token))

    override suspend fun getAllInstrumets(token: String): List<InstrumentModel> {
        var instrumets = accountApi.getAllInstruments(TokenRequest(token))
        for (instrumet in instrumets)
            instrumet.instrumetType = InstrumetType.values()[instrumet.instrumet_type!!].type
        return instrumets
    }

    override suspend fun getCardHistory(
        number: String,
        token: String
    ): List<HistoryInstrumentModel> {
        var history = accountApi.getCardHistory(GetInstrumentHistoryRequest(token, number))
        for (item in history)
            item.pay_type = PayType.values()[item.type].type
        return history
    }

    override suspend fun getCheckHistory(
        number: String,
        token: String
    ): List<HistoryInstrumentModel> {
        var history = accountApi.getCheckHistory(GetInstrumentHistoryRequest(token, number))
        for (item in history)
            item.pay_type = PayType.values()[item.type].type
        return history
    }

    override suspend fun getAllHistory(
        token: String,
        operationCount: Int
    ): List<HistoryInstrumentModel> {
        var history =
            accountApi.getAllHistory(GetAllInstrumentHistoryRequest(token, operationCount))
        for (item in history)
            item.pay_type = PayType.values()[item.type].type
        return history
    }

    override suspend fun blockCard(number: String, token: String): Boolean =
        ResultType.values()[accountApi.blockCard(GetInstrumentHistoryRequest(token, number))].type

    override suspend fun refillCard(
        cardSource: String,
        cardDest: String,
        sum: Double,
        token: String
    ): Boolean = ResultType.values()[accountApi.refillCard(
        TransferRequest(
            token,
            cardSource,
            cardDest,
            sum
        )
    )].type

    override suspend fun PayCheck(
        cardSource: String,
        cardDest: String,
        sum: Double,
        token: String
    ): Boolean = ResultType.values()[accountApi.payCheck(
        TransferRequest(
            token,
            cardSource,
            cardDest,
            sum
        )
    )].type

    override suspend fun PayCategory(
        cardSource: String,
        dest_id: Int,
        sum: Double,
        token: String
    ): Boolean = ResultType.values()[accountApi.payCategory(
        PayCategoryRequest(
            token,
            cardSource,
            dest_id,
            sum
        )
    )].type

    override suspend fun GetCategory(): List<CategoryModel> =
        accountApi.getCategory()


    override suspend fun signIn(
        name: String,
        username: String,
        password: String,
    ): String? {
        var response = accountApi.signIn(SignInRequest(name, username, password))
        if (response.success)
            return response.data.token
        return null
    }


    override suspend fun logIn(username: String, password: String): UserModel? {
        var response = accountApi.logIn(LogInRequest(username, password))
        if (response.success)
            return response.data
        return null
    }

    override suspend fun getUser(token: String): UserModel? {
        var response = accountApi.getUser(TokenRequest(token))
        if (response.success)
            return response.data
        return null
    }

    override suspend fun changePassword(
        old_password: String,
        new_password: String,
        token: String
    ): String? {
        var response =accountApi.changePassword(ChangePasswordRequest(token, old_password, new_password))
        if (response.success)
            return response.data.token
        return null
    }

    override suspend fun changeUsername(username: String, token: String): Boolean =
        ResultType.values()[accountApi.changeUsername(ChangeUsernameRequest(token, username))].type

    override suspend fun getLoginHistory(token: String): List<LoginHistoryModel>? {
        var response = accountApi.getLoginHistory(TokenRequest(token))
        if (response.success)
            return response.data
        return null

    }
}