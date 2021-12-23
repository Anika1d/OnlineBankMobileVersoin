package com.fefuproject.shared.account.data.repository

import com.fefuproject.shared.account.data.api.AccountApi
import com.fefuproject.shared.account.domain.entity.CardEvent
import com.fefuproject.shared.account.domain.entity.CardSummary
import com.fefuproject.shared.account.domain.models.*
import com.fefuproject.shared.account.domain.repository.AccountRepository
import com.google.gson.JsonObject

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import javax.inject.Inject

class AccountApiRepositoryWeb @Inject constructor(private val accountApi: AccountApi) :
    AccountRepository {
    override suspend fun getCardsSummary(): List<CardSummary> {
        TODO("Not yet implemented")
    }

    override suspend fun getCardEvents(cardNumber: String): List<CardEvent> {
        TODO("Not yet implemented")
    }

    override suspend fun getBankomats(): List<BankomatModel> =
        accountApi.getBankomats().data.listOfSmt

    override suspend fun getValute(): ValuteModel = accountApi.getValute().data

    override suspend fun getCards(token: String): List<CardModel> =
        accountApi.getCards(token).data.listOfSmt

    override suspend fun getChecks(token: String): List<CheckModel> =
        accountApi.getChecks(token).data.listOfSmt

    override suspend fun getCredits(token: String): List<CreditModel> =
        accountApi.getCredits(token).data.listOfSmt

    override suspend fun getCheckHistory(
        number: String,
        token: String
    ): List<HistoryInstrumentModel> =
        accountApi.getCheckHistory(number, token).data.listOfSmt

    override suspend fun BlockCard(number: String, token: String): Boolean =
        accountApi.blockCard(number, token).success

    override suspend fun RefillCard(
        cardSource: String,
        cardDest: String,
        sum: DecimalFormat,
        token: String
    ): Boolean = accountApi.refillCard(cardSource, cardDest, sum, token).success

    override suspend fun PayCheck(
        cardSource: String,
        cardDest: String,
        sum: DecimalFormat,
        token: String
    ): Boolean = accountApi.payCheck(cardSource, cardDest, sum, token).success

    override suspend fun GetCategory(): List<CategoryModel> =
        accountApi.getCategory().data.listOfSmt

    override suspend fun SignIn(
        name: String,
        username: String,
        password: String,
        salt: String,
        token: String
    ): String? =  accountApi.signIn(name, username, password, salt, token).data.token


    override suspend fun LogIn(username: String, password: String): UserModel =
        accountApi.logIn(username, password).data

    override suspend fun getUser(token: String): UserModel = accountApi.getUser(token).data

    override suspend fun changePassword(
        old_password: String,
        new_password: String,
        token: String
    ): String? =
        accountApi.changePassword(old_password, new_password, token).data.token


    override suspend fun changeUsername(username: String, token: String): Boolean =
        accountApi.changeUsername(username, token).success

    override suspend fun GetLoginHistory(token: String): List<LoginHistoryModel> =
        accountApi.getLoginHistory(token).data.listOfSmt
}