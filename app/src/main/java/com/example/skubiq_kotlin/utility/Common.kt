package com.example.skubiq_kotlin.utility

import android.content.Context
import android.text.format.DateUtils
import com.example.skubiq_kotlin.constants.EndpointConstants
import com.example.skubiq_kotlin.models.AuthToken
import com.example.skubiq_kotlin.models.WMSCoreMessage
import com.example.skubiq_kotlin.models.WMSCoreMessageRequest

class Common {
    lateinit var userId : String
    lateinit var selectedLanguage : String

    fun SetAuthentication(Constant: EndpointConstants?, context: Context): WMSCoreMessageRequest{
        val sharedPreferencesUtil = SharedPreferencesUtil(context, "Loginactivity")
        userId = sharedPreferencesUtil.getString("RefUserId", "")
        selectedLanguage = sharedPreferencesUtil.getString("selectedLang", "")
        val message = WMSCoreMessageRequest()
        val token = AuthToken()
        token.AuthKey=AndroidUtils.getDeviceSerialNumber().toString()
        token.UserID=userId
        token.AuthValue=""
        token.Locale=selectedLanguage
        token.LoginTimeStamp= ""
        token.AuthToken=""
        token.RequestNumber=1
        // message.setType(Constant);
        message.AuthToken=token
        return message
    }

}