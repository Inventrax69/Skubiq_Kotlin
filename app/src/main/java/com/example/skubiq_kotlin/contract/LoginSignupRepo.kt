package com.example.skubiq_kotlin.contract
import com.example.skubiq_kotlin.Either
import com.example.skubiq_kotlin.domain.exceptions.MyException
import com.example.skubiq_kotlin.models.WMSCoreMessage
import com.example.skubiq_kotlin.models.WMSCoreMessageRequest


interface LoginSignupRepo {
    suspend fun login(userListPRQ: WMSCoreMessageRequest): Either<MyException, String>

    suspend fun getWarehouse(wareHouseListPRQ: WMSCoreMessageRequest): Either<MyException, String>

    suspend fun getInboundAPI(inboundList : WMSCoreMessageRequest) : Either<MyException, String>


}