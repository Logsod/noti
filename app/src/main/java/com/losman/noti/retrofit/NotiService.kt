package com.losman.noti.retrofit

import com.losman.noti.model.*
import com.losman.noti.retrofit.request.RequestDeleteCartridgeModels
import com.losman.noti.retrofit.request.RequestUpdateCartridgeDep
import com.losman.noti.retrofit.responces.CheckTokenResponse
import com.losman.noti.retrofit.responces.SignInResponse
import com.losman.noti.retrofit.responces.testResponce
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface NotiService {
    /////////////////////////////////////
    @GET("things")
    fun getThings(): Single<testResponce>

    @GET("signin")
    fun signIn(
        @Query("login") login: String,
        @Query("password") password: String
    ): Single<SignInResponse>

    @FormUrlEncoded
    @POST("signin")
    fun logOut(@Field("token") token: String) : Completable


    @GET("checkToken")
    fun checkToken(
        @Query("token") token: String
    ): Single<CheckTokenResponse>

    /////////////////////////////////////
    //printers
    /////////////////////////////////////
    @FormUrlEncoded
    @POST("printerModel")
    fun addPrinterModel(@Field("printerModelName") printerModelName: String): Completable

    @GET("printerModel")
    fun getAllPrinterModel(): Single<List<PrinterModel>>

    @PUT("printerModel")
    fun updatePrinterModelName(@Body model: PrinterModel): Completable

    @DELETE("printerModel/{id}")
    fun deletePrinterModelName(@Path("id") id: Int): Completable

    /////
    @FormUrlEncoded
    @POST("printer")
    fun addPrinter(
        @Field("model_id") model_id: Int,
        @Field("comment") comment: String
    ): Single<PrinterDevice>

    @GET("printer")
    fun getAllPrinter(): Single<List<PrinterDevice>>

    @PUT("printer")
    fun updatePrinterComment(@Body printer: PrinterDevice): Completable

    @DELETE("printer/{id}")
    fun deletePrinter(@Path("id") id: Int): Completable

    /////////////////////////////////////
    //cartridges
    /////////////////////////////////////
    @FormUrlEncoded
    @POST("cartridgeModel")
    fun addCartridgeModel(@Field("cartridgeModelName") cartridgeModelName: String): Single<CartridgeModel>

    @GET("cartridgeModel")
    fun getAllCartridgeModel(): Single<List<CartridgeModel>>

    @PUT("cartridgeModel/model_dep")
    fun updateCartridgeModelDep(@Body request: RequestUpdateCartridgeDep): Single<List<CartridgeModel>>

    @PUT("cartridgeModel")
    fun deleteCartridgeModel(@Body request: RequestDeleteCartridgeModels): Completable


    @GET("cartridgeModel/model_dep")
    fun getCartridgeDep(
        @Query("id") id: Int
    ): Single<List<CartridgeModel>>

    ////////////////////////////////////////
    ///////base state
    ////////////////////////////////////////
    @GET("cartridgeBaseState")
    fun getAllBaseStateCartridges(): Single<List<CartridgeBaseStateModel>>

    // action list
    // 0 add cartridge to base state
    // 1 change amount
    // 2 take one
    // 3 delete

    @FormUrlEncoded
    @POST("cartridgeBaseState")
    fun addCartridgesToBaseState(
        @Field("cartridge_id") id: Int,
        @Field("amount") amount: Int,
        @Field("action") action: Int = 0
    ): Single<CartridgeBaseStateModel>


    @FormUrlEncoded
    @POST("cartridgeBaseState")
    fun changeCartridgeAmount(
        @Field("id") id: Int,
        @Field("amount") amount: Int,
        @Field("action") action: Int = 1
    ): Completable

    @FormUrlEncoded
    @POST("cartridgeBaseState")
    fun deleteCartridgeFromBaseState(
        @Field("id") id: Int,
        @Field("action") action: Int = 3
    ): Completable

    @FormUrlEncoded
    @POST("cartridgeBaseState")
    fun takeOneCartridge(
        @Field("cartridge_id") cartridge_id: Int,
        @Field("id") id: Int,
        @Field("action") action: Int = 2
    ): Completable

    ///////cartridge state list label
    @GET("cartridgeStateList")
    fun getStateList(): Single<List<CartridgeLabelStateModel>>

    //////////////////////////////
    ///////cartridge state
    //////////////////////////////
    @GET("cartridgeState/{state_id}")
    fun getCartridgesByState(@Path("state_id") state_id: Int): Single<List<CartridgeStateModel>>


    //action list
    //0 change status
    //1 delete
    @FormUrlEncoded
    @POST("cartridgeState")
    fun changeCartridgeState(
        @Field("row_state_id") row_state_id: Int,
        @Field("state") state: Int,
        @Field("action") action: Int = 0
    ): Completable

    @FormUrlEncoded
    @POST("cartridgeState")
    fun deleteCartridgeState(
        @Field("row_state_id") row_state_id: Int,
        @Field("action") action: Int = 1
    ): Completable

}