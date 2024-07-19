package com.example.skubiq_kotlin.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.skubiq_kotlin.BaseViewModel
import com.example.skubiq_kotlin.contract.LoginSignupRepo
import com.example.skubiq_kotlin.models.WMSCoreMessage
import com.example.skubiq_kotlin.models.WMSCoreMessageRequest
import com.google.gson.Gson
import kotlinx.coroutines.launch

class HouseKeepingViewModel(private val loginSignupRepo: LoginSignupRepo) : BaseViewModel() {

    val tenantsListData : MutableLiveData<String> = MutableLiveData()

    private val gson: Gson = Gson()

    fun getTenants(wareHouseRequest: WMSCoreMessageRequest){
        launch {
            postValue(loginSignupRepo.getTenants(wareHouseRequest), tenantsListData)
        }
    }

    fun parseJsonToMyModel(jsonString: String): WMSCoreMessage {
        return gson.fromJson(jsonString, WMSCoreMessage::class.java)
    }

}