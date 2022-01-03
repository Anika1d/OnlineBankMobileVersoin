package com.fefuproject.shared.account.data.api

import com.fefuproject.shared.account.domain.models.*
import com.fefuproject.shared.account.domain.models.CategoryModel
import com.fefuproject.shared.account.domain.models.ResponseModel
import com.fefuproject.shared.account.domain.requests.*
import retrofit2.http.*

interface AccountApi {
    @GET("bankomats")
    suspend fun getBankomats(): List<BankomatModel>

    @GET("valute")
    suspend fun getValute(): ValuteModel

    @POST("getcards")
    suspend fun getCards(@Body request: TokenRequest): List<CardModel>

    @POST("getcheck")
    suspend fun getChecks(@Body request: TokenRequest): List<CheckModel>

    @POST("getcredits")
    suspend fun getCredits(@Body request: TokenRequest): List<CreditModel>

    @POST("getallinstruments")
    suspend fun getAllInstruments(@Body request: TokenRequest): List<InstrumentModel>

    @POST("history/card")
    suspend fun getCardHistory(@Body request: GetInstrumentHistoryRequest): List<HistoryInstrumentModel>

    @POST("history/check")
    suspend fun getCheckHistory(@Body request: GetInstrumentHistoryRequest): List<HistoryInstrumentModel>

    @POST("history/all")
    suspend fun getAllHistory(@Body request: GetAllInstrumentHistoryRequest): List<HistoryInstrumentModel>

    @POST("block")
    suspend fun blockCard(@Body request: GetInstrumentHistoryRequest): Int

    @POST("refill") // todo check format of sum
    suspend fun refillCard(@Body request: TransferRequest): Int

    @POST("pay") // todo check format of sum
    suspend fun payCheck(@Body request: TransferRequest): Int

    @POST("pay/category") // todo check format of sum
    suspend fun payCategory(@Body request: PayCategoryRequest): Int

    @POST("category")
    suspend fun getCategory(): List<CategoryModel>

    @POST("signin")
    suspend fun signIn(@Body request: SignInRequest): TokenModel

    @POST("login")
    suspend fun logIn(@Body request: LogInRequest): UserModel

    @POST("getuser")
    suspend fun getUser(@Body request: TokenRequest): ResponseModel<UserModel>

    @PUT("editepassword")
    suspend fun changePassword(@Body request: ChangePasswordRequest): TokenModel

    @PUT("editeusername")//todo fix this shit
    suspend fun changeUsername(@Body request: ChangeUsernameRequest): Int

    @POST("lastlogins")
    suspend fun getLoginHistory(@Body request: TokenRequest): List<LoginHistoryModel>
}