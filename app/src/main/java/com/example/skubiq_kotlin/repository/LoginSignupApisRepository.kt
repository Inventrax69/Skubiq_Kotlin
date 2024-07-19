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

    override suspend fun getStoreRefNos(inboundListPRQ: WMSCoreMessageRequest): Either<MyException, String> {
        return either {
            loginSignupApiService.getStoreRefNos(inboundListPRQ)
        }
    }

    override suspend fun validateLocation(scanListPRQ: WMSCoreMessageRequest): Either<MyException, String> {
        return either {
            loginSignupApiService.validateLocation(scanListPRQ)
        }
    }

    override suspend fun validateEmptyPallet(scanPalletListPRQ: WMSCoreMessageRequest): Either<MyException, String> {
        return either {
            loginSignupApiService.validateEmptyPallet(scanPalletListPRQ)
        }
    }

    override suspend fun validateMaterial(scanSKUListPRQ: WMSCoreMessageRequest): Either<MyException, String> {
        return either {
            loginSignupApiService.validateMaterial(scanSKUListPRQ)
        }
    }

    override suspend fun getReceivedQTY(receiveQTYListPRQ: WMSCoreMessageRequest): Either<MyException, String> {
        return either {
            loginSignupApiService.getReceivedQTY(receiveQTYListPRQ)
        }
    }

    override suspend fun updateReceiveItemForHHT(updateListPRQ: WMSCoreMessageRequest): Either<MyException, String> {
        return either {
            loginSignupApiService.updateReceiveItemForHHT(updateListPRQ)
        }
    }

    override suspend fun getStorageLocations(storageListPRQ: WMSCoreMessageRequest): Either<MyException, String> {
        return either {
            loginSignupApiService.getStorageLocations(storageListPRQ)
        }
    }


    override suspend fun getobdRefNos(obdListPRQ: WMSCoreMessageRequest): Either<MyException, String> {
        return either {
            loginSignupApiService.getobdRefNos(obdListPRQ)
        }
    }



    override suspend fun getTenants(tenantsListPRQ: WMSCoreMessageRequest): Either<MyException, String> {
        return either {
            loginSignupApiService.getTenants(tenantsListPRQ)
        }
    }
}