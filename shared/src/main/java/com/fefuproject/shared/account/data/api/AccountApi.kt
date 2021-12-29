package com.fefuproject.shared.account.data.api

import com.fefuproject.shared.account.domain.models.*
import com.fefuproject.shared.account.domain.models.CategoryModel
import com.fefuproject.shared.account.domain.models.ResponseModel.ResponseModel
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import java.text.DecimalFormat

interface AccountApi {

    @GET("bankomats")
    suspend fun getBankomats(): ResponseModel<SmtListModel<BankomatModel>>

    @GET("valute")
    suspend fun getValute(): ResponseModel<ValuteModel>

    @POST("getcards")
    suspend fun getCards(
        @Field("token") token: String,
    ): ResponseModel<SmtListModel<CardModel>>

    @POST("getcheck")
    suspend fun getChecks(
        @Field("token") token: String,
    ): ResponseModel<SmtListModel<CheckModel>>

    @POST("getcredits")
    suspend fun getCredits(
        @Field("token") token: String,
    ): ResponseModel<SmtListModel<CreditModel>>

    @POST("history/card")
    suspend fun getCardHistory(
        @Field("number") number: String,
        @Field("token") token: String,
    ): ResponseModel<SmtListModel<HistoryInstrumentModel>>

    @POST("history/check")
    suspend fun getCheckHistory(
        @Field("number") number: String,
        @Field("token") token: String,
    ): ResponseModel<SmtListModel<HistoryInstrumentModel>>

    @POST("history/all")
    suspend fun getAllHistory(
        @Field("number") number: String,
        @Field("token") token: String,
        @Field("operationCount") operationCount: Int,
    ): ResponseModel<SmtListModel<HistoryInstrumentModel>>

    @POST("block")
    suspend fun blockCard(
        @Field("number") number: String,
        @Field("token") token: String,
    ): ResponseModel<Void>

    @POST("refill") // todo check format of sum
    suspend fun refillCard(
        @Field("source") cardSource: String,
        @Field("dest") cardDest: String,
        @Field("sum") sum: DecimalFormat,
        @Field("token") token: String,
    ): ResponseModel<Void>

    @POST("pay") // todo check format of sum
    suspend fun payCheck(
        @Field("source") cardSource: String,
        @Field("dest") cardDest: String,
        @Field("sum") sum: DecimalFormat,
        @Field("token") token: String,
    ): ResponseModel<Void>

    @POST("category")
    suspend fun getCategory(): ResponseModel<SmtListModel<CategoryModel>>

    @POST("signin")
    suspend fun signIn(
        @Field("name") name: String,
        @Field("username") username: String,
        @Field("password") password: String,
    ): ResponseModel<TokenModel>

    @POST("login")
    suspend fun logIn(
        @Field("username") username: String,
        @Field("password") password: String,
    ): ResponseModel<UserModel>

    @POST("getuser")
    suspend fun getUser(
        @Field("token") token: String,
    ): ResponseModel<UserModel>

    @PUT("editepassword")
    suspend fun changePassword(
        @Field("old_password") old_password: String,
        @Field("new_password") new_password: String,
        @Field("token") token: String,
    ):ResponseModel<TokenModel>

    @PUT("editeusername")
    suspend fun changeUsername(
        @Field("username") username: String,
        @Field("token") token: String,
    ):ResponseModel<Void>

    @POST("lastlogins")
    suspend fun getLoginHistory(
        @Field("token") token: String,
    ): ResponseModel<SmtListModel<LoginHistoryModel>>
}