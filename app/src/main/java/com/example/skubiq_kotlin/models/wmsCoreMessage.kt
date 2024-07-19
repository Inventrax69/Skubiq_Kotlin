package com.example.skubiq_kotlin.models


import android.os.Parcelable
import com.example.skubiq_kotlin.constants.EndpointConstants
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


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

@Parcelize
data class InboundDTO(

    @SerializedName("_InboundNumber") var InboundNumber: String?  = null,
    @SerializedName("_Location") var Location: String? = null,
    @SerializedName("MDescription") var MDescription: String? = null,
    @SerializedName("SupplierInvoiceDetailsID") var SupplierInvoiceDetailsID: String? = null,
    @SerializedName("UserId") var UserId: String?  = null,
    @SerializedName("InboundID") var InboundID: String? = null,
    @SerializedName("StoreRefNo") var StoreRefNo: String? = null,
    @SerializedName("Storerefno") var Storerefno: String? = null,
    @SerializedName("PalletNo") var PalletNo: String? = null,
    @SerializedName("AccountID") var AccountID: String? = null,
    @SerializedName("StorageLocation") var StorageLocation: String? = null,
    @SerializedName("Result") var Result: String? = null,
    @SerializedName("Mcode") var Mcode: String? = null,
    @SerializedName("MCode") var MCode: String? = null,
    @SerializedName("ReceivedQty") var ReceivedQty: String? = null,
    @SerializedName("ItemPendingQty") var ItemPendingQty: String? = null,
    @SerializedName("BatchNo") var BatchNo: String? = null,
    @SerializedName("SerialNo") var SerialNo: String? = null,
    @SerializedName("MfgDate") var MfgDate: String? = null,
    @SerializedName("ExpDate") var ExpDate: String? = null,
    @SerializedName("ProjectRefno") var ProjectRefno: String? = null,
    @SerializedName("Qty") var Qty: String? = null,
    @SerializedName("Lineno") var Lineno: String? = null,
    @SerializedName("HasDisc") var HasDisc: String? = null,
    @SerializedName("CartonNo") var CartonNo: String? = null,
    @SerializedName("CreatedBy") var CreatedBy: String? = null,
    @SerializedName("IsDam") var IsDam: String? = null,
    @SerializedName("SkipType") var SkipType: String? = null,
    @SerializedName("SkipReason") var SkipReason: String?  = null,
    @SerializedName("InvoiceQty") var InvoiceQty: String? = null,
    @SerializedName("IsOutbound") var IsOutbound: String? = null,
    @SerializedName("MRP") var MRP: String? = null,
    @SerializedName("Dock") var Dock: String? = null,
    @SerializedName("Entry")
    val entry: List<EntryDTO>? =null,
    @SerializedName("VehicleNo") var VehicleNo: String? = null,
    @SerializedName("HUNo") var HUNo: String? = null,
    @SerializedName("HUSize") var HUSize: String? = null,
    @SerializedName("WarehouseID") var WarehouseID: String? = null,
    @SerializedName("TenantID") var TenantID: String? = null,
    @SerializedName("UserRole") var userRole: String? = null,
    @SerializedName("SLoc") var SLoc: String? = null,
    @SerializedName("ProjectNo") var ProjectNo: String? = null,
    @SerializedName("SupplierInvoiceID") var SupplierInvoiceID: String? = null,
    @SerializedName("POSOHeaderId") var POSOHeaderId: String? = null,
    @SerializedName("TotalPalletNo") var TotalPalletNo: Int? = null,
    @SerializedName("palletcount") var palletcount: Int? = null,
    @SerializedName("volume") var volume: Int? = null,
    @SerializedName("weight") var weight: Int? = null,
    @SerializedName("IsCustomLabel") var IsCustomLabel: String? = null,
    @SerializedName("ImageURL") var ImageURL: String? = null,
    @SerializedName("MaterialMasterID") var MaterialMasterID: Int? = 0,
    @SerializedName("LocationID") var LocationID: Int? = null,
    @SerializedName("CartonId") var CartonId: Int? = null,
    @SerializedName("StorageLocationID") var StorageLocationID: Int? = null,
    @SerializedName("MMID") var MMID: Int? = null,
    @SerializedName("Remarks") var Remarks: String? = null,
    @SerializedName("IsDamaged") var IsDamaged: Int? = null,
    @SerializedName("IsShipmentClose") var IsShipmentClose: Int? = null,
    @SerializedName("GoodsMovementDetailsId") var GoodsMovementDetailsId: Int? = null,
    @SerializedName("Uom") var Uom: String? = null,
    @SerializedName("UomQty") var UomQty: Int? = null,
    @SerializedName("Sku") var Sku: String? = null,
    @SerializedName("FilePath") var FilePath: String? = null,
    @SerializedName("PoNumber") var PoNumber: String? = null,
    @SerializedName("InvoiceNo") var InvoiceNo: String? = null,
    @SerializedName("MaterialMasterUOMId") var MaterialMasterUOMId: Int? = null,
    @SerializedName("SupplierId") var SupplierId: Int? = null,
    @SerializedName("SupplierName") var SupplierName: String? = null,
    @SerializedName("SupplierCode") var SupplierCode: String? = null,
    @SerializedName("POHeaderID") var POHeaderID: Int? = null,
    @SerializedName("NoOfBoxes") var NoOfBoxes: Int? = null,
    @SerializedName("ZPLScript") var ZPLScript: String? = null,
    @SerializedName("WhId") var WhId: Int? = null,
    @SerializedName("Ponumber") var Ponumber: String? = null,
    @SerializedName("ScanInput") var ScanInput: String? = null,
    @SerializedName("TotalQty") var TotalQty: String? = null,
    @SerializedName("ScanResult") var ScanResult: Boolean? = null,
    @SerializedName("BoxResult") var BoxResult: String? = null
) : Parcelable

