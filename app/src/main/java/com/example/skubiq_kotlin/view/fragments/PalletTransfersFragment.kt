package com.example.skubiq_kotlin.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.skubiq_kotlin.R
import com.example.skubiq_kotlin.constants.EndpointConstants
import com.example.skubiq_kotlin.databinding.FragmentPalletTransfersBinding
import com.example.skubiq_kotlin.models.HousekeepingDTO
import com.example.skubiq_kotlin.models.WMSCoreMessage
import com.example.skubiq_kotlin.models.WMSCoreMessageRequest
import com.example.skubiq_kotlin.models.WMSExceptionMessage
import com.example.skubiq_kotlin.utility.Common
import com.example.skubiq_kotlin.utility.SharedPreferencesUtil
import com.example.skubiq_kotlin.utils.Constants
import com.example.skubiq_kotlin.viewmodels.HouseKeepingViewModel
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.inventrax.skubiq.util.ProgressDialogUtils
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PalletTransfersFragment : Fragment(), View.OnClickListener {

    private lateinit var fragmentPalletTransfersBinding: FragmentPalletTransfersBinding
    private lateinit var common : Common
    private var userID : String? = null
    private var tenantID : String? = ""
    private var accountId : String? = null
    private var warehouseID : String? = ""
    private  var  sharedPreferencesUtil: SharedPreferencesUtil? = null
    private val houseKeepingViewModel : HouseKeepingViewModel by sharedViewModel()
    private var response = WMSCoreMessage();
    private var gson: Gson = Gson()
    var lstDTO= mutableListOf<HousekeepingDTO>()
    var _lstStock: List<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_pallet_transfers, container, false)

        fragmentPalletTransfersBinding = FragmentPalletTransfersBinding.inflate(layoutInflater)
        val view = fragmentPalletTransfersBinding.root

        common = Common()
        sharedPreferencesUtil = context?.let { SharedPreferencesUtil(it, "Loginactivity") }
        userID = sharedPreferencesUtil?.getString("RefUserId", "")
        accountId = sharedPreferencesUtil?.getString("AccountId", "")
        warehouseID = sharedPreferencesUtil?.getString("WarehouseID", "")

        loadFormControls()
        attachGetTenantsObserver()

        fragmentPalletTransfersBinding.btnGo.setOnClickListener(this)

        return view
    }

    private fun loadFormControls(){

        getTenants()
    }

    private fun getTenants(){

        ProgressDialogUtils.showProgressDialog(resources.getString(R.string.please_wait))

        var wmsCoreMessageRequest: WMSCoreMessageRequest = WMSCoreMessageRequest()

        wmsCoreMessageRequest= context?.let { common.SetAuthentication(EndpointConstants.HouseKeepingDTO, it) }!!

        val housekeepingDTO = HousekeepingDTO()
        with(housekeepingDTO){
            userId = userID.toString()
            tenantID = ""
            accountID = accountId.toString()
            warehouseId = warehouseID.toString()

        }
        with(wmsCoreMessageRequest){
            EntityObject = housekeepingDTO
        }

        houseKeepingViewModel.getTenants(wmsCoreMessageRequest)


    }

    fun attachGetTenantsObserver(){

        houseKeepingViewModel.tenantsListData.observe(viewLifecycleOwner, Observer {
            it.apply {

                if (it.isNotEmpty()){
                    try {
                        response = houseKeepingViewModel.parseJsonToMyModel(this)

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
                            lstDTO = mutableListOf<HousekeepingDTO>()
                            _lstStock = mutableListOf<String>()

                            //inboundDTO = response.EntityObject as InboundDTO

                            for (entity in entityObject) {
                                if (entity is Map<*, *>) {
                                    val map = entity as Map<*, *>
                                    val jsonElement: JsonElement = gson.toJsonTree(map)
                                    val pojo: HousekeepingDTO = gson.fromJson(jsonElement, HousekeepingDTO::class.java)
                                    lstDTO.add(pojo)
                                }

                            }

                            for(tenant in lstDTO){
                                (_lstStock as MutableList<String>).add(tenant.tenantName!!)
                            }


                            val arrayAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                _lstStock as List<Any?>
                            )


                            fragmentPalletTransfersBinding.spinnerSelectTenant.setAdapter(arrayAdapter)
                        }
                    }catch (ex : Exception){
                        ProgressDialogUtils.closeProgressDialog()
                        Constants.showAlertDialog(requireActivity(), resources.getString(R.string.EMC_0173))
                    }
                }else{
                    ProgressDialogUtils.closeProgressDialog()
                    Constants.showAlertDialog(requireActivity(), resources.getString(R.string.EMC_0173))
                }
            }
        })
    }

    override fun onClick(v: View) {
        when(v.id){

            R.id.btnGo -> {

                /*if (whId != "" && tenantId != "") {

                    // method to get the storage locations
                } else {
                    common.showUserDefinedAlertType(
                        activity!!.resources.getString(R.string.EMC_0011),
                        activity!!, context!!, "Warning"
                    )
                }*/

                fragmentPalletTransfersBinding.rlSelect.setVisibility(View.GONE)
                fragmentPalletTransfersBinding.rlIPalletTransfer.setVisibility(View.VISIBLE)
            }
        }
    }

}