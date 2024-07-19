package com.bluboy.android.domain.network

import com.bluboy.android.data.models.BaseResponse
import com.bluboy.android.data.models.RegisterUserRS
import com.bluboy.android.presentation.utility.ApiConstant
import com.bluboy.android.presentation.utility.AppConstant
import com.example.skubiq_kotlin.models.WMSCoreMessage
import com.example.skubiq_kotlin.models.WMSCoreMessageRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface LoginSignupApiService {
    @POST(ApiConstant.ApiLogin)
    suspend fun login(@Body wmsCoreMessage: WMSCoreMessageRequest): String

    @POST(ApiConstant.ApiGetWarehouse)
    suspend fun getWarehouse(@Body wmsCoreMessage: WMSCoreMessageRequest): String

    @POST(ApiConstant.ApiStoreRefNo)
    suspend fun getStoreRefNos(@Body wmsCoreMessage: WMSCoreMessageRequest) : String

    @POST(ApiConstant.ApiValidateLocation)
    suspend fun validateLocation(@Body wmsCoreMessage: WMSCoreMessageRequest) : String

    @POST(ApiConstant.ApiValidateEmptyPallet)
    suspend fun validateEmptyPallet(@Body wmsCoreMessage: WMSCoreMessageRequest) : String

    @POST(ApiConstant.ApiValidateMaterial)
    suspend fun validateMaterial(@Body wmsCoreMessage: WMSCoreMessageRequest) : String

    @POST(ApiConstant.ApiGetReceivedQTY)
    suspend fun getReceivedQTY(@Body wmsCoreMessage: WMSCoreMessageRequest) : String

    @POST(ApiConstant.ApiUpdateReceiveItemForHHT)
    suspend fun updateReceiveItemForHHT(@Body wmsCoreMessage: WMSCoreMessageRequest) : String

    @POST(ApiConstant.ApiGetStorageLocations)
    suspend fun getStorageLocations(@Body wmsCoreMessage: WMSCoreMessageRequest) : String

    @POST(ApiConstant.ApiGetobdRefNos)
    suspend fun getobdRefNos(@Body wmsCoreMessage: WMSCoreMessageRequest) : String



}