@Parcelize
data class EntryDTO(
    @SerializedName("DockNumber"    ) var DockNumber    : String? = null,
    @SerializedName("VehicleNumber" ) var VehicleNumber : String? = null,
    @SerializedName("DockID"        ) var DockID        : String? = null

) : Parcelable

data class ScanDTO(
    @SerializedName("SupplierInvoiceDetailsID") var SupplierInvoiceDetailsID : String?  = null,
    @SerializedName("ScanInput") var ScanInput: String?  = null,
    @SerializedName("ScanResult") var ScanResult: Boolean? = null,
    @SerializedName("Message") var Message: String?  = null,
    @SerializedName("WarehouseID") var WarehouseID : String?  = null,
    @SerializedName("TenantID"                 ) var TenantID                 : String?  = null,
    @SerializedName("SkuCode"                  ) var SkuCode                  : String?  = null,
    @SerializedName("Batch"                    ) var Batch                    : String?  = null,
    @SerializedName("SerialNumber"             ) var SerialNumber             : String?  = null,
    @SerializedName("ExpDate"                  ) var ExpDate                  : String?  = null,
    @SerializedName("MfgDate"                  ) var MfgDate                  : String?  = null,
    @SerializedName("PrjRef"                   ) var PrjRef                   : String?  = null,
    @SerializedName("KitID"                    ) var KitID                    : String?  = null,
    @SerializedName("LineNumber"               ) var LineNumber               : String?  = null,
    @SerializedName("InboundID"                ) var InboundID                : String?  = null,
    @SerializedName("UserID"                   ) var UserID                   : String?  = null,
    @SerializedName("Mrp"                      ) var Mrp                      : String?  = null,
    @SerializedName("ObdNumber"                ) var ObdNumber                : String?  = null,
    @SerializedName("VlpdNumber"               ) var VlpdNumber               : String?  = null,
    @SerializedName("AccountID") var AccountID: Int? = null,
    @SerializedName("IsCycleCount"             ) var IsCycleCount             : Boolean? = null,
    @SerializedName("HUSize"                   ) var HUSize                   : String?  = null,
    @SerializedName("HUNo"                     ) var HUNo                     : String?  = null,
    @SerializedName("UOM"                      ) var UOM                      : String?  = null,
    @SerializedName("Length"                   ) var Length                   : Int?     = null,
    @SerializedName("Width"                    ) var Width                    : Int?     = null,
    @SerializedName("Height"                   ) var Height                   : Int?     = null,
    @SerializedName("Weight"                   ) var Weight                   : Int?     = null,
    @SerializedName("volume"                   ) var volume                   : Int?     = null,
    @SerializedName("CartonID"                 ) var CartonID                 : Int?     = null,
    @SerializedName("Location"                 ) var Location                 : String?  = null,
    @SerializedName("Description"              ) var Description              : String?  = null,
    @SerializedName("IsSerialNo"               ) var IsSerialNo               : Boolean? = null

)

