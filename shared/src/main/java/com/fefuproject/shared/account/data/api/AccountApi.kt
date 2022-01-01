package com.fefuproject.shared.account.data.api

import android.telephony.SignalStrengthUpdateRequest
import com.fefuproject.shared.account.domain.models.*
import com.fefuproject.shared.account.domain.models.CategoryModel
import com.fefuproject.shared.account.domain.models.ResponseModel.ResponseModel
import com.fefuproject.shared.account.domain.requests.GetAllInstrumentHistoryRequest
import retrofit2.http.*
import java.text.DecimalFormat

interface AccountApi {
    @FormUrlEncoded
    @GET("bankomats")
    suspend fun getBankomats(): ResponseModel<SmtListModel<BankomatModel>>

    @FormUrlEncoded
    @GET("valute")
    suspend fun getValute(): ResponseModel<ValuteModel>

    @FormUrlEncoded
    @POST("getcards")
    suspend fun getCards(
        @Field("token") token: String,
    ): ResponseModel<SmtListModel<CardModel>>

    @FormUrlEncoded
    @POST("getcheck")
    suspend fun getChecks(
        @Field("token") token: String,
    ): ResponseModel<SmtListModel<CheckModel>>

    @FormUrlEncoded
    @POST("getcredits")
    suspend fun getCredits(
        @Field("token") token: String,
    ): ResponseModel<SmtListModel<CreditModel>>

    @FormUrlEncoded
    @POST("history/card")
    suspend fun getCardHistory(
        @Field("number") number: String,
        @Field("token") token: String,
    ): ResponseModel<SmtListModel<HistoryInstrumentModel>>

    @FormUrlEncoded
    @POST("history/check")
    suspend fun getCheckHistory(
        @Field("number") number: String,
        @Field("token") token: String,
    ): ResponseModel<SmtListModel<HistoryInstrumentModel>>

    @FormUrlEncoded
    @POST("history/all")
    suspend fun getAllHistory(@Body request: GetAllInstrumentHistoryRequest): ResponseModel<SmtListModel<HistoryInstrumentModel>>

    @FormUrlEncoded
    @POST("block")
    suspend fun blockCard(
        @Field("number") number: String,
        @Field("token") token: String,
    ): ResponseModel<Void>

    @FormUrlEncoded
    @POST("refill") // todo check format of sum
    suspend fun refillCard(
        @Field("source") cardSource: String,
        @Field("dest") cardDest: String,
        @Field("sum") sum: DecimalFormat,
        @Field("token") token: String,
    ): ResponseModel<Void>

    @FormUrlEncoded
    @POST("pay") // todo check format of sum
    suspend fun payCheck(
        @Field("source") cardSource: String,
        @Field("dest") cardDest: String,
        @Field("sum") sum: DecimalFormat,
        @Field("token") token: String,
    ): ResponseModel<Void>

    @FormUrlEncoded
    @POST("category")
    suspend fun getCategory(): ResponseModel<SmtListModel<CategoryModel>>

    @FormUrlEncoded
    @POST("signin")
    suspend fun signIn(
        @Field("name") name: String,
        @Field("username") username: String,
        @Field("password") password: String,
    ): ResponseModel<TokenModel>

    @FormUrlEncoded
    @POST("login")
    suspend fun logIn(
        @Field("username") username: String,
        @Field("password") password: String,
    ): ResponseModel<UserModel>

    @FormUrlEncoded
    @POST("getuser")
    suspend fun getUser(
        @Field("token") token: String,
    ): ResponseModel<UserModel>

    @FormUrlEncoded
    @PUT("editepassword")
    suspend fun changePassword(
        @Field("old_password") old_password: String,
        @Field("new_password") new_password: String,
        @Field("token") token: String,
    ):ResponseModel<TokenModel>

    @FormUrlEncoded
    @PUT("editeusername")
    suspend fun changeUsername(
        @Field("username") username: String,
        @Field("token") token: String,
    ):ResponseModel<Void>

    @FormUrlEncoded
    @POST("lastlogins")
    suspend fun getLoginHistory(
        @Field("token") token: String,
    ): ResponseModel<SmtListModel<LoginHistoryModel>>
}