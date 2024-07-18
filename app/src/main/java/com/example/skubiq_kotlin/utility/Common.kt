package com.example.skubiq_kotlin.utility

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import com.example.skubiq_kotlin.R
import com.example.skubiq_kotlin.constants.EndpointConstants
import com.example.skubiq_kotlin.models.AuthToken
import com.example.skubiq_kotlin.models.WMSCoreMessageRequest
import com.example.skubiq_kotlin.models.WMSExceptionMessage
import com.example.skubiq_kotlin.utils.DialogUtils
import com.example.skubiq_kotlin.utils.SoundUtils

class Common {

    lateinit var userId : String
    lateinit var selectedLanguage : String
    var isPopupActive: Boolean = false
    fun setIsPopupActive(isPopupActive: Boolean) {
        this.isPopupActive = isPopupActive
    }
    fun SetAuthentication(Constant: EndpointConstants?, context: Context): WMSCoreMessageRequest{
        val sharedPreferencesUtil = SharedPreferencesUtil(context, "Loginactivity")
        userId = sharedPreferencesUtil.getString("RefUserId", "")
        selectedLanguage = sharedPreferencesUtil.getString("selectedLang", "")
        val message = WMSCoreMessageRequest()
        val token = AuthToken()
        token.AuthKey="8bc508defc8b6cb4"
        token.UserID=userId
        token.AuthValue=""
        token.Locale="en"
        token.LoginTimeStamp= "2024-07-15 10:49:42.092"
        token.AuthToken=""
        token.RequestNumber=1
        // message.setType(Constant);
        message.AuthToken=token
        return message
    }

    fun showUserDefinedAlertType(
        errorCode: String?,
        activity: Activity,
        context: Context,
        alerttype: String
    ) {
        if (alerttype == "Critical Error") {
            setIsPopupActive(true)
            SoundUtils.alertCriticalError(activity, context)
            DialogUtils.showAlertDialog(activity,
                activity.resources.getString(R.string.EMC_0123),
                errorCode,
                R.drawable.link_break,
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> setIsPopupActive(false)
                    }
                })

            return
        }
        if (alerttype == "Error") {
            setIsPopupActive(true)
            SoundUtils.alertError(activity, context)
            DialogUtils.showAlertDialog(activity,
                activity.resources.getString(R.string.EMC_0124),
                errorCode,
                R.drawable.cross_circle,
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> setIsPopupActive(false)
                    }
                })

            return
        }
        if (alerttype == "Warning") {
            setIsPopupActive(true)
            SoundUtils.alertWarning(activity, context)
            DialogUtils.showAlertDialog(activity,
                activity.resources.getString(R.string.EMC_0125),
                errorCode,
                R.drawable.warning_img,
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> setIsPopupActive(false)
                    }
                })

            return
        }
        if (alerttype == "Success") {
            setIsPopupActive(true)
            SoundUtils.alertSuccess(activity, context)
            DialogUtils.showAlertDialog(activity,
                activity.resources.getString(R.string.EMC_0126),
                errorCode,
                R.drawable.success,
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> setIsPopupActive(false)
                    }
                })
            return
        }
    }





    fun showAlertType(
        wmsExceptionMessage: WMSExceptionMessage,
        activity: Activity,
        context: Context
    ) {
        if (wmsExceptionMessage.ShowAsCriticalError) {
            setIsPopupActive(true)
            SoundUtils.alertCriticalError(activity, context)
            DialogUtils.showAlertDialog(activity,
                activity.resources.getString(R.string.EMC_0123),
                wmsExceptionMessage.WMSMessage,
                R.drawable.link_break,
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> setIsPopupActive(false)
                    }
                })

            return
        }
        if (wmsExceptionMessage.ShowAsError) {
            setIsPopupActive(true)
            SoundUtils.alertError(activity, context)
            DialogUtils.showAlertDialog(activity,
                activity.resources.getString(R.string.EMC_0124),
                wmsExceptionMessage.WMSMessage,
                R.drawable.cross_circle,
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> setIsPopupActive(false)
                    }
                })

            return
        }
        if (wmsExceptionMessage.ShowAsWarning) {
            setIsPopupActive(true)
            SoundUtils.alertWarning(activity, context)
            DialogUtils.showAlertDialog(activity,
                activity.resources.getString(R.string.EMC_0125),
                wmsExceptionMessage.WMSMessage,
                R.drawable.warning_img,
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> setIsPopupActive(false)
                    }
                })

            return
        }
        if (wmsExceptionMessage.ShowAsSuccess) {
            setIsPopupActive(true)
            SoundUtils.alertSuccess(activity, context)
            DialogUtils.showAlertDialog(activity,
                activity.resources.getString(R.string.EMC_0126),
                wmsExceptionMessage.WMSMessage,
                R.drawable.success,
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> setIsPopupActive(false)
                    }
                })
            return
        }
    }
}