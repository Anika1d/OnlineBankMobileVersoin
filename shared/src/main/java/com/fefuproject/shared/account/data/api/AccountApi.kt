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
    suspend fun getCards(@Body request: GetInstrumentRequest): ResponseModel<List<CardModel>>

    @POST("getcheck")
    suspend fun getChecks(@Body request: GetInstrumentRequest): ResponseModel<List<CheckModel>>

    @POST("getcredits")
    suspend fun getCredits(@Body request: GetInstrumentRequest): ResponseModel<List<CreditModel>>

    @POST("getallinstruments")
    suspend fun getAllInstruments(@Body request: TokenRequest): ResponseModel<List<InstrumentModel>>

    @POST("history/card")
    suspend fun getCardHistory(@Body request: GetInstrumentHistoryRequest): ResponseModel<PageListModel<HistoryInstrumentModel>>

    @POST("history/check")
    suspend fun getCheckHistory(@Body request: GetInstrumentHistoryRequest): ResponseModel<PageListModel<HistoryInstrumentModel>>

    @POST("history/all")
    suspend fun getAllHistory(@Body request: GetAllInstrumentHistoryRequest): ResponseModel<PageListModel<HistoryInstrumentModel>>

    @POST("block")
    suspend fun blockCard(@Body request: BlockCardRequest): Int

    @POST("unblock")
    suspend fun unblockCard(@Body request: BlockCardRequest): Int

    @POST("refill")
    suspend fun payByCard(@Body request: TransferRequest): Int

    @POST("pay")
    suspend fun payByCheck(@Body request: TransferRequest): Int

    @POST("pay/category")
    suspend fun payCategory(@Body request: PayCategoryRequest): Int

    @POST("category")
    suspend fun getCategory(): ResponseModel<List<CategoryModel>>

    @POST("edit/instrument/name")
    suspend fun changeInstrumentName(@Body request: ChangeInstrumentNameRequest): Int

    @POST("signin")
    suspend fun signIn(@Body request: SignInRequest): ResponseModel<String>

    @POST("device/add")
    suspend fun deviceAdd(@Body request: AddDeviceRequest): Int

    @POST("login")
    suspend fun logIn(@Body request: LogInRequest): ResponseModel<UserModel>

    @POST("getuser")
    suspend fun getUser(@Body request: TokenRequest): ResponseModel<UserModel>

    @POST("templates/get")
    suspend fun getTemplate(@Body request: GetTemplateRequest): ResponseModel<List<TemplateModel>>

    @POST("templates/set")
    suspend fun setTemplate(@Body request: SetTemplateRequest): Int

    @POST("templates/delete")
    suspend fun deleteTemplate(@Body request: DeletetTemplateRequest): Int

    @PUT("editepassword")
    suspend fun changePassword(@Body request: ChangePasswordRequest): ResponseModel<String>

    @PUT("editeusername")//todo fix this shit
    suspend fun changeUsername(@Body request: ChangeUsernameRequest): Int

    @POST("lastlogins")
    suspend fun getLoginHistory(@Body request: TokenRequest): ResponseModel<List<LoginHistoryModel>>
}