package com.example.skubiq_kotlin.repository
import com.bluboy.android.domain.network.LoginSignupApiService
import com.example.skubiq_kotlin.BaseRepository
import com.example.skubiq_kotlin.Either
import com.example.skubiq_kotlin.contract.LoginSignupRepo
import com.example.skubiq_kotlin.domain.exceptions.MyException
import com.example.skubiq_kotlin.models.WMSCoreMessage
import com.example.skubiq_kotlin.models.WMSCoreMessageRequest


class LoginSignupApisRepository constructor(
    private val loginSignupApiService: LoginSignupApiService
) : LoginSignupRepo, BaseRepository() {
    override suspend fun login(userListPRQ: WMSCoreMessageRequest): Either<MyException, String> {
        return either {
            loginSignupApiService.login(userListPRQ)

        }
    }

    override suspend fun getWarehouse(wareHouseListPRQ: WMSCoreMessageRequest): Either<MyException, String> {
        return either {
            loginSignupApiService.getWarehouse(wareHouseListPRQ)

        }
    }

    override suspend fun getInboundAPI(inboundList: WMSCoreMessageRequest): Either<MyException, String> {
        return either {
            loginSignupApiService.getInboundAPI(inboundList)
        }
    }


}