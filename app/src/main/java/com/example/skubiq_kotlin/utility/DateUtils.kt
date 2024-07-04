package com.example.skubiq_kotlin.utility


import android.app.Activity
import android.content.Context
import android.net.wifi.WifiManager
import java.sql.Timestamp
import java.util.*

fun getTimeStamp(): String {
    return Timestamp(Date().time).toString()
}

