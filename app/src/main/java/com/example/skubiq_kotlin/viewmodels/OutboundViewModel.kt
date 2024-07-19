package com.example.skubiq_kotlin.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.skubiq_kotlin.BaseViewModel
import com.example.skubiq_kotlin.contract.LoginSignupRepo
import com.example.skubiq_kotlin.models.WMSCoreMessage
import com.example.skubiq_kotlin.models.WMSCoreMessageRequest
import com.google.gson.Gson
import kotlinx.coroutines.launch

class OutboundViewModel(private val loginSignupRepo: LoginSignupRepo) : BaseViewModel() {

    val outboundListData : MutableLiveData<String> = MutableLiveData()
    private val gson: Gson = Gson()

    fun getobdRefNos(wareHouseRequest: WMSCoreMessageRequest){
        launch {
            postValue(loginSignupRepo.getobdRefNos(wareHouseRequest), outboundListData)
        }
    }

    fun parseJsonToMyModel(jsonString: String): WMSCoreMessage {
        return gson.fromJson(jsonString, WMSCoreMessage::class.java)
    }

}