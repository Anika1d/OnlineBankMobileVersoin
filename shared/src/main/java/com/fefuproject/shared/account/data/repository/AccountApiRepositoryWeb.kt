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

    override suspend fun getBankomats(): List<BankomatModel> {
       return accountApi.getBankomats().data.listOfSmt
    }

    override suspend fun getValute(): ValuteModel {
        TODO("Not yet implemented")
    }

    override suspend fun getCards(): List<CardModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getChecks(): List<CheckModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getCredits(): List<CreditModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getCheckHistory(number: String): List<HistoryInstrumentModel> {
        TODO("Not yet implemented")
    }

    override suspend fun BlockCard(number: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun RefillCard(
        cardSource: String,
        cardDest: String,
        sum: DecimalFormat
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun PayCheck(
        cardSource: String,
        cardDest: String,
        sum: DecimalFormat
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun GetCategory(): List<CategoryModel> {
        TODO("Not yet implemented")
    }

    override suspend fun SignIn(
        name: String,
        username: String,
        password: String,
        salt: String
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun LogIn(username: String, password: String): UserModel {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(): UserModel {
        TODO("Not yet implemented")
    }

    override suspend fun changePassword(old_password: String, new_password: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun changeUsername(username: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun GetLoginHistory(): List<LoginHistoryModel> {
        TODO("Not yet implemented")
    }
}