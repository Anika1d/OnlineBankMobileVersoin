package com.fefuproject.shared.account.data.repository

import com.fefuproject.shared.account.data.api.AccountApi
import com.fefuproject.shared.account.domain.models.*
import com.fefuproject.shared.account.domain.repository.AccountRepository
import com.fefuproject.shared.account.domain.requests.*
import java.text.DecimalFormat
import javax.inject.Inject

class AccountRepositoryApiImpl @Inject constructor(private val accountApi: AccountApi) :
    AccountRepository {
    override suspend fun getBankomats(): List<BankomatModel> =
        accountApi.getBankomats().data.listOfSth

    override suspend fun getValute(): ValuteModel = accountApi.getValute().data

    override suspend fun getCards(token: String): List<CardModel> =
        accountApi.getCards(TokenRequest(token))

    override suspend fun getChecks(token: String): List<CheckModel> =
        accountApi.getChecks(TokenRequest(token))

    override suspend fun getCredits(token: String): List<CreditModel> =
        accountApi.getCredits(TokenRequest(token))

    override suspend fun getCardHistory(
        number: String,
        token: String
    ): List<HistoryInstrumentModel> =
        accountApi.getCardHistory(GetInstrumentHistoryRequest(token, number))

    override suspend fun getCheckHistory(
        number: String,
        token: String
    ): List<HistoryInstrumentModel> =
        accountApi.getCheckHistory(GetInstrumentHistoryRequest(token, number))

    override suspend fun getAllHistory(
        token: String,
        operationCount: Int
    ): List<HistoryInstrumentModel> =
        accountApi.getAllHistory(GetAllInstrumentHistoryRequest( token, operationCount))

    override suspend fun blockCard(number: String, token: String): Boolean =
        accountApi.blockCard(GetInstrumentHistoryRequest(token, number)).success

    override suspend fun refillCard(
        cardSource: String,
        cardDest: String,
        sum: Double,
        token: String
    ): Boolean = accountApi.refillCard(TransferRequest(token,cardSource,cardDest,sum)).success

    override suspend fun PayCheck(
        cardSource: String,
        cardDest: String,
        sum: Double,
        token: String
    ): Boolean = accountApi.payCheck(TransferRequest(token,cardSource,cardDest,sum)).success

    override suspend fun GetCategory(): List<CategoryModel> =
        accountApi.getCategory()

    override suspend fun signIn(
        name: String,
        username: String,
        password: String,
    ): String? = accountApi.signIn(SignInRequest(name, username, password)).data.token


    override suspend fun logIn(username: String, password: String): UserModel =
        accountApi.logIn(LogInRequest(username, password)).data

    override suspend fun getUser(token: String): UserModel = accountApi.getUser(TokenRequest(token))

    override suspend fun changePassword(
        old_password: String,
        new_password: String,
        token: String
    ): String? =
        accountApi.changePassword(ChangePasswordRequest(token, old_password, new_password)).data.token


    override suspend fun changeUsername(username: String, token: String): Boolean =
        accountApi.changeUsername(ChangeUsernameRequest(token, username)).success

    override suspend fun getLoginHistory(token: String): List<LoginHistoryModel> =
        accountApi.getLoginHistory(TokenRequest(token))
}