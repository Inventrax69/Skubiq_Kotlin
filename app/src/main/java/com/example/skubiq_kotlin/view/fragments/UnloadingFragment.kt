package com.example.skubiq_kotlin.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.skubiq_kotlin.LoginSignupViewModel
import com.example.skubiq_kotlin.R
import com.example.skubiq_kotlin.constants.EndpointConstants
import com.example.skubiq_kotlin.databinding.FragmentUnloadingBinding
import com.example.skubiq_kotlin.models.HousekeepingDTO
import com.example.skubiq_kotlin.models.WMSCoreMessage
import com.example.skubiq_kotlin.models.WMSCoreMessageRequest
import com.example.skubiq_kotlin.models.entities.EntryDTO
import com.example.skubiq_kotlin.models.entities.InboundDTO
import com.example.skubiq_kotlin.utility.Common
import com.example.skubiq_kotlin.utility.SharedPreferencesUtil
import com.example.skubiq_kotlin.view.activities.MainActivity
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

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
    private var inboundList = mutableListOf<InboundDTO>()
    private var warehouseList = mutableListOf<String>()
    private var storeRefLst = mutableListOf<String>()
    private lateinit var btnGo : Button

    private var Storerefno: String? = null
    var userId: String? = null
    var scanType:kotlin.String? = null
    var inboundId:kotlin.String? = null
    var invoiceQty:kotlin.String? = null
    var receivedQty:kotlin.String? = ""
    var warehouseId:kotlin.String? = null
    var tenantId:kotlin.String? = null
    var totalPalletCount:kotlin.String? = null
    var isCustomLabled:kotlin.String? = null

    var vehicles: List<String>? = null
    var lstEntry: List<List<EntryDTO>>? = null
    var lstInbound: List<InboundDTO>? = null

    var vehilceNo: String = ""
    var dock:kotlin.String? = ""

    private val loginSignupViewModel: LoginSignupViewModel by sharedViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUnloadingBinding.inflate(inflater, container, false);
      //  loadFormControllers()

        sharedPreferencesUtil = context?.let { SharedPreferencesUtil(it, "UnloadingFragment") }
        userID = sharedPreferencesUtil?.getString("RefUserId", "")
        scanType = sharedPreferencesUtil?.getString("scanType", "")
        accountId = sharedPreferencesUtil?.getString("AccountId", "")
        selectedWH = sharedPreferencesUtil?.getString("WarehouseID", "")

        getInboundData()
        attachInboundObserver()


        btnGo = binding.root.findViewById(R.id.btnGo)
        btnGo.setOnClickListener {
            //FragmentsUtils.replaceFragmentWithBackStack(requireActivity(),R.id.container,GoodsInFragment())
            findNavController().
                    navigate(UnloadingFragmentDirections.actionUnloadingFragmentToGoodsInFragment())

        }

        //setUpSpinnerStoreRefNumber()
        setUpSpinnerSelectVehicle()

        return  binding.root
    }

    fun myScannedData(mainActivity: MainActivity, scannedData: String) {

    }

    /*fun loadFormControls() {

        if (NetworkUtils.isInternetAvailable(requireContext())) {
            //binding.tvSelectStRef = rootView.findViewById<View>(R.id.tvSelectStRef) as TextView
            //tvSelectVehcle = rootView.findViewById<View>(R.id.tvSelectVehcle) as TextView

            //spinnerSelectStRef = rootView.findViewById<View>(R.id.spinnerSelectStRef) as SearchableSpinner
            binding.spinnerSelectStRef.setOnItemSelectedListener(object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    Storerefno = binding.spinnerSelectStRef.getSelectedItem().toString()

                    // getting values as per the selected store ref number
                    getInboundId()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            })

            //spinnerSelectVehicle = rootView.findViewById<View>(R.id.spinnerSelectVehicle) as SearchableSpinner

            binding.spinnerSelectVehicle.setOnItemSelectedListener(object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View,
                    i: Int,
                    l: Long
                ) {
                    vehilceNo = ""

                    vehilceNo = binding.spinnerSelectStRef.getSelectedItem().toString()

                    for (entryDTO in lstEntry!!) {
                        for (k in entryDTO.indices) {
                            if (entryDTO[k].vehicleNumber.equals(vehilceNo)) {
                                dock = entryDTO[k].dockNumber
                            }
                        }
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {
                }
            })

            //btnGo = rootView.findViewById<View>(R.id.btnGo)
            //binding.btnGo.setOnClickListener(this@MainActivity)

            gson = GsonBuilder().create()
            core = WMSCoreMessage()
            sloc = java.util.ArrayList<List<StorageLocationDTO>>()
            lstStorageloc = java.util.ArrayList<String>()
            entryDTOList = java.util.ArrayList<EntryDTO>()

            val sp = activity!!.getSharedPreferences("LoginActivity", Context.MODE_PRIVATE)
            userId = sp.getString("RefUserId", "")
            scanType = sp.getString("scanType", "")
            accountId = sp.getString("AccountId", "")
            selectedWH = sp.getString("WarehouseID", "")

            common = Common()
            exceptionLoggerUtils = ExceptionLoggerUtils()
            errorMessages = ErrorMessages()
            lstInbound = java.util.ArrayList()

            ProgressDialogUtils.closeProgressDialog()
            common.setIsPopupActive(false)

            LoadInbounddetails()
        } else {
            DialogUtils.showAlertDialog(activity, activity!!.resources.getString(R.string.EMC_0025))
            // soundUtils.alertSuccess(LoginActivity.this,getBaseContext());  ljihggyg
            return
        }

//        final Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.right_animation);
//        txtSelectStRef.setAnimation(animation);
//        tvSelectVehcle.setAnimation(animation);
    }*/

    fun getInboundData(){

        var wmsCoreMessageRequest: WMSCoreMessageRequest = WMSCoreMessageRequest()

        wmsCoreMessageRequest= context?.let { common.SetAuthentication(EndpointConstants.InboundDTO, it) }!!

        val inboundDTO = com.example.skubiq_kotlin.models.entities.InboundDTO()
        with(inboundDTO){
            userID = userID.toString()
            accountId = accountId.toString()

        }
        with(wmsCoreMessageRequest){
            EntityObject = inboundDTO
        }

        loginSignupViewModel.getInboundAPI(wmsCoreMessageRequest)
    }

    fun attachInboundObserver(){
        loginSignupViewModel.inboundListData.observe(viewLifecycleOwner, Observer {
            it.apply {

                response = loginSignupViewModel.parseJsonToMyModel(this)

                if (response.Type!!.equals("Exception")) {
// you have handle
                } else {
                    var _lstUnloading: List<LinkedTreeMap<*, *>> = java.util.ArrayList()
                   _lstUnloading = response.EntityObject as List<LinkedTreeMap<*, *>>

                    val lstDto: MutableList<InboundDTO> = java.util.ArrayList()
                    val _lstINBNames: MutableList<String?> = java.util.ArrayList()
                    val _lstInboundId: List<String> = java.util.ArrayList()

                    for (i in _lstUnloading.indices) {
                        val dto = InboundDTO(_lstUnloading[i].entries.toString())
                        lstDto.add(dto)
                        //lstInbound.add(dto)
                    }

                    for (i in lstDto.indices) {
                        _lstINBNames.add(lstDto[i].storeRefNo)
                    }

                   /* var entityobject = response.EntityObject as List<*>
                    inboundList = mutableListOf<InboundDTO>()
                    warehouseList = mutableListOf<String>()
                    for (entity in entityobject) {
                        if (entity is Map<*, *>) {
                            val map = entity as Map<String, Any>
                            val jsonElement: JsonElement = gson.toJsonTree(map)
                            val pojo: InboundDTO =
                                gson.fromJson(jsonElement, InboundDTO::class.java)
                            inboundList.add(pojo)
                        }


                    }

*/
                    val arrayAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
                        requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        _lstINBNames as List<Any?>
                    )
                    binding.spinnerSelectStRef.setAdapter(arrayAdapter)
                    //ProgressDialogUtils.closeProgressDialog()


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

    private fun setUpSpinnerSelectVehicle(){

        val vehicleList : ArrayList<String> = arrayListOf()
        vehicleList.add("ts23444")
        vehicleList.add("ts23444")

        val arrayAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            vehicleList as List<Any?>
        )
        binding.spinnerSelectVehicle.setAdapter(arrayAdapter)


    }


    override fun onResume() {
        super.onResume()

        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            getString(R.string.title_activity_goodsIn)
    }


}