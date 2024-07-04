package com.example.skubiq_kotlin.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.AsyncTask
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import java.lang.reflect.InvocationTargetException

object NetworkUtils {

    var TYPE_WIFI: Int = 1
    var TYPE_MOBILE: Int = 2
    var TYPE_NOT_CONNECTED: Int = 0
    private var connectivityManager: ConnectivityManager? = null
    private var locationManager: LocationManager? = null
    private var  isInternetAvailable = false

    fun getConnectivityStatus(context: Context): Int {
        connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager!!.activeNetworkInfo
        if (null != activeNetwork) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) return TYPE_WIFI

            if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) return TYPE_MOBILE
        }
        return TYPE_NOT_CONNECTED
    }

    fun getConnectivityStatusAsString(context: Context): String? {
        val conn: Int = getConnectivityStatus(context)
        var status: String? = null
        if (conn == TYPE_WIFI) {
            status = "Wifi enabled"
        } else if (conn == TYPE_MOBILE) {
            status = "Mobile data enabled"
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet"
        }
        return status
    }

    fun getConnectivityStatusAsBoolean(context: Context): Boolean {
        val connectionType: Int = getConnectivityStatus(context)
        var status = false
        if (connectionType == TYPE_WIFI) {
            status = true
        } else if (connectionType == TYPE_MOBILE) {
            status = true
        } else if (connectionType == TYPE_NOT_CONNECTED) {
            status = false
        }
        return status
    }


    fun isWiFiEnabled(context: Context): Boolean {
        return getConnectivityStatus(context) == TYPE_WIFI
    }

    fun setForceWiFiEnabled(context: Context, enabled: Boolean) {
        try {
            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            wifiManager.setWifiEnabled(enabled)
        } catch (ex: Exception) {
        }
    }


    fun setWiFiEnabled(activity: Activity, context: Context, enabled: Boolean) {
        try {
            buildAlertMessageNoWiFi(activity, context, enabled)
        } catch (ex: Exception) {
        }
    }

    private fun buildAlertMessageNoWiFi(activity: Activity, context: Context, enabled: Boolean) {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Your Wi-Fi seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                val wifiManager =
                    context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                wifiManager.setWifiEnabled(enabled)
            }
            .setNegativeButton(
                "No"
            ) { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }

    fun isMobileDataEnabled(context: Context): Boolean {
        return getConnectivityStatus(context) == TYPE_MOBILE
    }

    @Throws(
        ClassNotFoundException::class,
        NoSuchFieldException::class,
        IllegalAccessException::class,
        NoSuchMethodException::class,
        InvocationTargetException::class
    )
    fun setMobileDataEnabled(context: Context?, enabled: Boolean) {
        /*final ConnectivityManager conman = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final Class conmanClass = Class.forName(conman.getClass().getName());
        final Field connectivityManagerField = conmanClass.getDeclaredField("mService");
        connectivityManagerField.setAccessible(true);
        final Object connectivityManager = connectivityManagerField.get(conman);
        final Class connectivityManagerClass =  Class.forName(connectivityManager.getClass().getName());
        final Method setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        setMobileDataEnabledMethod.setAccessible(true);
        setMobileDataEnabledMethod.invoke(connectivityManager, enabled);*/

        val dataMtd =
            ConnectivityManager::class.java.getDeclaredMethod(
                "setMobileDataEnabled",
                Boolean::class.javaPrimitiveType
            )
        dataMtd.isAccessible = true
        dataMtd.invoke(connectivityManager, true)
    }


    fun isInternetAvailable(context: Context): Boolean {
        try {
            /*   if(getConnectivityStatusAsBoolean(AbstractApplication.get())){

                       return new InternetAvailabilityCheckTask().execute(ServiceURLConstants.METHOD_CHECK_INTERNET).get();

                   }
                   else
                   {

                       return false;

                   }
       */

            val connectivityManager =
                (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
                .isConnected
        } catch (ex: Exception) {
            ex.printStackTrace()
            return false
        }
    }

    fun openSettingActivity(activity: Activity) {
        activity.startActivity(Intent(Settings.ACTION_SETTINGS))
    }


    fun isGPSEnabled(context: Context): Boolean {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gps_enabled = false
        var network_enabled = false

        try {
            gps_enabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: Exception) {
        }

        try {
            network_enabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (ex: Exception) {
        }

        return gps_enabled
    }


    fun enableGPS(activity: Activity) {
        locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps(activity)
        }
    }

    private fun buildAlertMessageNoGps(activity: Activity) {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton(
                "Yes"
            ) { dialog, id -> activity.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
            .setNegativeButton(
                "No"
            ) { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }

    internal class InternetAvailabilityCheckTask : AsyncTask<String?, Void?, Boolean>() {

        override fun onPostExecute(aBoolean: Boolean) {

            var isInternetAvalaible = false

            isInternetAvalaible = aBoolean
        }

        override fun doInBackground(vararg params: String?): Boolean {
            try {
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

            return false
        }
    }

    fun isConnect(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val netArray = connectivityManager.allNetworks
            var netInfo: NetworkInfo?
            for (net in netArray) {
                netInfo = connectivityManager.getNetworkInfo(net)
                if ((netInfo!!.typeName.equals(
                        "WIFI",
                        ignoreCase = true
                    ) || netInfo.typeName.equals(
                        "MOBILE",
                        ignoreCase = true
                    )) && netInfo.isConnected && netInfo.isAvailable
                ) {
                    //if (netInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    Log.d("Network", "NETWORKNAME: " + netInfo.typeName)
                    return true
                }
            }
        } else {
            if (connectivityManager != null) {
                @Suppress("deprecation") val netInfoArray = connectivityManager.allNetworkInfo
                if (netInfoArray != null) {
                    for (netInfo in netInfoArray) {
                        if ((netInfo.typeName.equals(
                                "WIFI",
                                ignoreCase = true
                            ) || netInfo.typeName.equals(
                                "MOBILE",
                                ignoreCase = true
                            )) && netInfo.isConnected && netInfo.isAvailable
                        ) {
                            //if (netInfo.getState() == NetworkInfo.State.CONNECTED) {
                            Log.d("Network", "NETWORKNAME: " + netInfo.typeName)
                            return true
                        }
                    }
                }
            }
        }
        return false
    }
}