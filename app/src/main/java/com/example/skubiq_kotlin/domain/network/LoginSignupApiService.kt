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
    suspend fun getInboundAPI(@Body wmsCoreMessage: WMSCoreMessageRequest) : String



}