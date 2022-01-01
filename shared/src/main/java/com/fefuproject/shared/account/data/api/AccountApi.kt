package com.fefuproject.shared.account.data.api

import android.telephony.SignalStrengthUpdateRequest
import com.fefuproject.shared.account.domain.models.*
import com.fefuproject.shared.account.domain.models.CategoryModel
import com.fefuproject.shared.account.domain.models.ResponseModel.ResponseModel
import com.fefuproject.shared.account.domain.requests.*
import retrofit2.http.*
import java.text.DecimalFormat

interface AccountApi {
    @GET("bankomats")
    suspend fun getBankomats(): ResponseModel<SmtListModel<BankomatModel>>

    @GET("valute")
    suspend fun getValute(): ResponseModel<ValuteModel>

    @POST("getcards")
    suspend fun getCards(@Body request: TokenRequest): List<CardModel>

    @POST("getcheck")
    suspend fun getChecks(@Body request: TokenRequest): List<CheckModel>

    @POST("getcredits")
    suspend fun getCredits(@Body request: TokenRequest): List<CreditModel>

    @POST("history/card")
    suspend fun getCardHistory(@Body request: GetInstrumentHistoryRequest): List<HistoryInstrumentModel>

    @POST("history/check")
    suspend fun getCheckHistory(@Body request: GetInstrumentHistoryRequest): List<HistoryInstrumentModel>

    @POST("history/all")
    suspend fun getAllHistory(@Body request: GetAllInstrumentHistoryRequest): List<HistoryInstrumentModel>

    @POST("block")
    suspend fun blockCard(@Body request: GetInstrumentHistoryRequest): ResponseModel<Void>

    @POST("refill") // todo check format of sum
    suspend fun refillCard(@Body request: TransferRequest): ResponseModel<Void>

    @POST("pay") // todo check format of sum
    suspend fun payCheck(@Body request: TransferRequest): ResponseModel<Void>

    @POST("category")
    suspend fun getCategory(): List<CategoryModel>

    @POST("signin")
    suspend fun signIn(@Body request: SignInRequest): ResponseModel<TokenModel>

    @POST("login")
    suspend fun logIn(@Body request: LogInRequest): ResponseModel<UserModel>

    @POST("getuser")
    suspend fun getUser(@Body request: TokenRequest): UserModel

    @PUT("editepassword")
    suspend fun changePassword(@Body request: ChangePasswordRequest):ResponseModel<TokenModel>

    @PUT("editeusername")//todo fix this shit
    suspend fun changeUsername(@Body request: ChangeUsernameRequest):ResponseModel<Void>

    @POST("lastlogins")
    suspend fun getLoginHistory(@Body request: TokenRequest): List<LoginHistoryModel>
}