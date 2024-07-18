package com.example.skubiq_kotlin.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.skubiq_kotlin.R
import com.example.skubiq_kotlin.constants.EndpointConstants
import com.example.skubiq_kotlin.databinding.FragmentUnloadingBinding
import com.example.skubiq_kotlin.models.EntryDTO
import com.example.skubiq_kotlin.models.HousekeepingDTO
import com.example.skubiq_kotlin.models.InboundDTO
import com.example.skubiq_kotlin.models.WMSCoreMessage
import com.example.skubiq_kotlin.models.WMSCoreMessageRequest
import com.example.skubiq_kotlin.models.WMSExceptionMessage
import com.example.skubiq_kotlin.utility.Common
import com.example.skubiq_kotlin.utility.SharedPreferencesUtil
import com.example.skubiq_kotlin.utils.Constants
import com.example.skubiq_kotlin.utils.NetworkUtils
import com.example.skubiq_kotlin.view.activities.MainActivity
import com.example.skubiq_kotlin.viewmodels.InboundViewModel
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.inventrax.skubiq.util.ProgressDialogUtils
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.jvm.internal.Intrinsics.Kotlin

class UnloadingFragment : Fragment() {

    private lateinit var binding: FragmentUnloadingBinding
    private lateinit var common : Common
    private  var  sharedPreferencesUtil: SharedPreferencesUtil? = null
    private var userID : String? = null
   // private var scanType : String? = null
    private var accountId : String? = null
    private var whId : String = ""
    private var userName : String? = null
    private var selectedWH : String? = ""


    private var response = WMSCoreMessage();
    private var gson: Gson = Gson()
    private var housekeepingList = mutableListOf<HousekeepingDTO>()

    private var warehouseList = mutableListOf<String>()
    private var storeRefLst = mutableListOf<String>()
    private lateinit var btnGo : Button

    private var Storerefno: String? = null
    var userId: String? = null
    var scanType:kotlin.String? = null
    var inboundId:kotlin.String? = null
    var invoiceQty:kotlin.String? = null
    var receivedQty:kotlin.String? = ""
    var pendingQTY:kotlin.String? = ""
    var warehouseId:kotlin.String? = null
    var tenantId:kotlin.String? = null
    var totalPalletCount:kotlin.Int? = null
    var isCustomLabled:kotlin.String? = null
    var weight:kotlin.Int? = null

    var lstEntry = mutableListOf<List<EntryDTO>>()
    var vehicles: List<String>? = null
    var lstInbound= mutableListOf<InboundDTO>()

    var vehilceNo: String = ""
    var dock:kotlin.String? = ""
    var volume:kotlin.Int? = null
   // private lateinit var inboundDTO: InboundDTO

    private val inboundViewModel : InboundViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUnloadingBinding.inflate(inflater, container, false);
      //  loadFormControllers()

        sharedPreferencesUtil = context?.let { SharedPreferencesUtil(it, "Loginactivity") }
        userID = sharedPreferencesUtil?.getString("RefUserId", "")
        scanType = sharedPreferencesUtil?.getString("scanType", "")
        accountId = sharedPreferencesUtil?.getString("AccountId", "")
        selectedWH = sharedPreferencesUtil?.getString("WarehouseID", "")
        warehouseId = sharedPreferencesUtil?.getString("WarehouseID", "")

        common  = Common()

        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            getString(R.string.title_activity_goodsIn)


        if (NetworkUtils.isInternetAvailable(requireContext())){
            loadInboundDetails()
        }else{
            Constants.showAlertDialog(requireActivity(), resources.getString(R.string.please_enable_internet))
        }

        attachStoreRefObserver()

        btnGo = binding.root.findViewById(R.id.btnGo)

