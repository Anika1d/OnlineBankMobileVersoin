package com.fefuproject.shared.account.data.api

import retrofit2.Call
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
    suspend fun getValute(): Call<ResponseModel<ValuteModel>>

    @POST("getcards") // todo insert token
    suspend fun getCards(): Call<ResponseModel<SmtListModel<CardModel>>>

    @POST("getcheck") // todo insert token
    suspend fun getChecks(): Call<ResponseModel<SmtListModel<CheckModel>>>

    @POST("getcredits") // todo insert token
    suspend fun getCredits(): Call<ResponseModel<SmtListModel<CreditModel>>>

    @POST("history/card") // todo insert token
    suspend fun getCardHistory(
        @Field("number") number: String,
    ): Call<ResponseModel<SmtListModel<HistoryInstrumentModel>>>

    @POST("history/check") // todo insert token
    suspend fun getCheckHistory(
        @Field("number") number: String,
    ): Call<ResponseModel<SmtListModel<HistoryInstrumentModel>>>

    @POST("block") // todo insert token
    suspend fun blockCard(
        @Field("number") number: String,
    ): Call<ResponseModel<Void>>

    @POST("refill") // todo insert token and check format of sum
    suspend fun refillCard(
        @Field("source") cardSource: String,
        @Field("dest") cardDest: String,
        @Field("sum") sum: DecimalFormat,
    ): Call<ResponseModel<Void>>

    @POST("pay") // todo insert token and check format of sum
    suspend fun payCheck(
        @Field("source") cardSource: String,
        @Field("dest") cardDest: String,
        @Field("sum") sum: DecimalFormat,
    ): Call<ResponseModel<Void>>

    @POST("category") // todo insert token
    suspend fun getCategory(): Call<ResponseModel<SmtListModel<CategoryModel>>>

    @POST("signin") // todo insert token
    suspend fun signIn(
        @Field("name") name: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("salt") salt: String,
    ): Call<ResponseModel<Void>>

    @POST("login") // todo insert token
    suspend fun logIn(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Call<ResponseModel<UserModel>>

    @POST("getuser") // todo insert token
    suspend fun getUser(): Call<ResponseModel<UserModel>>

    @PUT("editepassword")
    suspend fun changePassword(
        @Field("old_password") old_password: String,
        @Field("new_password") new_password: String,
    ):Call<ResponseModel<Void>>

    @PUT("editeusername")
    suspend fun changeUsername(
        @Field("username") username: String,
    ):Call<ResponseModel<Void>>

    @POST("lastlogins") // todo insert token
    suspend fun getLoginHistory(): Call<ResponseModel<SmtListModel<LoginHistoryModel>>>
}