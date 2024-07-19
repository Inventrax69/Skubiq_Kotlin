package com.example.skubiq_kotlin.view.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.cipherlab.barcode.GeneralString
import com.example.skubiq_kotlin.R
import com.example.skubiq_kotlin.constants.EndpointConstants
import com.example.skubiq_kotlin.databinding.FragmentGoodsInBinding
import com.example.skubiq_kotlin.models.InboundDTO
import com.example.skubiq_kotlin.models.ScanDTO
import com.example.skubiq_kotlin.models.WMSCoreMessage
import com.example.skubiq_kotlin.models.WMSCoreMessageRequest
import com.example.skubiq_kotlin.models.WMSExceptionMessage
import com.example.skubiq_kotlin.utility.Common
import com.example.skubiq_kotlin.utility.SharedPreferencesUtil
import com.example.skubiq_kotlin.utils.Constants
import com.example.skubiq_kotlin.utils.DialogUtils

import com.example.skubiq_kotlin.utils.ScanValidator
import com.example.skubiq_kotlin.utils.SoundUtils
import com.example.skubiq_kotlin.viewmodels.ScanViewModel
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.internal.LinkedTreeMap
import com.inventrax.skubiq.util.ProgressDialogUtils
import okio.IOException
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.io.ByteArrayOutputStream

class OBDPickingDetailsFragment : Fragment(), View.OnClickListener {

    private lateinit var fragmentGoodsInBinding: FragmentGoodsInBinding
    private lateinit var common: Common
    private var userID: String? = null
    private var accountId: String? = null
    var warehouseId: kotlin.String? = null
    private var inboundID: String? = null

    //pallet
    private var tenantID: String? = null
    private var scanInput: String? = null
    private var sharedPreferencesUtil: SharedPreferencesUtil? = null
    private val scanViewModel: ScanViewModel by sharedViewModel()
    private var response = WMSCoreMessage()
    private val isInboundCompleted = false
    private var isDockScanned: kotlin.Boolean = false
    private var isContanierScanned: kotlin.Boolean = false
    private var isRsnScanned: kotlin.Boolean = false
    private var isMcodeScanned: kotlin.Boolean = false
    private var isCustomBarcode: kotlin.Boolean = false
    private var isSerialNUM: kotlin.Boolean = false
    private var Materialcode: String? = null
    private var huNo: kotlin.String? = ""
    private var huSize: kotlin.String? = ""
    private val userId: String? = null
    private var scanType: kotlin.String? = null
    private var lineNo: kotlin.String? = null
    private var userRoll: kotlin.String? = null
    private var receivedQty: String? = null
    private var pendingQty: kotlin.String? = null
    private var dock: kotlin.String? = ""
    private var vehicleNo: kotlin.String? = ""
    var supplierInvoiceDetailsId: String? = null
    private val etSerial: EditText? = null
    private var etMfgDate: EditText? = null
    private var etExpDate: EditText? = null
    private var etBatch: EditText? = null
    private var etPrjRef: EditText? = null
    private var etQty: EditText? = null
    private var etKidID: EditText? = null
    private var etMRP: EditText? = null
    private var lblHu: EditText? = null
    private var etSKUDesc: EditText? = null
    private var storeRefNo: String? = null
    var storageLoc: String? = null
    var inboundId: kotlin.String? = null
    var invoiceQty: kotlin.String? = null
    var recQty: kotlin.String? = ""
    private val first_pic_id = 123
    private var firstImageString: String? = null
    var qrFormatString: String = ""
    private var gson: Gson = Gson()
    private lateinit var filter: IntentFilter


