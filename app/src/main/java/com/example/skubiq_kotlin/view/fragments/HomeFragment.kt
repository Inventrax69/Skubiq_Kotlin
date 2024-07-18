package com.example.skubiq_kotlin.view.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.skubiq_kotlin.LoginSignupViewModel
import com.example.skubiq_kotlin.R
import com.example.skubiq_kotlin.constants.EndpointConstants
import com.example.skubiq_kotlin.databinding.FragmentHomeBinding
import com.example.skubiq_kotlin.databinding.WarehouseDropdownListBinding
import com.example.skubiq_kotlin.models.HousekeepingDTO
import com.example.skubiq_kotlin.models.WMSCoreMessage
import com.example.skubiq_kotlin.models.WMSCoreMessageRequest
import com.example.skubiq_kotlin.models.WMSExceptionMessage
import com.example.skubiq_kotlin.utility.Common
import com.example.skubiq_kotlin.utility.SharedPreferencesUtil
import com.google.gson.Gson
import com.google.gson.JsonElement
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var dialogBinding: WarehouseDropdownListBinding
    private lateinit var common : Common
    private  var  sharedPreferencesUtil: SharedPreferencesUtil? = null
    private var userID : String? = null
    private var scanType : String? = null
    private var accountId : String? = null
    private var userName : String? = null
    private var selectedWH : String = ""
    private var whId : String = ""

    private var response = WMSCoreMessage();
    private val gson: Gson = Gson()
    private var housekeepingList = mutableListOf<HousekeepingDTO>()
    private var warehouseList = mutableListOf<String>()

    private val loginSignupViewModel: LoginSignupViewModel by sharedViewModel()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        loadFormControllers()
        return  binding.root
    }


    fun loadFormControllers(){

        sharedPreferencesUtil = activity?.let { SharedPreferencesUtil(it, "Loginactivity") }
        userID = sharedPreferencesUtil?.getString("RefUserId", "")
        scanType = sharedPreferencesUtil?.getString("scanType", "")
        accountId = sharedPreferencesUtil?.getString("AccountId", "")
        userName= sharedPreferencesUtil?.getString("UserName", "")
        common  = Common()
        binding.llReceive.setOnClickListener(this)
        binding.llPutaway.setOnClickListener(this)
        binding.llItemPutaway.setOnClickListener(this)

        binding.llLivestock.setOnClickListener(this)
        binding.llHouseKeeping.setOnClickListener(this)
        binding.llCycleCount.setOnClickListener(this)

        binding.llPicking.setOnClickListener(this)
        binding.llBatchPicking.setOnClickListener(this)
        binding.llSorting.setOnClickListener(this)

        binding.llLoading.setOnClickListener(this)
        binding.LLPodSign.setOnClickListener(this)
        binding.btnwarehouse.setOnClickListener(this)
        binding.lblUsername.text = userName

        binding.btnwarehouse.text = sharedPreferencesUtil?.getString("Warehouse","")
        if (sharedPreferencesUtil?.getString("WarehouseID","")!!.isEmpty()  || sharedPreferencesUtil?.getString("WarehouseID","").equals("")) {
            getWarehouse()

        }

        attachObserver()
    }

    override fun onClick(v: View?) {
        when(v?.id){

           binding.llReceive.id -> activity?.let {
               //FragmentsUtils.replaceFragmentWithBackStack(it,R.id.container,UnloadingFragment())
               //findNavController().navigate(HomeFragmentDirections.actionHomeFragment2ToUnloadingFragment2())
               findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUnloadingFragment())
           }
            binding.btnwarehouse.id -> getWarehouse()
        }

    }

    fun getWarehouse(){
        var wmsCoreMessageRequest: WMSCoreMessageRequest = WMSCoreMessageRequest()

        wmsCoreMessageRequest=
            context?.let { common.SetAuthentication(EndpointConstants.HouseKeepingDTO, it) }!!

        val housekeepingDTO = HousekeepingDTO()
        with(housekeepingDTO){
            userId = userID.toString()
            accountID = accountId.toString()

        }
        with(wmsCoreMessageRequest){
            EntityObject = housekeepingDTO
        }

        loginSignupViewModel.getWarehouse(wmsCoreMessageRequest)
        sharedPreferencesUtil?.removeKey("WarehouseID")
    }

    private fun attachObserver() {

        loginSignupViewModel.wareHouselistData.observe(viewLifecycleOwner, Observer {
            it?.apply {
                response = loginSignupViewModel.parseJsonToMyModel(this)

                if (response.Type!!.equals("Exception")){
// you have handle

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
                }
                else{
                    var entityobject = response.EntityObject as List<*>
                    housekeepingList = mutableListOf<HousekeepingDTO>()
                    warehouseList = mutableListOf<String>()
                    for (entity in entityobject) {
                        if (entity is Map<*, *>) {
                            val map = entity as Map<String, Any>
                            val jsonElement: JsonElement = gson.toJsonTree(map)
                            val pojo: HousekeepingDTO =
                                gson.fromJson(jsonElement, HousekeepingDTO::class.java)
                            housekeepingList.add(pojo)
                        }


                    }

                    for(housekeeping in housekeepingList){
                        warehouseList.add(housekeeping.warehouse)
                    }


                    if (sharedPreferencesUtil?.getString("WarehouseID","")!!.isEmpty()  || sharedPreferencesUtil?.getString("WarehouseID","").equals("")) {
                        showWarehouseDialog(warehouseList)

                    }

                }

            }

        })
    }


    private fun showWarehouseDialog(housekeepingList: MutableList<String>) {

        val dialogBinding = WarehouseDropdownListBinding.inflate(LayoutInflater.from(activity))
        val customDialog = AlertDialog.Builder(requireContext())
        val liveStockAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, housekeepingList)

        dialogBinding.spinnerSelectTenant.adapter = liveStockAdapter

        dialogBinding.spinnerSelectTenant.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedWH = housekeepingList[position].toString()
                binding.btnwarehouse.text = selectedWH
                sharedPreferencesUtil?.saveString("Warehouse", selectedWH!!)
                getWarehouseId()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle nothing selected if needed
            }
        })

        customDialog.apply {
            setView(dialogBinding.root)
            setCancelable(false)
            setTitle(resources.getString(R.string.select_wh))
            setNegativeButton(
                resources.getString(R.string.ok),
                DialogInterface.OnClickListener { dialogInterface, i -> customDialog.setCancelable(true) }
            )
            /*val negativeButton = dialogBinding.root.findViewById<TextView>(R.id.negative_button)
            negativeButton.setOnClickListener {
                // Perform negative action
                customDialog.dismiss()
            }*/

        }

        val alertDialog: AlertDialog = customDialog.create()

        alertDialog.show()
        val window = alertDialog.window
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        //val window: Window? = customDialog.getWindow()
        //window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }


    fun getWarehouseId() {
        for (oHouseKeeping in housekeepingList) {
            if (oHouseKeeping.warehouse.equals(selectedWH)) {
                whId = oHouseKeeping.warehouseId // Warehouse Id of selected warehouse
                sharedPreferencesUtil?.saveString("WarehouseID", whId)
            }
        }
    }

    fun scanHome(){
        Toast.makeText(requireContext(), "Home Fragment", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()

        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            getString(R.string.home)
    }

}