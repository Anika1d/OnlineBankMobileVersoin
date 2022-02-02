package com.fefuproject.shared.account.data.repository

import com.fefuproject.shared.account.data.api.AccountApi
import com.fefuproject.shared.account.domain.enums.InstrumetType
import com.fefuproject.shared.account.domain.enums.PayType
import com.fefuproject.shared.account.domain.enums.ResultType
import com.fefuproject.shared.account.domain.models.*
import com.fefuproject.shared.account.domain.repository.AccountRepository
import com.fefuproject.shared.account.domain.requests.*
import javax.inject.Inject

class AccountRepositoryApiImpl @Inject constructor(private val accountApi: AccountApi) :
    AccountRepository {
    override suspend fun getBankomats(): List<BankomatModel> =
        accountApi.getBankomats()

    override suspend fun getValute(): ValuteResponseModel = accountApi.getValute()

    override suspend fun getCards(token: String, number: String?): List<CardModel>? {
        val response = accountApi.getCards(GetInstrumentRequest(token, number))
        if (response.success)
            return response.data
        return null
    }

    override suspend fun getChecks(token: String, number: String?): List<CheckModel>? {
        val response = accountApi.getChecks(GetInstrumentRequest(token, number))
        if (response.success)
            return response.data
        return null
    }

    override suspend fun getCredits(token: String, number: String?): List<CreditModel>? {
        val response = accountApi.getCredits(GetInstrumentRequest(token, number))
        if (response.success)
            return response.data
        return null
    }

    override suspend fun getAllInstruments(token: String): List<InstrumentModel>? {
        val response = accountApi.getAllInstruments(TokenRequest(token))
        if (response.success) {
            val instruments = response.data
            for (instrument in instruments) {
                instrument.instrumentType =
                    InstrumetType.values()[instrument.instrument_type].type
            }
            return instruments
        }
        return null
    }

    override suspend fun getCardHistory(
        number: String,
        token: String,
        pageNumber: Int,
        pageSize: Int
    ): PageListModel<HistoryInstrumentModel>? {
        val response =
            accountApi.getCardHistory(
                GetInstrumentHistoryRequest(
                    token,
                    number,
                    pageNumber,
                    pageSize
                )
            )
        if (response.success) {
            val history = response.data.historyList
            for (item in history)
                item.pay_type = PayType.values()[item.destType].type
            response.data.historyList = history
            return response.data
        }
        return null
    }

    override suspend fun getCheckHistory(
        number: String,
        token: String,
        pageNumber: Int,
        pageSize: Int
    ): PageListModel<HistoryInstrumentModel>? {
        val response =
            accountApi.getCheckHistory(
                GetInstrumentHistoryRequest(
                    token,
                    number,
                    pageNumber,
                    pageSize
                )
            )
        if (response.success) {
            val history = response.data.historyList
            for (item in history)
                item.pay_type = PayType.values()[item.destType].type
            response.data.historyList = history
            return response.data
        }
        return null
    }

    override suspend fun getAllHistory(
        token: String,
        pageNumber: Int,
        pageSize: Int
    ): PageListModel<HistoryInstrumentModel>? {
        val response =
            accountApi.getAllHistory(GetAllInstrumentHistoryRequest(token, pageNumber, pageSize))
        if (response.success) {
            val history = response.data.historyList
            for (item in history)
                item.pay_type = PayType.values()[item.destType].type
            response.data.historyList = history
            return response.data
        }
        return null
    }

    override suspend fun blockCard(number: String, token: String): Boolean =
        ResultType.values()[accountApi.blockCard(BlockCardRequest(token, number))].type

    override suspend fun unblockCard(number: String, token: String): Boolean =
        ResultType.values()[accountApi.unblockCard(BlockCardRequest(token, number))].type

    override suspend fun editInstrumentName(
        name: String,
        token: String,
        number: String,
        instrument: InstrumetType
    ): Boolean = ResultType.values()[accountApi.changeInstrumentName(
        ChangeInstrumentNameRequest(
            token,
            name,
            number,
            instrument.ordinal
        )
    )].type


    override suspend fun PayByCard(
        cardSource: String,
        cardDest: String,
        sum: Double,
        token: String,
        payType: Int
    ): Boolean = ResultType.values()[accountApi.payByCard(
        TransferRequest(
            token,
            cardSource,
            cardDest,
            sum,
            payType
        )
    )].type

    override suspend fun PayByCheck(
        cardSource: String,
        cardDest: String,
        sum: Double,
        token: String,
        payType: Int
    ): Boolean = ResultType.values()[accountApi.payByCheck(
        TransferRequest(
            token,
            cardSource,
            cardDest,
            sum,
            payType
        )
    )].type

    override suspend fun PayCategory(
        cardSource: String,
        Categorydest: String,
        sum: Double,
        token: String
    ): Boolean = ResultType.values()[accountApi.payCategory(
        PayCategoryRequest(
            token,
            cardSource,
            Categorydest,
            sum
        )
    )].type

    override suspend fun GetCategory(): List<CategoryModel>? {
        val response = accountApi.getCategory()
        if (response.success)
            return response.data
        return null
    }


    override suspend fun signIn(
        name: String,
        username: String,
        password: String,
    ): String? {
        val response = accountApi.signIn(SignInRequest(name, username, password))
        if (response.success)
            return response.data
        return null
    }

    override suspend fun addDevice(token: String, deviceToken: String): Boolean =
        ResultType.values()[accountApi.deviceAdd(
            AddDeviceRequest(
                token, deviceToken
            )
        )].type


    override suspend fun logIn(username: String, password: String): UserModel? {
        val response = accountApi.logIn(LogInRequest(username, password))
        if (response.success)
            return response.data
        return null
    }

    override suspend fun getUser(token: String): UserModel? {
        val response = accountApi.getUser(TokenRequest(token))
        if (response.success)
            return response.data
        return null
    }

    override suspend fun getTemplate(token: String, id: Int?): List<TemplateModel>? {
        val response = accountApi.getTemplate(GetTemplateRequest(token, id))
        if (response.success)
            return response.data
        return null
    }

    override suspend fun setTemplate(//todo
        token: String,
        source: String,
        dest: String,
        source_type: Int,
        dest_type: Int,
        name: String,
        sum: Double
    ): Boolean = ResultType.values()[accountApi.setTemplate(
        SetTemplateRequest(
            token, name, source, dest, source_type, dest_type, sum
        )
    )].type

    override suspend fun deleteTemplate(token: String, id: Int): Boolean =
        ResultType.values()[accountApi.deleteTemplate(
            DeletetTemplateRequest(
                token, id
            )
        )].type

    override suspend fun changePassword(
        old_password: String,
        new_password: String,
        token: String
    ): String? {
        val response =
            accountApi.changePassword(ChangePasswordRequest(token, old_password, new_password))
        if (response.success)
            return response.data
        return null
    }

    override suspend fun changeUsername(username: String, token: String): Boolean =
        ResultType.values()[accountApi.changeUsername(ChangeUsernameRequest(token, username))].type

    override suspend fun getLoginHistory(token: String): List<LoginHistoryModel>? {
        val response = accountApi.getLoginHistory(TokenRequest(token))
        if (response.success)
            return response.data
        return null

    }
}