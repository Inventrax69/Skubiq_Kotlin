package com.example.skubiq_kotlin.domain.network
import com.bluboy.android.data.models.BaseResponse
import com.bluboy.android.data.models.CMSPageRS
import com.bluboy.android.data.models.FAQDataListRS
import com.bluboy.android.data.models.PromocodeRs
import com.bluboy.android.presentation.utility.ApiConstant
import com.bluboy.android.presentation.utility.AppConstant
import com.example.skubiq_kotlin.models.WMSCoreMessageRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface HomeApiService {

    @POST(ApiConstant.ApiStoreRefNo)
    suspend fun login(@Body wmsCoreMessage: WMSCoreMessageRequest): String

}