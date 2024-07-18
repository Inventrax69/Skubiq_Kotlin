package com.example.skubiq_kotlin.utils

object ScanValidator {

    const val QR_FORMAT_STRING: String = "(01)(10)(21)(11)(17)(51)(52)(53)(50)(55)1(54)1"

    fun IsItemScanned(scannedData: String): Boolean {
        return if (scannedData.split("[-]".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray().size == 2) {
            true
        } else {
            false
        }
    }


    // Scan Validator
    fun isItemScanned(scannedData: String): Boolean {
        return if (scannedData.split("[|]".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray().size == 9 || scannedData.split("[|]".toRegex())
                .dropLastWhile { it.isEmpty() }
                .toTypedArray().size == 5 || scannedData.split("[|]".toRegex())
                .toTypedArray().size == 9) {
            true
        } else {
            false
        }
    }

    fun isContainerScanned(scanneddata: String): Boolean {
        return if (scanneddata.length == 14 && (isNumeric(scanneddata.substring(0, 3)) || isNumeric(
                scanneddata.substring(8, 10)
            ))
        ) {
            true
        } else {
            false
        }
    }


    fun isLocationScanned(scannedData: String): Boolean {
        return if ((scannedData.length == 7 || scannedData.length == 9) && isNumeric(
                scannedData.substring(
                    5,
                    7
                )
            )
        ) {
            true
        } else {
            false
        }
    }


    fun isDockLocationScanned(scannedData: String): Boolean {
        return if (scannedData.length == 9 && scannedData.startsWith("DZ")) {
            true
        } else {
            false
        }
    }


    fun isNumeric(ValueToCheck: String): Boolean {
        try {
            val result = ValueToCheck.toDouble()
            return true
        } catch (ex: Exception) {
            return false
        }
    }

    fun isPalletScanned(scannedData: String): Boolean {
        return if ((scannedData.length == 15 && scannedData.substring(0, 1)
                .equals("C", ignoreCase = true))
        ) {
            true
        } else {
            false
        }
    }


    fun replaceQrPlaceholder(qrString: String, barcode: String, placeholder: String): String {
        return qrString.replace(placeholder, placeholder + barcode)
    }

}