package com.example.skubiq_kotlin.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.skubiq_kotlin.R

object Constants {

    var mProgressDialog: Dialog? = null
    private var alertDialog: AlertDialog.Builder? = null
    var POSITIVE_BUTTON_TEXT: String = "OK"

    fun showCustomProgressDialog(context: Context) {
        mProgressDialog = Dialog(context)

        mProgressDialog?.let {
            /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
            it.setContentView(R.layout.dialog_progress_bar)

            //Start the dialog and display it on screen.
            it.show()
        }
    }

    /**
     * This function is used to dismiss the progress dialog if it is visible to user.
     */
    fun hideProgressDialog() {
        mProgressDialog?.let {
            it.dismiss()
        }
    }

    //android.R.id.button1 for positive, android.R.id.button2 for negative, and android.R.id.button3 for neutral
    fun showAlertDialog(activity: Activity?, message: String?) {
        alertDialog = AlertDialog.Builder(
            activity!!
        )
        alertDialog!!.setMessage(message)
        alertDialog!!.setPositiveButton(
            POSITIVE_BUTTON_TEXT,
            null
        )
        alertDialog!!.show()
    }


}