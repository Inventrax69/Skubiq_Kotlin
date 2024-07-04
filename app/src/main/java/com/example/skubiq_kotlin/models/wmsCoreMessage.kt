package com.example.skubiq_kotlin.models


import com.example.skubiq_kotlin.constants.EndpointConstants
import com.google.gson.annotations.SerializedName


data class WMSCoreMessage(
    @SerializedName("AuthToken") var AuthToken: AuthToken? = null,
    @SerializedName("EntityObject") var EntityObject: Any?= null,
    @SerializedName("Type") var Type: EndpointConstants? =null,
    @SerializedName("WMSMessages") var WMSMessages:  List<WMSExceptionMessage> = emptyList()
)


data class WMSCoreMessageRequest(
    @SerializedName("AuthToken") var AuthToken: AuthToken? = null,
    @SerializedName("EntityObject") var EntityObject: Any? = null,
    @SerializedName("Type") var Type: EndpointConstants? = null,
    @SerializedName("WMSMessages") var WMSMessages:  List<WMSExceptionMessage> = emptyList()

)


data class AuthToken (

    @SerializedName("AuthKey") var AuthKey : String="",
    @SerializedName("UserID") var UserID : String="",
    @SerializedName("AuthValue") var AuthValue : String="",
    @SerializedName("LoginTimeStamp") var LoginTimeStamp : String="",
    @SerializedName("AuthToken") var AuthToken : String="",
    @SerializedName("RequestNumber") var RequestNumber : Int=0,
    @SerializedName("SSOUSerID") var SSOUSerID : Int=0,
    @SerializedName("CookieIdentifier") var CookieIdentifier : String="",
    @SerializedName("Locale") var Locale : String=""

)


data class ProfileDTO (

    @SerializedName("UserName") var UserName : String="",
    @SerializedName("UserID") var UserID : String="",
    @SerializedName("UserTypeID") var UserTypeID : Int=0,
    @SerializedName("UserType") var UserType : String="",
    @SerializedName("SessionIdentifier") var SessionIdentifier : String="",
    @SerializedName("CookieIdentifier") var CookieIdentifier : String="",
    @SerializedName("ClientIP") var ClientIP : String="",
    @SerializedName("ClientMAC") var ClientMAC : String="",
    @SerializedName("LoginTimeStamp") var LoginTimeStamp : String="",
    @SerializedName("LastRequestTimestamp") var LastRequestTimestamp : String="",
    @SerializedName("UserRoleID") var UserRoleID : Int=0,
    @SerializedName("UserRole") var UserRole : String="",
    @SerializedName("WarehouseID") var WarehouseID : Int=0,
    @SerializedName("IsLoggedIn") var IsLoggedIn : Boolean=false,
    @SerializedName("EMail") var EMail : String="",
    @SerializedName("SSOUserID") var SSOUserID : Int=0,
    @SerializedName("FirstName") var FirstName : String="",
    @SerializedName("LastName") var LastName : String="",
    @SerializedName("Password") var Password : String="",
    @SerializedName("SiteCodes") var SiteCodes : String="",
    @SerializedName("DepartmentIDs") var DepartmentIDs : String="",
    @SerializedName("MachineIPAddress") var MachineIPAddress : String="",
    @SerializedName("TenantID") var TenantID : Int=0,
    @SerializedName("SsoId") var SsoId : Int=0,
    @SerializedName("AccountId") var AccountId : String="",
    @SerializedName("TokenData") var TokenData :List<TokenData> = emptyList()
)

data class WMSExceptionMessage(
    @SerializedName("WMSMessage") val WMSMessage: String,
    @SerializedName("WMSExceptionCode") val WMSExceptionCode: String,
    @SerializedName("ShowAsError")val ShowAsError: Boolean,
    @SerializedName("ShowAsWarning")val ShowAsWarning: Boolean,
    @SerializedName("ShowAsSuccess")val ShowAsSuccess: Boolean,
    @SerializedName("ShowAsCriticalError")val ShowAsCriticalError: Boolean,
    @SerializedName("ShowUserConfirmDialogue")val ShowUserConfirmDialogue: Boolean
)


data class LoginUserDTO (
    @SerializedName("ClientMAC") var ClientMAC : String="",
    @SerializedName("MailID") var MailID : String="",
    @SerializedName("PasswordEncrypted") var PasswordEncrypted : String="",
    @SerializedName("SessionIdentifier") var SessionIdentifier : String=""
)


data class TokenData (
    @SerializedName("username") var username : String="",
    @SerializedName("grant_type") var grant_type : String="",
    @SerializedName("scope") var scope : String="",
    @SerializedName("password") var password : String="",
    @SerializedName("access_token") var access_token : String="",
    @SerializedName("refresh_token") var refresh_token : String="",
    @SerializedName("token_type") var token_type : String="",
    @SerializedName("expires_in") var expires_in : String="",
    @SerializedName("error") var error : String="",
)


data class HousekeepingDTO(
    @SerializedName("UserId") var userId: String="",
    @SerializedName("AccountID") var accountID: String="",
    @SerializedName("TenantName") var tenantName: String="",
    @SerializedName("TenantID") var tenantID: String="",
    @SerializedName("Warehouse") var warehouse: String="",
    @SerializedName("WarehouseId") var warehouseId: String="",
    @SerializedName("CartonNo") var cartonNo: String="",
    @SerializedName("Result") var result: String="",
    @SerializedName("IsCustomLabel") var isCustomLabel: String=""
)

/*
data class InboundDTO (

    @SerializedName("AccountID"        ) var AccountID        : String? = null,
    @SerializedName("MaterialMasterID" ) var MaterialMasterID : Int?    = null,
    @SerializedName("SupplierId"       ) var SupplierId       : Int?    = null,
    @SerializedName("UserId"           ) var UserId           : String? = null,
    @SerializedName("WarehouseID"      ) var WarehouseID      : String? = null

)
*/





