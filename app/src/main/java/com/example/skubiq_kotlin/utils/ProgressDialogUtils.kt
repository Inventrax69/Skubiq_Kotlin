package com.inventrax.skubiq.util

import android.app.ProgressDialog
import android.content.Context
import com.example.skubiq_kotlin.R
import com.example.skubiq_kotlin.application.AbstractApplication

open class ProgressDialogUtils {


    constructor(context: Context) {
        progressDialog = ProgressDialog(context).apply {
            progressDialog?.setCancelable(false)
            setMessage(context.getString(R.string.dialog_loading))
        }
    }

    constructor() {
        progressDialog = ProgressDialog(AbstractApplication.get()).apply {
            progressDialog?.setCancelable(false)
            setMessage("Loading...")
        }
    }

    constructor(context: Context, dialogStyle: Int) {
        progressDialog = ProgressDialog(context, dialogStyle).apply {
            progressDialog?.setCancelable(false)
            setMessage(context.getString(R.string.dialog_loading))
        }
    }

    companion object {
        private var progressDialog: ProgressDialog? = null
        private var ProgressActive: Boolean = false

        @JvmStatic
        fun isProgressActive(): Boolean {
            return ProgressActive
        }

        @JvmStatic
        fun setProgressActive(progressActive: Boolean) {
            ProgressActive = progressActive
        }

        @JvmStatic
        fun showProgressDialog() {
            if (progressDialog != null && !progressDialog!!.isShowing) {
                progressDialog!!.show()
            }
        }

        @JvmStatic
        fun showProgressDialog(progressStyle: Int) {
            if (progressDialog != null && !progressDialog!!.isShowing) {
                progressDialog!!.setProgressStyle(progressStyle)
                progressDialog!!.show()
            }
        }

        @JvmStatic
        fun showProgressDialog(progressStyle: Int, message: String) {
            if (progressDialog != null && !progressDialog!!.isShowing) {
                progressDialog!!.setMessage(message)
                progressDialog!!.isIndeterminate = true
                progressDialog!!.setProgressStyle(progressStyle)
                progressDialog!!.show()
            }
        }

        @JvmStatic
       public fun showProgressDialog(message: String) {
            setProgressActive(true)
            if (progressDialog != null && !progressDialog!!.isShowing) {
                progressDialog!!.setMessage(message)
                progressDialog!!.show()
            }
        }

        @JvmStatic
        fun closeProgressDialog() {
            setProgressActive(false)
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
        }

        @JvmStatic
        fun getProgressDialog(context: Context): ProgressDialog {
            if (progressDialog == null) {
                progressDialog = ProgressDialog(context).apply {
                    setMessage(context.getString(R.string.dialog_loading))
                    progressDialog?.setCancelable(false)
                }
            }
            return progressDialog!!
        }
    }
}
