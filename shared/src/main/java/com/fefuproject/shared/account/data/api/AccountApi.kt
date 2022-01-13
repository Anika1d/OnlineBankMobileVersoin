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
    suspend fun getValute(): ValuteResponseModel

    @POST("getcards")
    suspend fun getCards(@Body request: TokenRequest): ResponseModel<List<CardModel>>

    @POST("getcheck")
    suspend fun getChecks(@Body request: TokenRequest): ResponseModel<List<CheckModel>>

    @POST("getcredits")
    suspend fun getCredits(@Body request: TokenRequest): ResponseModel<List<CreditModel>>

    @POST("getallinstruments")
    suspend fun getAllInstruments(@Body request: TokenRequest): ResponseModel<List<InstrumentModel>>

    @POST("history/card")
    suspend fun getCardHistory(@Body request: GetInstrumentHistoryRequest): ResponseModel<List<HistoryInstrumentModel>>

    @POST("history/check")
    suspend fun getCheckHistory(@Body request: GetInstrumentHistoryRequest): ResponseModel<List<HistoryInstrumentModel>>

    @POST("history/all")
    suspend fun getAllHistory(@Body request: GetAllInstrumentHistoryRequest): ResponseModel<List<HistoryInstrumentModel>>

    @POST("block")
    suspend fun blockCard(@Body request: BlockCardRequest): Int

    @POST("refill") // todo check format of sum
    suspend fun payByCard(@Body request: TransferRequest): Int

    @POST("pay") // todo check format of sum
    suspend fun payByCheck(@Body request: TransferRequest): Int

    @POST("pay/category") // todo check format of sum
    suspend fun payCategory(@Body request: PayCategoryRequest): Int

    @POST("category")
    suspend fun getCategory(): ResponseModel<List<CategoryModel>>

    @POST("signin")
    suspend fun signIn(@Body request: SignInRequest): ResponseModel<TokenModel>

    @POST("login")
    suspend fun logIn(@Body request: LogInRequest): ResponseModel<UserModel>

    @POST("getuser")
    suspend fun getUser(@Body request: TokenRequest): ResponseModel<UserModel>

    @PUT("editepassword")
    suspend fun changePassword(@Body request: ChangePasswordRequest): ResponseModel<TokenModel>

    @PUT("editeusername")//todo fix this shit
    suspend fun changeUsername(@Body request: ChangeUsernameRequest): Int

    @POST("lastlogins")
    suspend fun getLoginHistory(@Body request: TokenRequest): ResponseModel<List<LoginHistoryModel>>
}