@Parcelize
data class OutboundDTO(
    @SerializedName("OutboundID")
    var outboundID: String? = null,

    @SerializedName("PickRefNo")
    var pickRefNo: List<String>? = null,

    @SerializedName("MRP")
    var mrp: String? = null,

    @SerializedName("MOP")
    var mop: String? = null,

    @SerializedName("SKU")
    var sku: String? = null,

    @SerializedName("SerialNo")
    var serialNo: String? = null,

    @SerializedName("MfgDate")
    var mfgDate: String? = null,

    @SerializedName("Location")
    var location: String? = null,

    @SerializedName("LoadList")
    var loadList: List<LoadDTO>? = null,

    @SerializedName("PalletNo")
    var palletNo: String? = null,

    @SerializedName("UserId")
    var userId: String? = null,

    @SerializedName("IsMaterialDamaged")
    var isMaterialDamaged: Boolean? = null,

    @SerializedName("IsMaterialNotFound")
    var isMaterialNotFound: Boolean? = null,

    @SerializedName("Result")
    var result: String? = null,

    @SerializedName("RequiredQty")
    var requiredQty: String? = null,

    @SerializedName("PickedQty")
    var pickedQty: String? = null,

    @SerializedName("SelectedPickRefNumber")
    var selectedPickRefNumber: String? = null,

    @SerializedName("SelectedLoadSheetNumber")
    var selectedLoadSheetNumber: String? = null,

    @SerializedName("AllowNestedInventoryDispatch")
    var allowNestedInventoryDispatch: Boolean? = null,

    @SerializedName("AllowDispatchOfOLDMRP")
    var allowDispatchOfOLDMRP: Boolean? = null,

    @SerializedName("AllowCrossDocking")
    var allowCrossDocking: Boolean? = null,

    @SerializedName("StrictComplianceToPicking")
    var strictComplianceToPicking: Boolean? = null,

    @SerializedName("AutoReconsileInventoryOnSkip")
    var autoReconsileInventoryOnSkip: Boolean? = null,

    @SerializedName("DockNumber")
    var dockNumber: String? = null,

    @SerializedName("SuggestionID")
    var suggestionID: String? = null,

    @SerializedName("RevertQty")
    var revertQty: String? = null,

    @SerializedName("CustomerCode")
    var customerCode: String? = null,

    @SerializedName("MaterialMasterId")
    var materialMasterId: String? = null,

    @SerializedName("OBDNo")
    var obdNo: String? = null,

    @SerializedName("BatchNo")
    var batchNo: String? = null,

    @SerializedName("ExpDate")
    var expDate: String? = null,

    @SerializedName("AssignedQuantity")
    var assignedQuantity: String? = null,

    @SerializedName("PendingQty")
    var pendingQty: String? = null,

    @SerializedName("AssignedID")
    var assignedID: String? = null,

    @SerializedName("ProjectNo")
    var projectNo: String? = null,

    @SerializedName("SkipReason")
    var skipReason: String? = null,

    @SerializedName("KitId")
    var kitId: String? = null,

    @SerializedName("CartonNo")
    var cartonNo: String? = null,

    @SerializedName("IsDam")
    var isDam: String? = null,

    @SerializedName("HasDis")
    var hasDis: String? = null,

    @SerializedName("Lineno")
    var lineno: String? = null,

    @SerializedName("AccountId")
    var accountId: String? = null,

    @SerializedName("MCode")
    var mCode: String? = null,

    @SerializedName("MDescription")
    var mDescription: String? = null,

    @SerializedName("Qty")
    var qty: String? = null,

    @SerializedName("ToCartonNo")
    var toCartonNo: String? = null,

    @SerializedName("SODetailsID")
    var soDetailsID: String? = null,

    @SerializedName("SOHeaderID")
    var soHeaderID: String? = null,

    @SerializedName("POSOHeaderId")
    var poSOHeaderId: String? = null,

    @SerializedName("SkipQty")
    var skipQty: String? = null,

    @SerializedName("SLoc")
    var sLoc: String? = null,

    @SerializedName("VLPDNumber")
    var vlpdNumber: String? = null,

    @SerializedName("VLPDId")
    var vlpdId: String? = null,

    @SerializedName("LocationId")
    var locationId: String? = null,

    @SerializedName("CartonID")
    var cartonID: String? = null,

    @SerializedName("TransferRequestDetailsId")
    var transferRequestDetailsId: String? = null,

    @SerializedName("TransferRequestId")
    var transferRequestId: String? = null,

    @SerializedName("SLocId")
    var sLocId: String? = null,

    @SerializedName("StorageLocationID")
    var storageLocationID: String? = null,

    @SerializedName("PickedId")
    var pickedId: String? = null,

    @SerializedName("AccountID")
    var accountID: String? = null,

    @SerializedName("Vehicle")
    var vehicle: String? = null,

    @SerializedName("OBDNumber")
    var obdNumber: String? = null,

    @SerializedName("DriverName")
    var driverName: String? = null,

    @SerializedName("DriverNo")
    var driverNo: String? = null,

    @SerializedName("LRnumber")
    var lrNumber: String? = null,

    @SerializedName("TenatID")
    var tenatID: String? = null,

    @SerializedName("SONumber")
    var soNumber: String? = null,

    @SerializedName("PackedQty")
    var packedQty: String? = null,

    @SerializedName("CartonSerialNo")
    var cartonSerialNo: String? = null,

    @SerializedName("PSNID")
    var psnID: String? = null,

    @SerializedName("PSNDetailsID")
    var psnDetailsID: String? = null,

    @SerializedName("PackType")
    var packType: String? = null,

    @SerializedName("PackComplete")
    var packComplete: String? = null,

    @SerializedName("TotalSOCount")
    var totalSOCount: String? = null,

    @SerializedName("ScannedSOCount")
    var scannedSOCount: String? = null,

    @SerializedName("BusinessType")
    var businessType: String? = null,

    @SerializedName("LoadRefNo")
    var loadRefNo: String? = null,

    @SerializedName("CustomerName")
    var customerName: String? = null,

    @SerializedName("CustomerAddress")
    var customerAddress: String? = null,

    @SerializedName("WareHouseID")
    var wareHouseID: String? = null,

    @SerializedName("Status")
    var status: String? = null,

    @SerializedName("SOQty")
    var soQty: String? = null,

    @SerializedName("HUSize")
    var huSize: String? = null,

    @SerializedName("HUNo")
    var huNo: String? = null,

    @SerializedName("IsPicking")
    var isPicking: String? = null,

    @SerializedName("UOM")
    var uom: String? = null,

    @SerializedName("SortingQty")
    var sortingQty: String? = null,

    @SerializedName("PickSerialNumber")
    var pickSerialNumber: String? = null,

    @SerializedName("ScanResult")
    var scanResult: Boolean? = null,

    @SerializedName("ImageURL")
    var imageURL: String? = null,

    @SerializedName("IsCustomLabel")
    var isCustomLabel: String? = null,

    @SerializedName("ActiveStockDetailsId")
    var activeStockDetailsId: Int? = null,

    @SerializedName("IsShipmentClose")
    var isShipmentClose: String? = null,

    @SerializedName("MaterialCode")
    var materialCode: String? = null,

    @SerializedName("AvailableQuantity")
    var availableQuantity: String? = null,

    @SerializedName("TenantID")
    var tenantID: String? = null,

    @SerializedName("CustomerId")
    var customerId: String? = null,

    @SerializedName("InvoiceNumber")
    var invoiceNumber: String? = null,

    var isChecked: Boolean = false
): Parcelable



@Parcelize
data class LoadDTO(
    @SerializedName("VehicleNumber")
    var vehicleNumber: String? = null,

    @SerializedName("DockNumber")
    var dockNumber: String? = null,

    @SerializedName("BoxQty")
    var boxQty: Double? = null,

    @SerializedName("Volume")
    var volume: Double? = null,

    @SerializedName("Weight")
    var weight: Double? = null,

    @SerializedName("LoadSheetNumber")
    var loadSheetNumber: String? = null,

    @SerializedName("LoadedQuantity")
    var loadedQuantity: String? = null,

    @SerializedName("LoadSheetQuantity")
    var loadSheetQuantity: String? = null,

    @SerializedName("CustomerCode")
    var customerCode: String? = null
): Parcelable






