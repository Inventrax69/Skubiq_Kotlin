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
import com.example.skubiq_kotlin.databinding.FragmentObdPickingHeaderBinding
import com.example.skubiq_kotlin.databinding.FragmentUnloadingBinding
import com.example.skubiq_kotlin.models.EntryDTO
import com.example.skubiq_kotlin.models.HousekeepingDTO
import com.example.skubiq_kotlin.models.InboundDTO
import com.example.skubiq_kotlin.models.OutboundDTO
import com.example.skubiq_kotlin.models.WMSCoreMessage
import com.example.skubiq_kotlin.models.WMSCoreMessageRequest
import com.example.skubiq_kotlin.models.WMSExceptionMessage
import com.example.skubiq_kotlin.utility.Common
import com.example.skubiq_kotlin.utility.SharedPreferencesUtil
import com.example.skubiq_kotlin.utils.Constants
import com.example.skubiq_kotlin.utils.NetworkUtils
import com.example.skubiq_kotlin.view.activities.MainActivity
import com.example.skubiq_kotlin.viewmodels.InboundViewModel
import com.example.skubiq_kotlin.viewmodels.OutboundViewModel
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.inventrax.skubiq.util.ProgressDialogUtils
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.jvm.internal.Intrinsics.Kotlin

class OBDPickingHeaderFragment : Fragment() {

    private lateinit var binding: FragmentObdPickingHeaderBinding
    private lateinit var common : Common
    private  var  sharedPreferencesUtil: SharedPreferencesUtil? = null
    private var userID : String? = null
   // private var scanType : String? = null
    private var AccountId : String? = null
    private var whId : String = ""
    private var userName : String? = null
    private var selectedWH : String? = ""


    private var response = WMSCoreMessage();
    private var gson: Gson = Gson()
    private var housekeepingList = mutableListOf<HousekeepingDTO>()

    private var warehouseList = mutableListOf<String>()
    private var obdRefLst = mutableListOf<String>()

    private lateinit var btnGo : Button

    private var OBDrefno: String? = null

    var userId: String? = null
    var scanType:kotlin.String? = null
    var outboundId:kotlin.String? = null
    var invoiceQty:kotlin.String? = null
    var receivedQty:kotlin.String? = ""
    var pendingQTY:kotlin.String? = ""
    var warehouseId:kotlin.String? = null
    var tenantId:kotlin.String? = null
    var totalPalletCount:kotlin.Int? = null
    var isCustomLabled:kotlin.String? = null
    var weight:kotlin.Int? = null


    var lstoutbound= mutableListOf<OutboundDTO>()

   // private lateinit var inboundDTO: InboundDTO

    private val outboundViewModel : OutboundViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentObdPickingHeaderBinding.inflate(inflater, container, false);
      //  loadFormControllers()

        sharedPreferencesUtil = context?.let { SharedPreferencesUtil(it, "Loginactivity") }
        userID = sharedPreferencesUtil?.getString("RefUserId", "")
        scanType = sharedPreferencesUtil?.getString("scanType", "")
        AccountId = sharedPreferencesUtil?.getString("AccountId", "")
        selectedWH = sharedPreferencesUtil?.getString("WarehouseID", "")
        warehouseId = sharedPreferencesUtil?.getString("WarehouseID", "")

        common  = Common()

        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            getString(R.string.title_activity_obdpicking)


        if (NetworkUtils.isInternetAvailable(requireContext())){
            getobdRefNos()
        }else{
            Constants.showAlertDialog(requireActivity(), resources.getString(R.string.please_enable_internet))
        }

        attachStoreRefObserver()

        btnGo = binding.root.findViewById(R.id.btnGo)

        binding.spinnerSelectPickList.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {

                // getting values as per the selected store ref number
                getOBDboundId()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        })


        btnGo.setOnClickListener {

            val outboundDTO = OutboundDTO()
            outboundDTO.obdNo=OBDrefno
            outboundDTO.outboundID = outboundId
            outboundDTO.isCustomLabel= isCustomLabled




            //FragmentsUtils.replaceFragmentWithBackStack(requireActivity(),R.id.container,GoodsInFragment())
            if (OBDrefno!!.isNotEmpty()){
                GetOutboundDeatils(outboundDTO)
            }else{
                Constants.showAlertDialog(requireActivity(), resources.getString(R.string.EMC_0035))
            }

        }



        return  binding.root
    }

    fun myScannedData(mainActivity: MainActivity, scannedData: String) {

    }

    private fun getobdRefNos(){

        //Constants.showCustomProgressDialog(requireContext())
        ProgressDialogUtils.showProgressDialog(resources.getString(R.string.please_wait))

        var wmsCoreMessageRequest: WMSCoreMessageRequest = WMSCoreMessageRequest()

        wmsCoreMessageRequest= context?.let { common.SetAuthentication(EndpointConstants.InboundDTO, it) }!!

        val outboundDTO = OutboundDTO()
        with(outboundDTO){
            userId = userID.toString()
            accountID = AccountId.toString()
            wareHouseID = warehouseId.toString()
            isPicking="0"

        }
        with(wmsCoreMessageRequest){
            EntityObject = outboundDTO
        }

        outboundViewModel.getobdRefNos(wmsCoreMessageRequest)

    }

    fun attachStoreRefObserver(){

        outboundViewModel.outboundListData.observe(viewLifecycleOwner, Observer {
            it.apply {

                if (it.isNotEmpty()){
                    try {
                        response = outboundViewModel.parseJsonToMyModel(this)

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
                            lstoutbound = mutableListOf<OutboundDTO>()
                            obdRefLst = mutableListOf<String>()


                            for (entity in entityObject) {
                                if (entity is Map<*, *>) {
                                    val map = entity as Map<*, *>
                                    val jsonElement: JsonElement = gson.toJsonTree(map)
                                    val pojo: OutboundDTO = gson.fromJson(jsonElement, OutboundDTO::class.java)
                                    lstoutbound.add(pojo)
                                }

                            }

                            for(inbound in lstoutbound){
                                obdRefLst.add(inbound.obdNo!!)
                            }


                            val arrayAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                obdRefLst as List<Any?>
                            )
                            binding.spinnerSelectPickList.setAdapter(arrayAdapter)


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



    private fun getOBDboundId() {

        OBDrefno = binding.spinnerSelectPickList.getSelectedItem().toString()



        for (oOutbound in lstoutbound!!) {

            if (oOutbound.obdNo!!.equals(OBDrefno)) {
                outboundId = oOutbound.outboundID
                isCustomLabled=oOutbound.isCustomLabel


            }
        }
    }

    private fun GetOutboundDeatils(outboundDTO: OutboundDTO) {



        findNavController().navigate(OBDPickingHeaderFragmentDirections.actionOBDPickingHeaderFragmentToOBDPickingDetailsFragment(outboundDTO))

    }

    override fun onResume() {
        super.onResume()

        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            getString(R.string.title_activity_obdpicking)

    }
}
