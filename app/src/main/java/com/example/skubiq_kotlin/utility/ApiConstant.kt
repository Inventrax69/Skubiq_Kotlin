package com.bluboy.android.presentation.utility

/**
 * Created by Parth Patibandha on 01,November,2019
 * Capermint technologies
 * android@caperminttechnologies.com
 */

class ApiConstant {
    companion object {


        //Before Auth
        const val ApiLogin = "Account/UserLogin"

        const val ApiGetWarehouse = "HouseKeeping/GetWarehouse"

        const val ApiStoreRefNo = "Inbound/GetStoreRefNos"

        const val ApiValidateLocation = "Scan/ValidateLocation"

        const val ApiValidateEmptyPallet = "Scan/ValidatePallet"

        const val ApiValidateMaterial = "Scan/ValiDateMaterial"

        const val ApiGetReceivedQTY = "Inbound/GetReceivedQty"

        const val ApiUpdateReceiveItemForHHT = "Inbound/UpdateReceiveItemForHHT"

        const val ApiGetStorageLocations = "Inbound/GetStorageLocations"

        const val ApiGetobdRefNos = "Outbound/GetobdRefNos"

        const val ApiHouseKeepingDTO = "HouseKeeping/GetTenants"

    }
}