package com.example.skubiq_kotlin


import androidx.lifecycle.MutableLiveData
import com.example.skubiq_kotlin.contract.LoginSignupRepo

import com.example.skubiq_kotlin.models.WMSCoreMessage
import com.example.skubiq_kotlin.models.WMSCoreMessageRequest
import com.google.gson.Gson
import kotlinx.coroutines.launch

class LoginSignupViewModel(private val loginSignupRepo: LoginSignupRepo) : BaseViewModel() {

    val loginUserRSLiveData: MutableLiveData<String> = MutableLiveData()
    val wareHouselistData: MutableLiveData<String> = MutableLiveData()
    val inboundListData : MutableLiveData<String> = MutableLiveData()
    private val gson: Gson = Gson()


    fun login(loginPRQ: WMSCoreMessageRequest) {
        launch {
            postValue(loginSignupRepo.login(loginPRQ), loginUserRSLiveData)
        }
    }


    fun getWarehouse(wareHouseRequest: WMSCoreMessageRequest) {
        launch {
            postValue(loginSignupRepo.getWarehouse(wareHouseRequest), wareHouselistData)
        }
    }

    fun getInboundAPI(wareHouseRequest: WMSCoreMessageRequest){
        launch {
            postValue(loginSignupRepo.getInboundAPI(wareHouseRequest), inboundListData)
        }
    }

    fun parseJsonToMyModel(jsonString: String): WMSCoreMessage {
        return gson.fromJson(jsonString, WMSCoreMessage::class.java)
    }




}