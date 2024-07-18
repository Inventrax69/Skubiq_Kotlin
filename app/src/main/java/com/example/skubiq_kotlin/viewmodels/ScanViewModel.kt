package com.example.skubiq_kotlin.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.skubiq_kotlin.BaseViewModel
import com.example.skubiq_kotlin.contract.LoginSignupRepo
import com.example.skubiq_kotlin.models.WMSCoreMessage
import com.example.skubiq_kotlin.models.WMSCoreMessageRequest
import com.google.gson.Gson
import kotlinx.coroutines.launch

class ScanViewModel(private val loginSignupRepo: LoginSignupRepo) : BaseViewModel() {

    val scanListData : MutableLiveData<String> = MutableLiveData()
    val scanPalletListData : MutableLiveData<String> = MutableLiveData()
    val scanSKUListData : MutableLiveData<String> = MutableLiveData()
    val receiveQTYListData : MutableLiveData<String> = MutableLiveData()
    val updateListData : MutableLiveData<String> = MutableLiveData()
    val inboundList : MutableLiveData<String> = MutableLiveData()
    val storageList : MutableLiveData<String> = MutableLiveData()

    private val gson: Gson = Gson()

    fun validateLocation(wareHouseRequest: WMSCoreMessageRequest){
        launch {
            postValue(loginSignupRepo.validateLocation(wareHouseRequest), scanListData)
        }
    }

    fun validateEmptyPallet(wareHouseRequest: WMSCoreMessageRequest){
        launch {
            postValue(loginSignupRepo.validateEmptyPallet(wareHouseRequest), scanPalletListData)
        }
    }

    fun validateMaterial(wareHouseRequest: WMSCoreMessageRequest){
        launch {
            postValue(loginSignupRepo.validateMaterial(wareHouseRequest), scanSKUListData)
        }
    }

    fun getReceivedQTY(wareHouseRequest: WMSCoreMessageRequest){
        launch {
            postValue(loginSignupRepo.getReceivedQTY(wareHouseRequest), receiveQTYListData)
        }
    }

    fun updateReceiveItemForHHT(wareHouseRequest: WMSCoreMessageRequest){
        launch {
            postValue(loginSignupRepo.updateReceiveItemForHHT(wareHouseRequest), updateListData)
        }
    }

    fun getStorageLocations(wareHouseRequest: WMSCoreMessageRequest){
        launch {
            postValue(loginSignupRepo.getStorageLocations(wareHouseRequest), storageList)
        }
    }

    fun parseJsonToMyModel(jsonString: String): WMSCoreMessage {
        return gson.fromJson(jsonString, WMSCoreMessage::class.java)
    }
}