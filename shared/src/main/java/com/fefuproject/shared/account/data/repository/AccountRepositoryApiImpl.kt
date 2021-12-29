package com.fefuproject.shared.account.data.repository

import com.fefuproject.shared.account.data.api.AccountApi
import com.fefuproject.shared.account.domain.models.*
import com.fefuproject.shared.account.domain.repository.AccountRepository
import java.text.DecimalFormat
import javax.inject.Inject

class AccountRepositoryApiImpl @Inject constructor(private val accountApi: AccountApi) :
    AccountRepository {
    override suspend fun getBankomats(): List<BankomatModel> =
        accountApi.getBankomats().data.listOfSth

    override suspend fun getValute(): ValuteModel = accountApi.getValute().data

    override suspend fun getCards(token: String): List<CardModel> =
        accountApi.getCards(token).data.listOfSth

    override suspend fun getChecks(token: String): List<CheckModel> =
        accountApi.getChecks(token).data.listOfSth

    override suspend fun getCredits(token: String): List<CreditModel> =
        accountApi.getCredits(token).data.listOfSth

    override suspend fun getCardHistory(
        number: String,
        token: String
    ): List<HistoryInstrumentModel> =
        accountApi.getCardHistory(number, token).data.listOfSth

    override suspend fun getCheckHistory(
        number: String,
        token: String
    ): List<HistoryInstrumentModel> =
        accountApi.getCheckHistory(number, token).data.listOfSth

    override suspend fun getAllHistory(
        number: String,
        token: String,
        operationCount: Int
    ): List<HistoryInstrumentModel> =
        accountApi.getAllHistory(number, token, operationCount).data.listOfSth

    override suspend fun blockCard(number: String, token: String): Boolean =
        accountApi.blockCard(number, token).success

    override suspend fun refillCard(
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
        accountApi.getCategory().data.listOfSth

    override suspend fun signIn(
        name: String,
        username: String,
        password: String,
    ): String? = accountApi.signIn(name, username, password).data.token


    override suspend fun logIn(username: String, password: String): UserModel =
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

    override suspend fun getLoginHistory(token: String): List<LoginHistoryModel> =
        accountApi.getLoginHistory(token).data.listOfSth
}