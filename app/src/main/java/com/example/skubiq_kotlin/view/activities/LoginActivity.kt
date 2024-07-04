package com.example.skubiq_kotlin.view.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.skubiq_kotlin.LoginSignupViewModel
import com.example.skubiq_kotlin.R
import com.example.skubiq_kotlin.databinding.ActivityLoginBinding
import com.example.skubiq_kotlin.models.*
import com.example.skubiq_kotlin.utility.*
import com.example.skubiq_kotlin.utils.Constants
import com.example.skubiq_kotlin.utils.NetworkUtils
import com.google.gson.Gson
import com.google.gson.JsonElement
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private val loginSignupViewModel: LoginSignupViewModel by viewModel()
    private var response = WMSCoreMessage();
    private val gson: Gson = Gson()
    private var sharedPreferencesUtil: SharedPreferencesUtil? = null
    private val MULTIPLE_PERMISSIONS : Int = 10

    private var permissions = arrayOf<String>(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CALL_PHONE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.INTERNET,
        Manifest.permission.WAKE_LOCK,
        Manifest.permission.VIBRATE,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.CHANGE_NETWORK_STATE
    )

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getSupportActionBar()?.hide()
        getSupportActionBar()?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesUtil = SharedPreferencesUtil(this@LoginActivity, "Loginactivity")

        binding.etPass.setOnTouchListener(OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3


            if (event.rawX >= (binding.etPass.getRight() - binding.etPass.getCompoundDrawables()
                    .get(DRAWABLE_RIGHT).getBounds().width())
            ) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> binding.etPass.setTransformationMethod(
                        HideReturnsTransformationMethod.getInstance()
                    )

                    MotionEvent.ACTION_UP -> binding.etPass.setTransformationMethod(
                        PasswordTransformationMethod.getInstance()
                    )

                }
                return@OnTouchListener true
            }
            false
        })



        loafFoamControllers()
        attachObserver()
    }

    private fun loafFoamControllers(){
        requestforpermissions(permissions)
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.btnLogin -> {

                //startActivity(Intent(this, MainActivity::class.java))
                val username = binding.etUsername.text.toString().trim { it <= ' ' }
                val password = binding.etPass.text.toString().trim { it <= ' ' }

                when{

                    TextUtils.isEmpty(username) -> {
                        /*Toast.makeText(*/
                        /*    this,*/
                        /*    resources.getString(R.string.userHint),*/
                        /*    Toast.LENGTH_SHORT*/
                        /*).show()*/

                        //binding.etUsername.setError(getString(R.string.userHint));
                        Constants.showAlertDialog(this, resources.getString(R.string.userHint))
                    }

                    TextUtils.isEmpty(password) -> {
                        /*Toast.makeText(*/
                        /*    this,*/
                        /*    resources.getString(R.string.passHint),*/
                        /*    Toast.LENGTH_SHORT*/
                        /*).show()*/
                        Constants.showAlertDialog(this, resources.getString(R.string.passHint))
                    }

                    else -> {
                        //Constants.showCustomProgressDialog(this@LoginActivity)

                        if (NetworkUtils.isInternetAvailable(this)){
                            login()
                        }else{
                            Constants.showAlertDialog(this, resources.getString(R.string.please_enable_internet))
                        }

                    }
                }
            }
        }
    }

    fun login(){

        Constants.showCustomProgressDialog(this)

        val wmsCoreMessageRequest = WMSCoreMessageRequest()
        val authToken = AuthToken()
        val loginUserDTO = LoginUserDTO()
        with(authToken){
            AuthKey=getDeviceSerialNumber();
            UserID="1"
            AuthValue=""
            LoginTimeStamp= getTimeStamp()
            AuthToken=""
            RequestNumber=1
            Locale="English"

        }

        with(loginUserDTO){
            ClientMAC=getMacAddress(this@LoginActivity)
            SessionIdentifier=getIPAddress(true)
            MailID=binding.etUsername.text.toString()
           PasswordEncrypted=binding.etPass.text.toString()
        }

        with(wmsCoreMessageRequest){
           AuthToken=authToken
         //  Type= EndpointConstants.LoginUserDTO
           EntityObject = loginUserDTO
        }

        loginSignupViewModel.login(wmsCoreMessageRequest)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun attachObserver() {

        loginSignupViewModel.loginUserRSLiveData.observe(this, Observer {
            it?.apply {

                if (it.isNotEmpty()){

                    try {
                        Constants.hideProgressDialog()
                        response = loginSignupViewModel.parseJsonToMyModel(this)

                        if (response.Type!!.equals("Exception")){
                            Constants.showAlertDialog(this@LoginActivity, resources.getString(R.string.EMC_0001))
                            //Toast.makeText(this@LoginActivity, resources.getString(R.string.tv_exception), Toast.LENGTH_SHORT).show()

                        }else{
                            //Log.i("response", response.Type.toString())

                            try {
                                var entityobject = response.EntityObject as List<*>
                                if (entityobject.size>0) {
                                    val map = entityobject.first() as Map<String, Any>
                                    val jsonElement: JsonElement = gson.toJsonTree(map)
                                    val profileDTO: ProfileDTO = gson.fromJson(jsonElement, ProfileDTO::class.java)

                                    sharedPreferencesUtil?.removeKey("WarehouseID")
                                    sharedPreferencesUtil?.removeKey("Warehouse")

                                    profileDTO.UserID?.let { UserID ->
                                        sharedPreferencesUtil?.saveString("RefUserId", UserID)

                                    }

                                    profileDTO.UserName?.let { UserName ->
                                        sharedPreferencesUtil?.saveString("UserName", UserName)

                                    }

                                    profileDTO.AccountId?.let { AccountId ->

                                        sharedPreferencesUtil?.saveString(
                                            "AccountId",
                                            AccountId.split("[.]")[0]
                                        )

                                    }

                                    profileDTO.TenantID?.let { TenantID ->

                                        sharedPreferencesUtil?.saveInt("TenantID", TenantID)

                                    }

                                    profileDTO.UserRole?.let { UserRole ->

                                        sharedPreferencesUtil?.saveString("UserRole", UserRole)

                                    }
                                    if (profileDTO.TokenData != null) {

                                        sharedPreferencesUtil?.saveString(
                                            "access_token",
                                            profileDTO.TokenData.first().access_token
                                        )


                                        sharedPreferencesUtil?.saveString(
                                            "refresh_token",
                                            profileDTO.TokenData.first().refresh_token
                                        )

                                        var expiresIn = profileDTO.TokenData.first().expires_in
                                        var expires: Int = expiresIn.toInt() - 50
                                        sharedPreferencesUtil?.saveInt("expires_in", expires);
                                    }

                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(intent)

                                    /*    val profileDTO = ProfileDTO.from(map)
                                      print(profileDTO)*/
                                    // print(profileDTO)


                                    /*val profileList = mutableListOf<ProfileDTO>()

                                    for (entity in entityobject) {
                                        if (entity is Map<*, *>) {
                                            val map = entity as Map<String, Any>
                                            val jsonElement: JsonElement = gson.toJsonTree(map)
                                            val pojo: ProfileDTO = gson.fromJson(jsonElement, ProfileDTO::class.java)
                                            profileList.add(pojo)
                                        }
                                    }

                                    for (profile in profileList) {
                                        print(profile)
                                    }*/
                            }


                            }catch (ex : Exception){
                                Constants.showAlertDialog(this@LoginActivity, resources.getString(R.string.EMC_0170))
                            }
                        }
                    }catch (ex : Exception){
                        Constants.hideProgressDialog()
                        Constants.showAlertDialog(this@LoginActivity, resources.getString(R.string.EMC_0170))
                        //Toast.makeText(this@LoginActivity, resources.getString(R.string.EMC_0001), Toast.LENGTH_SHORT).show()
                    }

                }else{
                    Constants.hideProgressDialog()
                    Constants.showAlertDialog(this@LoginActivity, resources.getString(R.string.EMC_0001))
                }

            }

        })

    }

    fun requestforpermissions(permissions: Array<String>) {
        if (checkPermissions()) {
        }
        //  permissions  granted.
    }

    private fun checkPermissions(): Boolean {
        var result: Int
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        for (p in permissions) {
            result = ContextCompat.checkSelfPermission(this@LoginActivity, p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), MULTIPLE_PERMISSIONS)
            return false
        }
        return true
    }

}