    private val myDataReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val scanner = intent.getStringExtra(GeneralString.BcReaderData)  // Scanned Barcode info
            scanner?.trim()?.let {
                processscannedInfo(it)
            }
        }
    }
    fun myScannedData(context: Context, scannedData: String) {
        processscannedInfo(scannedData)
    }
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentGoodsInBinding = FragmentGoodsInBinding.inflate(layoutInflater)
        //val view = inflater.inflate(R.layout.fragment_goods_in, container, false)
        val view = fragmentGoodsInBinding.root

        common = Common()
        sharedPreferencesUtil = context?.let { SharedPreferencesUtil(it, "Loginactivity") }
        userID = sharedPreferencesUtil?.getString("RefUserId", "")
        accountId = sharedPreferencesUtil?.getString("AccountId", "")
        warehouseId = sharedPreferencesUtil?.getString("WarehouseID", "")
        scanType = sharedPreferencesUtil?.getString("scanType", "")
        userRoll = sharedPreferencesUtil?.getString("UserRole", "")


        val args: OBDPickingDetailsFragmentArgs by navArgs()

        args.let {

            try {
                fragmentGoodsInBinding.lblStoreRefNo.text = it.outboundDetails.obdNo

                inboundID = it.outboundDetails.outboundID
                if (it.outboundDetails.isCustomLabel.equals("1")){
                    isCustomBarcode=true
                }else{
                    isCustomBarcode=false
                }

                // supplierInvoiceDetailsId = it.inboundDetails.SupplierInvoiceDetailsID.toString()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fragmentGoodsInBinding.spinnerSelectSloc.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                storageLoc = fragmentGoodsInBinding.spinnerSelectSloc.getSelectedItem().toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        })

        fragmentGoodsInBinding.btnReceive.setOnClickListener(this)
        fragmentGoodsInBinding.btnClear.setOnClickListener(this)
        fragmentGoodsInBinding.ivCaptureImage.setOnClickListener(this)

        val rtIntentEnable = Intent("sw.reader.decode.require").apply {
            putExtra("Enable", true)
        }
        activity?.sendBroadcast(rtIntentEnable)

        filter = IntentFilter().apply {
            addAction("sw.reader.decode.complete")
        }
        activity?.registerReceiver(myDataReceiver, filter)
        getSLocs()
        attachGetSLocObserver()

        return view
    }



        fun processscannedInfo(scannedData: String) {
        //Toast.makeText(context,scannedData,Toast.LENGTH_LONG).show()

        if ((requireActivity().findViewById<View>(R.id.drawer_layout) as DrawerLayout).isDrawerOpen(
                GravityCompat.START
            )
        ) {
            return
        }

        if (ProgressDialogUtils.isProgressActive() || Common().isPopupActive) {
            common.showUserDefinedAlertType(
                requireActivity().resources.getString(R.string.EMC_0082),
                requireActivity(), requireContext(), "Warning"
            )
            return
        }

        if (!Common().isPopupActive && !isInboundCompleted) {
            if (!ProgressDialogUtils.isProgressActive()) {

                if (!isDockScanned) {
                    validateLocation(scannedData)
                } else {
                    if (!isContanierScanned) {
                        validateEmptyPallet(scannedData)
                    } else {
                        if (!isCustomBarcode) {
                            validateMaterial(scannedData)
                        } else {
                            if (!isMcodeScanned) {
                                qrFormatString = ScanValidator.QR_FORMAT_STRING
                                qrFormatString = ScanValidator.replaceQrPlaceholder(
                                    qrFormatString,
                                    scannedData,
                                    "(01)"
                                )
                                fragmentGoodsInBinding.lblScannedSku.setText(scannedData)
                                showDialogToScanSerialNumber(qrFormatString)
                            } else {
                                qrFormatString = ScanValidator.replaceQrPlaceholder(
                                    qrFormatString,
                                    scannedData,
                                    "(21)"
                                )
                                validateMaterial(qrFormatString)
                            }
                        }
                    }
                }

            } else {
                if (!Common().isPopupActive) {
                    common.showUserDefinedAlertType(
                        requireActivity().resources.getString(R.string.EMC_0080),
                        requireActivity(), requireContext(), "Warning"
                    )
                }
                SoundUtils.alertWarning(requireActivity(), requireContext())
            }
        }
    }



    private fun validateLocation(scannedData: String){

        //Constants.showCustomProgressDialog(requireActivity())
        ProgressDialogUtils.showProgressDialog(resources.getString(R.string.please_wait))

        var wmsCoreMessageRequest: WMSCoreMessageRequest = WMSCoreMessageRequest()

        wmsCoreMessageRequest= context?.let { common.SetAuthentication(EndpointConstants.ScanDTO, it) }!!

        val scanDTO = ScanDTO()
        with(scanDTO){
            UserID = userID.toString()
            AccountID = accountId?.toInt()
          //  WarehouseID = warehouseId.toString()
            ScanInput=scannedData
            InboundID=inboundID
        }
        with(wmsCoreMessageRequest){
            EntityObject = scanDTO
        }

        scanViewModel.validateLocation(wmsCoreMessageRequest)

        scanViewModel.scanListData.observe(viewLifecycleOwner, Observer {
            it.apply {

                if (it.isNotEmpty()){
                    try {
                        Constants.hideProgressDialog()
                        response = scanViewModel.parseJsonToMyModel(this)

                        if (response.Type!!.equals("Exception")) {

                            // you have handle
                            //Constants.hideProgressDialog()
                            ProgressDialogUtils.closeProgressDialog()

                            var entityObject = response.EntityObject as List<*>

                            //inboundDTO = response.EntityObject as InboundDTO

                            for (entity in entityObject) {
                                if (entity is Map<*, *>) {
                                    val map = entity as Map<*, *>
                                    val jsonElement: JsonElement = gson.toJsonTree(map)
                                    val pojo: WMSExceptionMessage = gson.fromJson(jsonElement, WMSExceptionMessage::class.java)
                                    activity?.let { it1 -> context?.let { it2 ->
                                        common.showAlertType(pojo, it1,
                                            it2
                                        )
                                    } }
                                }

                            }
                        } else {

                            //Constants.hideProgressDialog()
                            ProgressDialogUtils.closeProgressDialog()

                            //var entityObject = response.EntityObject as List<*>
                            var _lResult: LinkedTreeMap<*, *> = LinkedTreeMap<Any, Any>()
                            _lResult = response.EntityObject as LinkedTreeMap<*, *>
                            //val map = _lResult.entries as Map<*, *>
                            val jsonElement: JsonElement = gson.toJsonTree(_lResult)
                            val scanDTO1: ScanDTO = gson.fromJson(jsonElement, ScanDTO::class.java)

                            // val scanDTO1 = ScanDTO(_lResult.entries.toString())


                            if (scanDTO1.ScanResult == true) {
                                fragmentGoodsInBinding.etDock.setText(scannedData)
                                isDockScanned = true
                                fragmentGoodsInBinding.cvScanDock.setCardBackgroundColor(resources.getColor(R.color.white))
                                fragmentGoodsInBinding.ivScanDock.setImageResource(R.drawable.check)
                            } else {
                                isDockScanned = false
                                fragmentGoodsInBinding.etDock.setText("")
                                fragmentGoodsInBinding.cvScanDock.setCardBackgroundColor(resources.getColor(R.color.white))
                                fragmentGoodsInBinding.ivScanDock.setImageResource(R.drawable.warning_img)
                                common.showUserDefinedAlertType(
                                    requireActivity().resources.getString(R.string.EMC_0019),
                                    requireActivity(), requireContext(), "Warning"
                                )
                            }
                        }
                    }catch (ex : Exception){
                        Constants.showAlertDialog(requireActivity(), resources.getString(R.string.EMC_0173))
                    }
                }else{
                    //Constants.hideProgressDialog()
                    ProgressDialogUtils.closeProgressDialog()
                    Constants.showAlertDialog(requireActivity(), resources.getString(R.string.EMC_0173))
                }
            }
        })

    }
    private fun validateEmptyPallet(scannedData: String){

        //Constants.showCustomProgressDialog(requireActivity())
        ProgressDialogUtils.showProgressDialog(resources.getString(R.string.please_wait))

        var wmsCoreMessageRequest: WMSCoreMessageRequest = WMSCoreMessageRequest()

        wmsCoreMessageRequest= context?.let { common.SetAuthentication(EndpointConstants.ScanDTO, it) }!!

        val scanDTO = ScanDTO()
        with(scanDTO){
            UserID = userID.toString()
            AccountID = accountId?.toInt()
            ScanInput = scannedData
            InboundID = inboundID
            //WarehouseID = warehouseId.toString()
            //TenantID = tenantID


        }
        with(wmsCoreMessageRequest){
            EntityObject = scanDTO
        }

        scanViewModel.validateEmptyPallet(wmsCoreMessageRequest)

        scanViewModel.scanPalletListData.observe(viewLifecycleOwner, Observer {
            it.apply {

                if (it.isNotEmpty()){
                    try {
                        //Constants.hideProgressDialog()
                        ProgressDialogUtils.closeProgressDialog()
                        response = scanViewModel.parseJsonToMyModel(this)

                        if (response.Type!!.equals("Exception")) {

                            // you have handle
                            Constants.hideProgressDialog()

                            var entityObject = response.EntityObject as List<*>

                            //inboundDTO = response.EntityObject as InboundDTO

                            for (entity in entityObject) {
                                if (entity is Map<*, *>) {
                                    val map = entity as Map<*, *>
                                    val jsonElement: JsonElement = gson.toJsonTree(map)
                                    val pojo: WMSExceptionMessage = gson.fromJson(jsonElement, WMSExceptionMessage::class.java)
                                    activity?.let { it1 -> context?.let { it2 ->
                                        common.showAlertType(pojo, it1,
                                            it2
                                        )
                                    } }
                                }

                            }
                        } else {

                            //Constants.hideProgressDialog()
                            ProgressDialogUtils.closeProgressDialog()

                            var _lResult: LinkedTreeMap<*, *> = LinkedTreeMap<Any, Any>()
                            _lResult = response.EntityObject as LinkedTreeMap<*, *>
                            //val map = _lResult.entries as Map<*, *>
                            val jsonElement: JsonElement = gson.toJsonTree(_lResult)
                            val scanDTO1: ScanDTO = gson.fromJson(jsonElement, ScanDTO::class.java)

                           /* var _lResult: LinkedTreeMap<*, *> = LinkedTreeMap<Any, Any>()
                            _lResult = response.EntityObject as LinkedTreeMap<*, *>
*/
                           // val scanDTO1 = ScanDTO(_lResult.entries.toString())

                            if (scanDTO1.ScanResult == true) {

                                isContanierScanned = true
                                fragmentGoodsInBinding.etPallet.setText(scannedData.toString())
                                fragmentGoodsInBinding.cvScanPallet.setCardBackgroundColor(resources.getColor(R.color.white))
                                fragmentGoodsInBinding.ivScanPallet.setImageResource(R.drawable.check)
                                fragmentGoodsInBinding.lblPalletNetWeight.setText(scanDTO1.Weight!!.toString())
                                fragmentGoodsInBinding.lblPalletHt.setText(scanDTO1.volume!!.toString())
                            } else {
                                isContanierScanned = false
                                fragmentGoodsInBinding.etPallet.setText("")
                                fragmentGoodsInBinding.cvScanPallet.setCardBackgroundColor(resources.getColor(R.color.white))
                                fragmentGoodsInBinding.ivScanPallet.setImageResource(R.drawable.warning_img)
                                common.showUserDefinedAlertType(
                                    scanDTO1.Message,
                                    requireActivity(),
                                    requireContext(),
                                    "Warning"
                                )
                                /*                                    etLocationTo.setText("");
                                cvScanToLoc.setCardBackgroundColor(getResources().getColor(R.color.white));
                                ivScanToLoc.setImageResource(R.drawable.warning_img);
                                common.showUserDefinedAlertType(getActivity().getResources().getString(R.string.EMC_0010), getActivity(), getContext(), "Warning");*/
                            }
                        }
                    }catch (ex : Exception){
                        Constants.showAlertDialog(requireActivity(), ex.toString())
                    }
                }else{
                    //Constants.hideProgressDialog()
                    ProgressDialogUtils.closeProgressDialog()
                    Constants.showAlertDialog(requireActivity(), resources.getString(R.string.EMC_0173))
                }
            }
        })

    }
    private fun validateMaterial(scannedData: String){

        //Constants.showCustomProgressDialog(requireActivity())
        ProgressDialogUtils.showProgressDialog(resources.getString(R.string.please_wait))

        var wmsCoreMessageRequest: WMSCoreMessageRequest = WMSCoreMessageRequest()

        wmsCoreMessageRequest= context?.let { common.SetAuthentication(EndpointConstants.ScanDTO, it) }!!

        val scanDTO = ScanDTO()
        with(scanDTO){
            UserID = userID.toString()
            AccountID = accountId?.toInt()
            WarehouseID = warehouseId.toString()
            InboundID = inboundID
            ScanInput = scannedData

        }
        with(wmsCoreMessageRequest){
            EntityObject = scanDTO
        }

        scanViewModel.validateMaterial(wmsCoreMessageRequest)

        scanViewModel.scanSKUListData.observe(viewLifecycleOwner, Observer {
            it.apply {

                if (it.isNotEmpty()){
                    try {
                        //Constants.hideProgressDialog()
                        ProgressDialogUtils.closeProgressDialog()
                        response = scanViewModel.parseJsonToMyModel(this)

                        if (response.Type!!.equals("Exception")) {

                            // you have handle
                            //Constants.hideProgressDialog()
                            ProgressDialogUtils.closeProgressDialog()

                            var entityObject = response.EntityObject as List<*>

                            //inboundDTO = response.EntityObject as InboundDTO

                            for (entity in entityObject) {
                                if (entity is Map<*, *>) {
                                    val map = entity as Map<*, *>
                                    val jsonElement: JsonElement = gson.toJsonTree(map)
                                    val pojo: WMSExceptionMessage = gson.fromJson(jsonElement, WMSExceptionMessage::class.java)
                                    activity?.let { it1 -> context?.let { it2 ->
                                        common.showAlertType(pojo, it1,
                                            it2
                                        )
                                    } }
                                }

                            }
                        } else {

                           // Constants.hideProgressDialog()
                            ProgressDialogUtils.closeProgressDialog()

                            var _lResult: LinkedTreeMap<*, *> = LinkedTreeMap<Any, Any>()
                            _lResult = response.EntityObject as LinkedTreeMap<*, *>
                            //val map = _lResult.entries as Map<*, *>
                            val jsonElement: JsonElement = gson.toJsonTree(_lResult)
                            val scanDTO1: ScanDTO = gson.fromJson(jsonElement, ScanDTO::class.java)

                            if (scanDTO1.ScanResult == true) {
                                /* ----For RSN reference----
                                       0 Sku|1 BatchNo|2 SerialNO|3 MFGDate|4 EXpDate|5 ProjectRefNO|6 Kit Id|7 line No|8 MRP ---- For SKU with 9 MSP's

                                       0 Sku|1 BatchNo|2 SerialNO|3 KitId|4 lineNo  ---- For SKU with 5 MSP's   */
                                /*
                                // Eg. : ToyCar|1|bat1|ser123|12/2/2018|12/2/2019|0|001*/

                                isMcodeScanned = true
                                fragmentGoodsInBinding.cvScanSKU.setCardBackgroundColor(resources.getColor(R.color.white))
                                fragmentGoodsInBinding.ivScanSKU.setImageResource(R.drawable.check)

                                /*    if (scannedData.split("[|]").length != 5) {*/
                                Materialcode = scanDTO1.SkuCode
                                fragmentGoodsInBinding.etBatch.setText(scanDTO1.Batch)
                                fragmentGoodsInBinding.etSerial.setText(scanDTO1.SerialNumber)
                                if (!fragmentGoodsInBinding.etSerial.getText().toString().isEmpty()) {
                                    isSerialNUM = true
                                }
                                fragmentGoodsInBinding.etMfgDate.setText(scanDTO1.MfgDate)
                                fragmentGoodsInBinding.etExpDate.setText(scanDTO1.ExpDate)
                                fragmentGoodsInBinding.etProjectRef.setText(scanDTO1.PrjRef)
                                fragmentGoodsInBinding.etKitId.setText(scanDTO1.KitID)
                                fragmentGoodsInBinding.etMrp.setText(scanDTO1.Mrp)
                                lineNo= scanDTO1.LineNumber
                                supplierInvoiceDetailsId = scanDTO1.SupplierInvoiceDetailsID
                                huNo = scanDTO1.HUNo
                                huSize = scanDTO1.HUSize
                                fragmentGoodsInBinding.etSKUDesc.setText(scanDTO1.Description)

                                fragmentGoodsInBinding.lblHu.setText(huNo + "/" + huSize)
                                fragmentGoodsInBinding.lblScannedSku.setText(Materialcode)

                                if (etKidID?.getText().toString() == "0") {
                                    etKidID?.setText("")
                                }

                                //   etMRP.setText(scannedData.split("[|]")[7]);


                                /*                                    } else {
                                    Materialcode = scannedData.split("[|]")[0];
                                    etBatch.setText(scannedData.split("[|]")[1]);
                                    etSerial.setText(scannedData.split("[|]")[2]);
                                    etKidID.setText(scannedData.split("[|]")[3]);
                                    lineNo = scannedData.split("[|]")[4];
                                }*/
                                if (scanType == "Auto") {
                                    etQty?.setText("1")
                                    getReceivedQTY() // To get the pending and received quantities
                                    //attachedReceivedQTYObserver(scannedData)
                                    return@Observer
                                } else {
                                    // for Manual mode
                                    etQty?.setEnabled(true)
                                    fragmentGoodsInBinding.btnReceive.setEnabled(true)
                                    /* btnReceive.setTextColor(getResources().getColor(R.color.white));
                                        btnReceive.setBackgroundResource(R.drawable.button_shape);*/
                                    fragmentGoodsInBinding.lblInboundQty.setText("")
                                    getreceivedQTY() // To get the pending and received quantities
                                    //attachedreceivedQTYObserver(scannedData)
                                }
                            } else {
                                isMcodeScanned = false
                                isRsnScanned = false
                                fragmentGoodsInBinding.lblScannedSku.setText("")
                                fragmentGoodsInBinding.cvScanSKU.setCardBackgroundColor(resources.getColor(R.color.white))
                                fragmentGoodsInBinding.ivScanSKU.setImageResource(R.drawable.warning_img)
                                common.showUserDefinedAlertType(
                                    scanDTO1.Message, requireActivity(),
                                    requireContext(), "Warning"
                                )
                            }


                        }
                    }catch (ex : Exception){
                        Constants.showAlertDialog(requireActivity(), resources.getString(R.string.EMC_0173))
                    }
                }else{
                    Constants.hideProgressDialog()
                    Constants.showAlertDialog(requireActivity(), resources.getString(R.string.EMC_0173))
                }
            }
        })
    }
    private fun getReceivedQTY(){

        //Constants.showCustomProgressDialog(requireActivity())
        ProgressDialogUtils.showProgressDialog(resources.getString(R.string.please_wait))

        var wmsCoreMessageRequest: WMSCoreMessageRequest = WMSCoreMessageRequest()

        wmsCoreMessageRequest= context?.let { common.SetAuthentication(EndpointConstants.Inbound, it) }!!

        val inboundDTO = InboundDTO()
        with(inboundDTO){
            Mcode = Materialcode
            Storerefno = storeRefNo
            InboundID = inboundID
            VehicleNo = vehicleNo
            Lineno = lineNo
            BatchNo = fragmentGoodsInBinding.etBatch.text.toString()
            SerialNo = fragmentGoodsInBinding.etSerial.text.toString()
            MfgDate = fragmentGoodsInBinding.etMfgDate.text.toString()
            ExpDate = fragmentGoodsInBinding.etExpDate.text.toString()
            ProjectRefno = fragmentGoodsInBinding.etProjectRef.text.toString()
            MRP = fragmentGoodsInBinding.etMrp.text.toString()
            MaterialMasterID = 0
            HUSize = huSize
            HUNo = huNo
            AccountID = accountId
            UserId = userID

            if (supplierInvoiceDetailsId==null || supplierInvoiceDetailsId == "" || supplierInvoiceDetailsId!!.isEmpty()){
                inboundDTO.SupplierInvoiceDetailsID = "0"
            } else {
                inboundDTO.SupplierInvoiceDetailsID = supplierInvoiceDetailsId
            }


        }
        with(wmsCoreMessageRequest){
            EntityObject = inboundDTO
        }

        scanViewModel.getReceivedQTY(wmsCoreMessageRequest)

        scanViewModel.receiveQTYListData.observe(viewLifecycleOwner, Observer {
            it.apply {

                if (it.isNotEmpty()){
                    try {
                        //Constants.hideProgressDialog()
                        ProgressDialogUtils.closeProgressDialog()
                        response = scanViewModel.parseJsonToMyModel(this)

                        if (response.Type!!.equals("Exception")) {

                            // you have handle
                            Constants.hideProgressDialog()

                            var entityObject = response.EntityObject as List<*>

                            //inboundDTO = response.EntityObject as InboundDTO

                            for (entity in entityObject) {
                                if (entity is Map<*, *>) {
                                    val map = entity as Map<*, *>
                                    val jsonElement: JsonElement = gson.toJsonTree(map)
                                    val pojo: WMSExceptionMessage = gson.fromJson(jsonElement, WMSExceptionMessage::class.java)
                                    activity?.let { it1 -> context?.let { it2 ->
                                        common.showAlertType(pojo, it1,
                                            it2
                                        )
                                    } }
                                }

                            }
                        } else {
                            //Constants.hideProgressDialog()
                            ProgressDialogUtils.closeProgressDialog()
                            var entityobject = response.EntityObject as List<*>
                            if (entityobject.size>0) {

                                val map = entityobject.first() as Map<String, Any>
                                val jsonElement: JsonElement = gson.toJsonTree(map)
                                val inboundDTO: InboundDTO =
                                    gson.fromJson(jsonElement, InboundDTO::class.java)
                                val receivedQty = inboundDTO.ReceivedQty
                                val pendingQty = inboundDTO.ItemPendingQty

                                // Preventing excess receiving in auto mode
                                fragmentGoodsInBinding.lblInboundQty.text = "$receivedQty/$pendingQty"

                                if (receivedQty == pendingQty) {
                                    etQty?.setText("")
                                    common.showUserDefinedAlertType(
                                        getActivity()?.resources?.getString(R.string.EMC_0075).orEmpty(),
                                        requireActivity(),
                                        requireContext(),
                                        "Success"
                                    )
                                } else {
                                    fragmentGoodsInBinding.cvScanSKU.setCardBackgroundColor(getResources().getColor(R.color.white))
                                    fragmentGoodsInBinding.ivScanSKU.setImageResource(R.drawable.check)
                                    validateRSNAndReceive()
                                }
                            }
                        }
                    }catch (ex : Exception){
                        Constants.showAlertDialog(requireActivity(), resources.getString(R.string.EMC_0173))
                    }
                }else{
                    //Constants.hideProgressDialog()
                    ProgressDialogUtils.closeProgressDialog()
                    Constants.showAlertDialog(requireActivity(), resources.getString(R.string.EMC_0173))
                }
            }
        })
    }
    private fun getreceivedQTY(){

        //Constants.showCustomProgressDialog(requireActivity())
        ProgressDialogUtils.showProgressDialog(resources.getString(R.string.please_wait))

        var wmsCoreMessageRequest: WMSCoreMessageRequest = WMSCoreMessageRequest()

        wmsCoreMessageRequest= context?.let { common.SetAuthentication(EndpointConstants.Inbound, it) }!!

        val inboundDTO = InboundDTO()
        with(inboundDTO){
            Mcode = Materialcode
            Storerefno = storeRefNo
            InboundID = inboundID
            VehicleNo = vehicleNo
            Lineno = lineNo
            BatchNo = fragmentGoodsInBinding.etBatch.text.toString()
            SerialNo = fragmentGoodsInBinding.etSerial.text.toString()
            MfgDate = fragmentGoodsInBinding.etMfgDate.text.toString()
            ExpDate = fragmentGoodsInBinding.etExpDate.text.toString()
            ProjectRefno = fragmentGoodsInBinding.etProjectRef.text.toString()
            MRP = fragmentGoodsInBinding.etMrp.text.toString()
            MaterialMasterID = 0
            HUSize = huSize
            HUNo = huNo
            AccountID = accountId
            UserId = userID

            if (supplierInvoiceDetailsId == null || supplierInvoiceDetailsId!!.isEmpty() || supplierInvoiceDetailsId==""){
                inboundDTO.SupplierInvoiceDetailsID = "0"
            } else {
                inboundDTO.SupplierInvoiceDetailsID = supplierInvoiceDetailsId
            }


        }
        with(wmsCoreMessageRequest){
            EntityObject = inboundDTO
        }

        scanViewModel.getReceivedQTY(wmsCoreMessageRequest)

        scanViewModel.receiveQTYListData.observe(viewLifecycleOwner, Observer {
            it.apply {

                if (it.isNotEmpty()){
                    try {
                        //Constants.hideProgressDialog()
                        ProgressDialogUtils.closeProgressDialog()
                        response = scanViewModel.parseJsonToMyModel(this)

                        if (response.Type!!.equals("Exception")) {

                            // you have handle
                            //Constants.hideProgressDialog()
                            ProgressDialogUtils.closeProgressDialog()

                            var entityObject = response.EntityObject as List<*>

                            //inboundDTO = response.EntityObject as InboundDTO

                            for (entity in entityObject) {
                                if (entity is Map<*, *>) {
                                    val map = entity as Map<*, *>
                                    val jsonElement: JsonElement = gson.toJsonTree(map)
                                    val pojo: WMSExceptionMessage = gson.fromJson(jsonElement, WMSExceptionMessage::class.java)
                                    activity?.let { it1 -> context?.let { it2 ->
                                        common.showAlertType(pojo, it1,
                                            it2
                                        )
                                    } }
                                }

                            }
                        } else {

                            //Constants.hideProgressDialog()
                            ProgressDialogUtils.closeProgressDialog()
                            var entityobject = response.EntityObject as List<*>
                            if (entityobject.size>0) {

                                val map = entityobject.first() as Map<String, Any>
                                val jsonElement: JsonElement = gson.toJsonTree(map)
                                val inboundDTO: InboundDTO =
                                    gson.fromJson(jsonElement, InboundDTO::class.java)
                                val receivedQty = inboundDTO.ReceivedQty
                                val pendingQty = inboundDTO.ItemPendingQty

                                // Preventing excess receiving in auto mode
                                fragmentGoodsInBinding.lblInboundQty.text = "$receivedQty/$pendingQty"

                                if (receivedQty == pendingQty) {
                                    isMcodeScanned = false
                                    isRsnScanned = false
                                    etQty!!.setText("")
                                    common.showUserDefinedAlertType(
                                        requireActivity().resources.getString(R.string.EMC_0075),
                                        requireActivity(), requireContext(), "Success"
                                    )
                                } else {
                                    isRsnScanned = true
                                    isMcodeScanned = true
                                    fragmentGoodsInBinding.cvScanSKU.setCardBackgroundColor(resources.getColor(R.color.white))
                                    fragmentGoodsInBinding.ivScanSKU.setImageResource(R.drawable.check)

                                    SoundUtils.alertWarning(requireActivity(), requireContext())
                                    DialogUtils.showAlertDialog(
                                        activity,
                                        requireActivity().resources.getString(R.string.EMC_0073)
                                    )
                                }
                            }

                        }
                    }catch (ex : Exception){
                        Constants.showAlertDialog(requireActivity(), resources.getString(R.string.EMC_0173))
                    }
                }else{
                    //Constants.hideProgressDialog()
                    ProgressDialogUtils.closeProgressDialog()
                    Constants.showAlertDialog(requireActivity(), resources.getString(R.string.EMC_0173))
                }
            }
        })
    }
    fun validateRSNAndReceive() {

        if (fragmentGoodsInBinding.etQty!!.text.toString().isEmpty()) {
            common.showUserDefinedAlertType(
                requireActivity().resources.getString(R.string.EMC_0067),
                requireActivity(), requireContext(), "Warning"
            )
            return
        }

        if (fragmentGoodsInBinding.etQty!!.text.toString() == "0") {
            common.showUserDefinedAlertType(
                requireActivity().resources.getString(R.string.EMC_0068),
                requireActivity(), requireContext(), "Warning"
            )
            return
        }
        if (!fragmentGoodsInBinding.etSerial!!.text.toString().isEmpty()) {
            val enteredqty = fragmentGoodsInBinding.etQty!!.text.toString().toDouble()
            if (enteredqty > 1) {
                common.showUserDefinedAlertType(
                    requireActivity().resources.getString(R.string.EMC_0066),
                    requireActivity(), requireContext(), "Warning"
                )
                return
            }
        }

        try {
            updateReceiveItemForHHT()
        }catch (ex : Exception){
            //handleException(ex, "003_04")
            Constants.hideProgressDialog()
            DialogUtils.showAlertDialog(activity, ex.toString())
        }
    }
    private fun updateReceiveItemForHHT(){

        //Constants.showCustomProgressDialog(requireActivity())
        ProgressDialogUtils.showProgressDialog(resources.getString(R.string.please_wait))

        var wmsCoreMessageRequest: WMSCoreMessageRequest = WMSCoreMessageRequest()

        wmsCoreMessageRequest= context?.let { common.SetAuthentication(EndpointConstants.Inbound, it) }!!

        val inboundDTO = InboundDTO()
        with(inboundDTO){
            Mcode = Materialcode
            Storerefno = storeRefNo
            CartonNo = fragmentGoodsInBinding.etPallet.text.toString()
            StorageLocation = storageLoc
            IsDam = fragmentGoodsInBinding.cbDescripency.isChecked().toString()
            ImageURL = firstImageString
            if (scanType == "Manual") {
                inboundDTO.Qty = fragmentGoodsInBinding.etQty!!.text.toString()
            } else {
                inboundDTO.Qty = "1"
            }
            BatchNo = fragmentGoodsInBinding.etBatch.text.toString()
            SerialNo = fragmentGoodsInBinding.etSerial.text.toString()
            MfgDate = fragmentGoodsInBinding.etMfgDate.text.toString()
            ExpDate = fragmentGoodsInBinding.etExpDate.text.toString()
            ProjectRefno = fragmentGoodsInBinding.etProjectRef.text.toString()
            userRole  = userRoll
            MRP = fragmentGoodsInBinding.etMrp.text.toString()
            HUSize = huSize
            HUNo = huNo
            AccountID = accountId
            UserId = userID

            if (fragmentGoodsInBinding.cbDescripency.isChecked().toString() == "true") {
                inboundDTO.HasDisc = "1"
            } else {
                inboundDTO.HasDisc = "0"
            }


            if (supplierInvoiceDetailsId==null || supplierInvoiceDetailsId == "" || supplierInvoiceDetailsId!!.isEmpty()){
                inboundDTO.SupplierInvoiceDetailsID = "0"
            } else {
                inboundDTO.SupplierInvoiceDetailsID = supplierInvoiceDetailsId
            }

            Lineno = lineNo
            InboundID = inboundID
            MRP = fragmentGoodsInBinding.etMrp.text.toString()
            IsDam = 0.toString()
            Dock = fragmentGoodsInBinding.etDock.text.toString()
            VehicleNo = vehicleNo

        }
        with(wmsCoreMessageRequest){
            EntityObject = inboundDTO
        }

        scanViewModel.updateReceiveItemForHHT(wmsCoreMessageRequest)

        scanViewModel.updateListData.observe(viewLifecycleOwner, Observer {
            it.apply {

                if (it.isNotEmpty()){
                    try {
                        Constants.hideProgressDialog()
                        response = scanViewModel.parseJsonToMyModel(this)

                        if (response.Type!!.equals("Exception")) {

                            // you have handle
                            //Constants.hideProgressDialog()
                            ProgressDialogUtils.closeProgressDialog()

                            var entityObject = response.EntityObject as List<*>

                            //inboundDTO = response.EntityObject as InboundDTO

                            for (entity in entityObject) {
                                if (entity is Map<*, *>) {
                                    val map = entity as Map<*, *>
                                    val jsonElement: JsonElement = gson.toJsonTree(map)
                                    val pojo: WMSExceptionMessage = gson.fromJson(jsonElement, WMSExceptionMessage::class.java)
                                    activity?.let { it1 -> context?.let { it2 ->
                                        common.showAlertType(pojo, it1,
                                            it2
                                        )
                                    } }
                                }

                            }
                        } else {

                           // val _lINB = response.EntityObject as List<LinkedTreeMap<*, *>>
                            //var dto: InboundDTO? = null

                            //Constants.hideProgressDialog()
                            ProgressDialogUtils.closeProgressDialog()
                            var entityobject = response.EntityObject as List<*>
                            if (entityobject.size>0) {

                                val map = entityobject.first() as Map<String, Any>
                                val jsonElement: JsonElement = gson.toJsonTree(map)
                                val dto: InboundDTO = gson.fromJson(jsonElement, InboundDTO::class.java)

                                if (dto.Result == "Success") {
                                    receivedQty = dto.ReceivedQty
                                    pendingQty = dto.ItemPendingQty

                                    fragmentGoodsInBinding.lblInboundQty.text = "$receivedQty/$pendingQty"
                                    fragmentGoodsInBinding.lblPalletNo.text = dto.TotalPalletNo.toString()
                                    fragmentGoodsInBinding.lblPalletNetWeight.text = dto.weight.toString()
                                    fragmentGoodsInBinding.lblPalletHt.text = dto.volume.toString()

                                    isMcodeScanned = false
                                    with(fragmentGoodsInBinding) {
                                        fragmentGoodsInBinding.etExpDate.text.clear()
                                        fragmentGoodsInBinding.etMfgDate.text.clear()
                                        fragmentGoodsInBinding.etBatch.text.clear()
                                        fragmentGoodsInBinding.etQty.text.clear()
                                        fragmentGoodsInBinding.etProjectRef?.text?.clear()
                                        fragmentGoodsInBinding.etSerial.text.clear()
                                        fragmentGoodsInBinding.etKitId?.text?.clear()
                                        fragmentGoodsInBinding.etMrp?.text?.clear()
                                        fragmentGoodsInBinding.lblScannedSku.text = ""
                                        fragmentGoodsInBinding.etQty.isEnabled = false
                                        fragmentGoodsInBinding.etQty.clearFocus()
                                    }

                                    Materialcode = ""
                                    //fragmentGoodsInBinding.lblHu.text = ""
                                    huSize = ""
                                    huNo = ""

                                    fragmentGoodsInBinding.cvScanSKU.setCardBackgroundColor(resources.getColor(R.color.skuColor))
                                    fragmentGoodsInBinding.ivScanSKU.setImageResource(R.drawable.qr3)

                                    if (receivedQty == pendingQty) {
                                        // if inbound completes for the single line item
                                        fragmentGoodsInBinding.cvScanSKU.setCardBackgroundColor(resources.getColor(R.color.white))
                                        fragmentGoodsInBinding.ivScanSKU.setImageResource(R.drawable.check)
                                        common.showUserDefinedAlertType(
                                            getString(R.string.EMC_0075),
                                            requireActivity(),
                                            requireContext(),
                                            "Success"
                                        )
                                    } else {
                                        SoundUtils.alertSuccess(requireActivity(), requireContext())
                                        return@Observer
                                    }
                                } else {
                                    isMcodeScanned = false
                                    fragmentGoodsInBinding.cvScanSKU.setCardBackgroundColor(resources.getColor(R.color.white))
                                    fragmentGoodsInBinding.ivScanSKU.setImageResource(R.drawable.invalid_cross)
                                    common.showUserDefinedAlertType(
                                        dto.Result,
                                        requireActivity(),
                                        requireContext(),
                                        "Error"
                                    )
                                }
                            }

                            //Constants.hideProgressDialog()
                            ProgressDialogUtils.closeProgressDialog()

                            /*for (item in _lINB) {
                                dto = InboundDTO(item.entries.toString())

                            }*/
                        }
                    }catch (ex : Exception){
                        Constants.showAlertDialog(requireActivity(), resources.getString(R.string.EMC_0173))
                    }
                }else{
                    //Constants.hideProgressDialog()
                    ProgressDialogUtils.closeProgressDialog()
                    Constants.showAlertDialog(requireActivity(), resources.getString(R.string.EMC_0173))
                }
            }
        })

    }
    private fun getSLocs(){

        //Constants.showCustomProgressDialog(requireActivity())
        ProgressDialogUtils.showProgressDialog(resources.getString(R.string.please_wait))

        var wmsCoreMessageRequest: WMSCoreMessageRequest = WMSCoreMessageRequest()

        wmsCoreMessageRequest= context?.let { common.SetAuthentication(EndpointConstants.Inbound, it) }!!

        val inboundDTO = InboundDTO()
        with(inboundDTO){
            UserId = userID
            AccountID = accountId
            MaterialMasterID = 0
            SupplierId = 0

        }
        with(wmsCoreMessageRequest){
            EntityObject = inboundDTO
        }

        scanViewModel.getStorageLocations(wmsCoreMessageRequest)
    }

    private fun attachGetSLocObserver(){

        scanViewModel.storageList.observe(viewLifecycleOwner, Observer {
            it.apply {

                if (it.isNotEmpty()){
                    try {
                        //Constants.hideProgressDialog()
                        ProgressDialogUtils.closeProgressDialog()
                        response = scanViewModel.parseJsonToMyModel(this)

                        if (response.Type!!.equals("Exception")) {

                            // you have handle
                            //Constants.hideProgressDialog()
                            ProgressDialogUtils.closeProgressDialog()

                            var entityObject = response.EntityObject as List<*>

                            //inboundDTO = response.EntityObject as InboundDTO

                            for (entity in entityObject) {
                                if (entity is Map<*, *>) {
                                    val map = entity as Map<*, *>
                                    val jsonElement: JsonElement = gson.toJsonTree(map)
                                    val pojo: WMSExceptionMessage = gson.fromJson(jsonElement, WMSExceptionMessage::class.java)
                                    activity?.let { it1 -> context?.let { it2 ->
                                        common.showAlertType(pojo, it1,
                                            it2
                                        )
                                    } }
                                }

                            }
                        } else {

                            //Constants.hideProgressDialog()
                            ProgressDialogUtils.closeProgressDialog()

                            var entityObject = response.EntityObject as List<*>
                            val lstDTO = mutableListOf<InboundDTO>()
                            val lstSLocNames = mutableListOf<String>()

                            //inboundDTO = response.EntityObject as InboundDTO

                            for (entity in entityObject) {
                                if (entity is Map<*, *>) {
                                    val map = entity as Map<*, *>
                                    val jsonElement: JsonElement = gson.toJsonTree(map)
                                    val pojo: InboundDTO = gson.fromJson(jsonElement, InboundDTO::class.java)
                                    lstDTO.add(pojo)
                                }

                            }

                            for(inbound in lstDTO){
                                lstSLocNames.add(inbound.StorageLocation!!)
                            }


                            val arrayAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                lstSLocNames as List<Any?>
                            )


                            fragmentGoodsInBinding.spinnerSelectSloc.setAdapter(arrayAdapter)

                            /*val _lstSLoc = response.EntityObject as List<LinkedTreeMap<*, *>>
                            val lstDto = mutableListOf<InboundDTO>()
                            val _lstSLocNames = mutableListOf<String>()

                            for (i in _lstSLoc.indices) {
                                val dto = InboundDTO(_lstSLoc[i].entries.toString())
                                lstDto.add(dto)
                            }

                            for (dto in lstDto) {
                                _lstSLocNames.add(dto.StorageLocation!!)
                            }

                            val arrayAdapterSLoc = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, _lstSLocNames)
                            fragmentGoodsInBinding.spinnerSelectSloc.adapter = arrayAdapterSLoc*/

                            //Selection need to do.....
                            /*val getPostion = lstSLocNames.indexOf("OK")
                            val compareValue = lstSLocNames.getOrNull(getPostion)

                            compareValue?.let {
                                val spinnerPosition = arrayAdapter.getPosition(getPostion)
                                fragmentGoodsInBinding.spinnerSelectSloc.setSelection(spinnerPosition)
                            }
*/
                        }
                    }catch (ex : Exception){
                        Constants.showAlertDialog(requireActivity(), resources.getString(R.string.EMC_0173))
                    }
                }else{
                    //Constants.hideProgressDialog()
                    ProgressDialogUtils.closeProgressDialog()
                    Constants.showAlertDialog(requireActivity(), resources.getString(R.string.EMC_0173))
                }
            }
        })

    }

    fun showDialogToScanSerialNumber(qrFormatString: String?) {
        DialogUtils.showConfirmAlertDialog(
            activity, "Confirm", getString(R.string.EMC_0172)
        ) { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    isMcodeScanned = true
                    common.setIsPopupActive(false)
                }

                DialogInterface.BUTTON_NEGATIVE -> {
                    validateMaterial(qrFormatString!!)
                    common.setIsPopupActive(false)
                }
            }
        }
    }

    private fun clearFields() {
        fragmentGoodsInBinding.cvScanDock.setCardBackgroundColor(resources.getColor(R.color.locationColor))
        fragmentGoodsInBinding.ivScanDock.setImageResource(R.drawable.qr3)

        fragmentGoodsInBinding.cvScanPallet.setCardBackgroundColor(resources.getColor(R.color.pallet_color))
        fragmentGoodsInBinding.ivScanPallet.setImageResource(R.drawable.qr3)

        fragmentGoodsInBinding.cvScanSKU.setCardBackgroundColor(resources.getColor(R.color.skuColor))
        fragmentGoodsInBinding.ivScanSKU.setImageResource(R.drawable.qr3)

        isMcodeScanned = false
        isSerialNUM = false

        with(fragmentGoodsInBinding) {
            fragmentGoodsInBinding.etPallet.setText("")
            fragmentGoodsInBinding.etExpDate.setText("")
            fragmentGoodsInBinding.etMfgDate.setText("")
            fragmentGoodsInBinding.etBatch.setText("")
            fragmentGoodsInBinding.etQty.setText("")
            fragmentGoodsInBinding.etProjectRef.setText("")
            fragmentGoodsInBinding.etSerial.setText("")
            fragmentGoodsInBinding.lblScannedSku.setText("")
            fragmentGoodsInBinding.etKitId.setText("")
            fragmentGoodsInBinding.etMrp.setText("")
            fragmentGoodsInBinding.lblHu.setText("")
            fragmentGoodsInBinding.etSKUDesc.setText("")
            fragmentGoodsInBinding.cbDescripency.isChecked = false

            fragmentGoodsInBinding.etQty.isEnabled = false
        }

        isDockScanned = false

        fragmentGoodsInBinding.etDock.setText("")

        isDockScanned = false
        isContanierScanned = false
        getSLocs()
        attachGetSLocObserver()

        /*
        binding.btnReceive.isEnabled = false
        binding.btnReceive.setTextColor(getColor(R.color.black))
        binding.btnReceive.setBackgroundResource(R.drawable.button_hide)
        */

        firstImageString = ""
        fragmentGoodsInBinding.ivCaptureImage.setImageResource(R.drawable.ic_baseline_camera_alt_24)
    }

    override fun onResume() {
        super.onResume()

        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            getString(R.string.title_activity_goodsIn)
    }

    override fun onClick(v: View?) {

        when(v!!.id){

            R.id.btnReceive -> {
                if (!fragmentGoodsInBinding.etDock.text.toString().isEmpty() && fragmentGoodsInBinding.etDock.text.toString() != "") {
                    if (!fragmentGoodsInBinding.etPallet.text.toString().isEmpty() && fragmentGoodsInBinding.etPallet.text.toString() != "") {
                        if (!fragmentGoodsInBinding.lblScannedSku.text.toString().isEmpty() && Materialcode!!.isNotEmpty()) {
                            if (userRoll!!.contains("3")) {
                                fragmentGoodsInBinding.cvScanSKU.setCardBackgroundColor(resources.getColor(R.color.gold))
                                fragmentGoodsInBinding.ivScanSKU.setImageResource(R.drawable.qr3)
                                validateRSNAndReceive()
                            } else {
                                if (receivedQty!!.split(".")[0].toInt() < pendingQty!!.split(".")[0].toInt()) {
                                    fragmentGoodsInBinding.cvScanSKU.setCardBackgroundColor(resources.getColor(R.color.gold))
                                    fragmentGoodsInBinding.ivScanSKU.setImageResource(R.drawable.qr3)
                                    validateRSNAndReceive()
                                } else {
                                    common.showUserDefinedAlertType(
                                        getString(R.string.EMC_0075),
                                        requireActivity(),
                                        requireContext(),
                                        "Warning"
                                    )
                                    //return@setOnClickListener
                                }
                            }
                        } else {
                            common.showUserDefinedAlertType(
                                getString(R.string.EMC_0028),
                                requireActivity(),
                                requireContext(),
                                "Warning"
                            )
                            //return@setOnClickListener
                        }
                    } else {
                        common.showUserDefinedAlertType(
                            getString(R.string.EMC_0093),
                            requireActivity(),
                            requireContext(),
                            "Warning"
                        )
                        //return@setOnClickListener
                    }
                } else {
                    common.showUserDefinedAlertType(
                        getString(R.string.EMC_0092),
                        requireActivity(),
                        requireContext(),
                        "Warning"
                    )
                    //return@setOnClickListener
                }
            }

            R.id.iv_capture_image -> {
                val camera_intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                // Start the activity with camera_intent, and request pic id
                startActivityForResult(camera_intent, first_pic_id)
            }

            R.id.btnClear -> {
                clearFields()
            }

        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == first_pic_id && resultCode == Activity.RESULT_OK) {
            data?.extras?.get("data")?.let {
                val firstPhoto = it as Bitmap
                fragmentGoodsInBinding.ivCaptureImage.setImageBitmap(firstPhoto)
                val baos1 = ByteArrayOutputStream()
                firstPhoto.compress(Bitmap.CompressFormat.JPEG, 100, baos1)
                val imageBytes1 = baos1.toByteArray()
                firstImageString = Base64.encodeToString(imageBytes1, Base64.DEFAULT)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val rtIntentDisable = Intent("sw.reader.decode.require").apply {
            putExtra("Enable", false)
        }
        activity?.sendBroadcast(rtIntentDisable)
        activity?.unregisterReceiver(myDataReceiver)
    }
}