        binding.spinnerSelectStRef.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {

                // getting values as per the selected store ref number
                getInboundId()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        })

        binding.spinnerSelectVehicle.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                vehilceNo = ""

                vehilceNo = binding.spinnerSelectVehicle.getSelectedItem().toString()

                for (entryDTO in lstEntry) {
                    for (k in entryDTO.indices) {
                        if (entryDTO[k].VehicleNumber.equals(vehilceNo)) {
                            dock = entryDTO[k].DockNumber
                        }
                    }
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        })

        btnGo.setOnClickListener {

            val inboundDTO = InboundDTO()
            inboundDTO.StoreRefNo=Storerefno
            inboundDTO.InboundID = inboundId
            inboundDTO.InvoiceQty = invoiceQty
            inboundDTO.ReceivedQty = receivedQty
            inboundDTO.ItemPendingQty = pendingQTY
            inboundDTO.VehicleNo = vehilceNo
            inboundDTO.TenantID = tenantId
            inboundDTO.Dock=dock
            inboundDTO.TotalPalletNo = totalPalletCount
            inboundDTO.weight = weight
            inboundDTO.Dock = dock
            inboundDTO.ReceivedQty = receivedQty
            inboundDTO.InvoiceQty = invoiceQty
            inboundDTO.volume = volume



            //FragmentsUtils.replaceFragmentWithBackStack(requireActivity(),R.id.container,GoodsInFragment())
            if (vehilceNo.isNotEmpty() && Storerefno!!.isNotEmpty()){
                GetInboundDeatils(inboundDTO)
            }else{
                Constants.showAlertDialog(requireActivity(), resources.getString(R.string.err_select_store_ref_no_and_vehicle_no))
            }

        }



        return  binding.root
    }

    fun myScannedData(mainActivity: MainActivity, scannedData: String) {

    }

    private fun loadInboundDetails(){

        //Constants.showCustomProgressDialog(requireContext())
        ProgressDialogUtils.showProgressDialog(resources.getString(R.string.please_wait))

        var wmsCoreMessageRequest: WMSCoreMessageRequest = WMSCoreMessageRequest()

        wmsCoreMessageRequest= context?.let { common.SetAuthentication(EndpointConstants.InboundDTO, it) }!!

        val inboundDTO = InboundDTO()
        with(inboundDTO){
            UserId = userID.toString()
            AccountID = accountId.toString()
            WarehouseID = warehouseId.toString()

        }
        with(wmsCoreMessageRequest){
            EntityObject = inboundDTO
        }

        inboundViewModel.getStoreRefNos(wmsCoreMessageRequest)

    }

    fun attachStoreRefObserver(){

        inboundViewModel.inboundListData.observe(viewLifecycleOwner, Observer {
            it.apply {

                if (it.isNotEmpty()){
                    try {
                        response = inboundViewModel.parseJsonToMyModel(this)

                        if (response.Type!!.equals("Exception")) {
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
                            // you have handle
                            //Constants.hideProgressDialog()
                            ProgressDialogUtils.closeProgressDialog()
                        } else {

                            //Constants.hideProgressDialog()
                            ProgressDialogUtils.closeProgressDialog()

                            var entityObject = response.EntityObject as List<*>
                            lstInbound = mutableListOf<InboundDTO>()
                            storeRefLst = mutableListOf<String>()

                            //inboundDTO = response.EntityObject as InboundDTO

                            for (entity in entityObject) {
                                if (entity is Map<*, *>) {
                                    val map = entity as Map<*, *>
                                    val jsonElement: JsonElement = gson.toJsonTree(map)
                                    val pojo: InboundDTO = gson.fromJson(jsonElement, InboundDTO::class.java)
                                    lstInbound.add(pojo)
                                }

                            }

                            for(inbound in lstInbound){
                                storeRefLst.add(inbound.StoreRefNo!!)
                            }


                            val arrayAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                storeRefLst as List<Any?>
                            )


                            binding.spinnerSelectStRef.setAdapter(arrayAdapter)
                        }
                    }catch (ex : Exception){
                        Constants.hideProgressDialog()
                        Constants.showAlertDialog(requireActivity(), resources.getString(R.string.EMC_0173))
                    }
                }else{
                    Constants.hideProgressDialog()
                    Constants.showAlertDialog(requireActivity(), resources.getString(R.string.EMC_0173))
                }
            }
        })
    }

    private fun setUpSpinnerStoreRefNumber(){

        val spinnerList : ArrayList<String> = arrayListOf()
        spinnerList.add("ASIA24IB00003")
        spinnerList.add("ASIA24IB00004")
        spinnerList.add("ASIA24IB00005")
        spinnerList.add("ASIA24IB00006")
        spinnerList.add("ASIA24IB00007")
        spinnerList.add("ASIA24IB00008")
        spinnerList.add("ASIA24IB00009")
        spinnerList.add("ASIA24IB000010")
        spinnerList.add("ASIA24IB000011")
        spinnerList.add("ASIA24IB000012")
        spinnerList.add("ASIA24IB000013")
        spinnerList.add("ASIA24IB000014")
        spinnerList.add("ASIA24IB000015")

        val arrayAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            spinnerList as List<Any?>
        )
        binding.spinnerSelectStRef.setAdapter(arrayAdapter)
    }

    private fun getInboundId(){

        Storerefno = binding.spinnerSelectStRef.getSelectedItem().toString()
        lstEntry = mutableListOf<List<EntryDTO>>()
        vehicles = mutableListOf<String>()

        for (oInbound in lstInbound!!) {

            if (oInbound.StoreRefNo!!.equals(Storerefno)) {
                inboundId = oInbound.InboundID
                invoiceQty = oInbound.InvoiceQty
                receivedQty = oInbound.ReceivedQty
                warehouseId = oInbound.WarehouseID
                tenantId = oInbound.TenantID
                isCustomLabled = oInbound.IsCustomLabel
                totalPalletCount = oInbound.TotalPalletNo

                oInbound.entry?.let { lstEntry.add(it) }    // to get vehicle numbers and dock values

                for (entryList in lstEntry) {
                    for (entry in entryList) {
                        entry.VehicleNumber?.let {
                            (vehicles as MutableList<String>).add(it)
                        } // list of vehicles assigned to the st. ref no
                    }
                }
            }

                val vehicleAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    vehicles as List<Any?>
                )


                binding.spinnerSelectVehicle.setAdapter(vehicleAdapter)

            }
        }

    private fun GetInboundDeatils(inboundDTO: InboundDTO) {

        //val action = UnloadingFragmentDirections.actionUnloadingFragmentToGoodsInFragment()

        findNavController().navigate(UnloadingFragmentDirections.actionUnloadingFragmentToGoodsInFragment(inboundDTO))

    }

    override fun onResume() {
        super.onResume()

        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            getString(R.string.title_activity_goodsIn)

        attachStoreRefObserver()
    }
}
