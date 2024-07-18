package com.example.skubiq_kotlin.contract
import com.example.skubiq_kotlin.Either
import com.example.skubiq_kotlin.domain.exceptions.MyException
import com.example.skubiq_kotlin.models.WMSCoreMessage
import com.example.skubiq_kotlin.models.WMSCoreMessageRequest


interface LoginSignupRepo {
    suspend fun login(userListPRQ: WMSCoreMessageRequest): Either<MyException, String>

    suspend fun getWarehouse(wareHouseListPRQ: WMSCoreMessageRequest): Either<MyException, String>

    suspend fun getStoreRefNos(inboundListPRQ : WMSCoreMessageRequest) : Either<MyException, String>

    suspend fun validateLocation(scanListPRQ : WMSCoreMessageRequest) : Either<MyException, String>

    suspend fun validateEmptyPallet(scanPalletListPRQ: WMSCoreMessageRequest) : Either<MyException, String>

    suspend fun validateMaterial(scanSKUListPRQ: WMSCoreMessageRequest) : Either<MyException, String>

    suspend fun getReceivedQTY(receiveQTYListPRQ: WMSCoreMessageRequest) : Either<MyException, String>

    suspend fun updateReceiveItemForHHT(updateListPRQ: WMSCoreMessageRequest) : Either<MyException, String>

    suspend fun getStorageLocations(storageListPRQ: WMSCoreMessageRequest) : Either<MyException, String>
}