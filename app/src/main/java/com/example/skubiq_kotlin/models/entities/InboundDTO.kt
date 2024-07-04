package com.example.skubiq_kotlin.models.entities

import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap

/**
 * Created by Padmaja.B on 20/12/2018.
 */
data class InboundDTO (
    @SerializedName("UserId")
    var userId: String? = null,

    @SerializedName("InboundID")
    var inboundID: String? = null,

    @SerializedName("StoreRefNo")
    var storeRefNo: String? = null,

    @SerializedName("PalletNo")
    var palletNo: String? = null,

    @SerializedName("AccountID")
    var accountID: String? = null,

    @SerializedName("StorageLocation")
    var storageLocation: String? = null,

    @SerializedName("Result")
    var result: String? = null,

    @SerializedName("Mcode")
    var mcode: String? = null,

    @SerializedName("ReceivedQty")
    var receivedQty: String? = null,

    @SerializedName("ItemPendingQty")
    var itemPendingQty: String? = null,

    @SerializedName("BatchNo")
    var batchNo: String? = null,

    @SerializedName("SerialNo")
    var serialNo: String? = null,

    @SerializedName("MfgDate")
    var mfgDate: String? = null,

    @SerializedName("ExpDate")
    var expDate: String? = null,

    @SerializedName("ProjectRefno")
    var projectRefno: String? = null,

    @SerializedName("Qty")
    var qty: String? = null,

    @SerializedName("LineNo")
    var lineNo: String? = null,

    @SerializedName("HasDisc")
    var hasDisc: String? = null,

    @SerializedName("CartonNo")
    var cartonNo: String? = null,

    @SerializedName("CreatedBy")
    var createdBy: String? = null,

    @SerializedName("IsDam")
    var isDam: String? = null,

    @SerializedName("SkipType")
    var skipType: String? = null,

    @SerializedName("SkipReason")
    var skipReason: String? = null,

    @SerializedName("InvoiceQty")
    var invoiceQty: String? = null,

    @SerializedName("IsOutbound")
    var isOutbound: String? = null,

    @SerializedName("MRP")
    var mRP: String? = null,

    @SerializedName("Dock")
    var dock: String? = null,

    @SerializedName("Entry")
    var entry: List<EntryDTO>? = null,

    @SerializedName("VehicleNo")
    var vehicleNo: String? = null,

    @SerializedName("SupplierInvoiceDetailsID")
    var supplierInvoiceDetailsID: String? = null,

    @SerializedName("HUNo")
    var hUNo: String? = null,

    @SerializedName("HUSize")
    var hUSize: String? = null,

    @SerializedName("WarehouseID")
    var warehouseID: String? = null,

    @SerializedName("TenantID")
    var tenantID: String? = null,

    @SerializedName("UserRole")
    var userRole: String? = null,

    @SerializedName("Location")
    var location: String? = null,

    @SerializedName("PutAwayQty")
    var putAwayQty: String? = null,

    @SerializedName("TotalPalletNo")
    var totalPalletNo: String? = null,

    @SerializedName("volume")
    var volume: String? = null,

    @SerializedName("weight")
    var weight: String? = null,

    @SerializedName("TransferRefNo")
    var transferRefNo: String? = null,

    @SerializedName("MaterialCode")
    var materialCode: String? = null,

    @SerializedName("Quantity")
    var quantity: String? = null,

    @SerializedName("FromLocation")
    var fromLocation: String? = null,

    @SerializedName("ContainerCode")
    var containerCode: String? = null,

    @SerializedName("TransferRequestDetailsId")
    var transferRequestDetailsId: Int? = null,

    @SerializedName("ToLocation")
    var toLocation: String? = null,

    @SerializedName("TransferType")
    var transferType: String? = null,

    @SerializedName("ImageURL")
    var imageURL: String? = null,


    @SerializedName("IsCustomLabel")
    var isCustomLabel: String? = null,

    @SerializedName("IsShipmentClose")
    var isShipmentClose: String? = null,

    @SerializedName("MaterialShortDescription")
    var materialShortDescription: String? = null,

    @SerializedName("MaterialMasterID")
    var materialMasterID: Int = 0,

    @SerializedName("SupplierId")
    var supplierId: Int = 0,

    @SerializedName("Sku")
    var sku: String? = null,

    @SerializedName("MDescription")
    var mDescription: String? = null,

    @SerializedName("Uom")
    var uom: String? = null,

    @SerializedName("UomQty")
    var uomQty: String? = null,

    @SerializedName("InvoiceNo")
    var invoiceNo: String? = null,

    @SerializedName("PoNumber")
    var poNumber: String? = null,

    @SerializedName("LocationID")
    var locationID: String? = null,

    @SerializedName("ActiveStockDetailsId")
    var activeStockDetailsId: String? = null,

    @SerializedName("SupplierCode")
    var supplierCode: String? = null,

    @SerializedName("SupplierName")
    var supplierName: String? = null,

    @SerializedName("InboundNumber")
    var inboundNumber: String? = null,

    @SerializedName("PrintDesc")
    var printDesc: String? = null,

    @SerializedName("TenantBarcodeTypeID")
    var tenantBarcodeTypeID: String? = null,

    @SerializedName("ZPLScript")
    var zPLScript: String? = null

)
