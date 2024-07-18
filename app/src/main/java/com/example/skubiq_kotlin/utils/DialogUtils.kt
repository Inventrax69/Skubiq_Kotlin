package com.example.skubiq_kotlin.utils

import android.app.Activity
import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog

object DialogUtils {

    var ALERT_DIALOG_STYLE: Int = 0
    var CONFIRM_DIALOG_STYLE: Int = 0
    var POSITIVE_BUTTON_TEXT: String = "OK"
    var NEGATIVE_BUTTON_TEXT: String = "Cancel"
    var NEUTRAL_BUTTON_TEXT: String = "Cancel"


    var YES_BUTTON_TEXT: String = "Yes"
    var NO_BUTTON_TEXT: String = "No"
    private var alertDialog: AlertDialog.Builder? = null


    //android.R.id.button1 for positive, android.R.id.button2 for negative, and android.R.id.button3 for neutral
    fun showAlertDialog(activity: Activity?, message: String?) {
        alertDialog = AlertDialog.Builder(activity!!)
        alertDialog!!.setMessage(message)
        alertDialog!!.setPositiveButton(POSITIVE_BUTTON_TEXT, null)
        alertDialog!!.show()
    }

    fun showAlertDialog(activity: Activity?, dialogTitle: String?, message: String?) {
        alertDialog = AlertDialog.Builder(activity!!)
        alertDialog!!.setTitle(dialogTitle)
        alertDialog!!.setMessage(message)
        alertDialog!!.setPositiveButton(POSITIVE_BUTTON_TEXT, null)
        alertDialog!!.show()
    }

    fun showAlertDialog(activity: Activity?, dialogTitle: String?, message: String?, id: Int) {
        alertDialog = AlertDialog.Builder(activity!!)
        alertDialog!!.setTitle(dialogTitle)
        alertDialog!!.setIcon(id)
        alertDialog!!.setMessage(message)
        alertDialog!!.setPositiveButton(POSITIVE_BUTTON_TEXT, null)
        alertDialog!!.show()
    }

    fun showAlertDialog(
        activity: Activity?,
        dialogTitle: String?,
        message: String?,
        onClickListener: DialogInterface.OnClickListener?
    ) {
        alertDialog = AlertDialog.Builder(activity!!)
        alertDialog!!.setCancelable(false)
        alertDialog!!.setTitle(dialogTitle)
        alertDialog!!.setMessage(message)
        alertDialog!!.setPositiveButton(POSITIVE_BUTTON_TEXT, onClickListener)
        alertDialog!!.show()
    }

    fun showAlertDialog(
        activity: Activity?,
        dialogTitle: String?,
        message: String?,
        id: Int,
        onClickListener: DialogInterface.OnClickListener?
    ) {
        alertDialog = AlertDialog.Builder(activity!!)
        alertDialog!!.setCancelable(false)
        alertDialog!!.setTitle(dialogTitle)
        alertDialog!!.setIcon(id)
        alertDialog!!.setMessage(message)
        alertDialog!!.setPositiveButton(POSITIVE_BUTTON_TEXT, onClickListener)
        alertDialog!!.show()
    }

    fun showAlertDialog(
        activity: Activity?,
        dialogTitle: String?,
        message: String?,
        onClickListener: DialogInterface.OnClickListener?,
        id: Int
    ) {
        alertDialog = AlertDialog.Builder(activity!!)
        alertDialog!!.setCancelable(false)
        alertDialog!!.setTitle(dialogTitle)
        alertDialog!!.setMessage(message)
        alertDialog!!.setIcon(id)
        alertDialog!!.setPositiveButton(POSITIVE_BUTTON_TEXT, onClickListener)
        alertDialog!!.show()
    }

    fun showConfirmDialog(
        activity: Activity?,
        dialogTitle: String?,
        message: String?,
        positive: String?,
        negitive: String?,
        onClickListener: DialogInterface.OnClickListener?
    ) {
        alertDialog = AlertDialog.Builder(activity!!)
        alertDialog!!.setCancelable(false)
        alertDialog!!.setTitle(dialogTitle)
        alertDialog!!.setMessage(message)
        alertDialog!!.setPositiveButton(positive, onClickListener)
        alertDialog!!.setNegativeButton(negitive, onClickListener)
        alertDialog!!.show()
    }

    fun showConfirmDialog(
        activity: Activity?,
        dialogTitle: String?,
        message: String?,
        onClickListener: DialogInterface.OnClickListener?
    ) {
        alertDialog = AlertDialog.Builder(activity!!)
        alertDialog!!.setCancelable(false)
        alertDialog!!.setTitle(dialogTitle)
        alertDialog!!.setMessage(message)
        alertDialog!!.setPositiveButton(POSITIVE_BUTTON_TEXT, onClickListener)
        alertDialog!!.setNegativeButton(NEGATIVE_BUTTON_TEXT, onClickListener)
        alertDialog!!.show()
    }


    /*public static AlertDialog.Builder showInputDialog(Activity activity,View view,String dialogTitle ,String message,DialogInterface.OnClickListener onClickListener)
    {
        alertDialog = new AlertDialog.Builder(activity, CONFIRM_DIALOG_STYLE);
        alertDialog.setCancelable(false);
        alertDialog.setTitle(dialogTitle);
        alertDialog.setView(view);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(POSITIVE_BUTTON_TEXT, onClickListener);
        alertDialog.setNegativeButton(NEGATIVE_BUTTON_TEXT, onClickListener);

        return  alertDialog;
    }*/
    fun showInputDialog(
        activity: Activity?,
        view: View?,
        dialogTitle: String?,
        message: String?,
        onClickListener: DialogInterface.OnClickListener?
    ) {
        alertDialog = AlertDialog.Builder(activity!!)
        alertDialog!!.setCancelable(false)
        alertDialog!!.setTitle(dialogTitle)
        alertDialog!!.setView(view)
        alertDialog!!.setMessage(message)
        alertDialog!!.setPositiveButton(POSITIVE_BUTTON_TEXT, onClickListener)
        alertDialog!!.setNegativeButton(NEGATIVE_BUTTON_TEXT, onClickListener)
        alertDialog!!.show()
    }


    /*public static AlertDialog.Builder showCustomDialog(Activity activity,View view,String dialogTitle ,String message,DialogInterface.OnClickListener onClickListener)
    {
        alertDialog = new AlertDialog.Builder(activity, CONFIRM_DIALOG_STYLE);
        alertDialog.setCancelable(false);
        alertDialog.setTitle(dialogTitle);
        alertDialog.setView(view);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(POSITIVE_BUTTON_TEXT, onClickListener);
        alertDialog.setNegativeButton(NEGATIVE_BUTTON_TEXT, onClickListener);

        return  alertDialog;
    }*/
    fun showCustomDialog(activity: Activity?, view: View?) {
        alertDialog = AlertDialog.Builder(activity!!)
        alertDialog!!.setCancelable(false)
        alertDialog!!.setView(view)
        alertDialog!!.show()
    }


    fun showCustomDialog(activity: Activity?, view: View?, dialogTitle: String?) {
        alertDialog = AlertDialog.Builder(activity!!)
        alertDialog!!.setCancelable(false)
        alertDialog!!.setTitle(dialogTitle)
        alertDialog!!.setView(view)
        alertDialog!!.show()
    }

    fun showCustomDialog(activity: Activity?, view: View?, dialogTitle: String?, message: String?) {
        alertDialog = AlertDialog.Builder(activity!!)
        alertDialog!!.setCancelable(false)
        alertDialog!!.setTitle(dialogTitle)
        alertDialog!!.setView(view)
        alertDialog!!.setMessage(message)
        alertDialog!!.show()
    }


    fun showCustomDialog(
        activity: Activity?,
        view: View?,
        onClickListener: DialogInterface.OnClickListener?
    ) {
        alertDialog = AlertDialog.Builder(activity!!)
        alertDialog!!.setCancelable(false)
        alertDialog!!.setView(view)
        alertDialog!!.setPositiveButton(POSITIVE_BUTTON_TEXT, onClickListener)
        alertDialog!!.setNegativeButton(NEGATIVE_BUTTON_TEXT, onClickListener)
        alertDialog!!.show()
    }


    fun showCustomDialog(
        activity: Activity?,
        view: View?,
        dialogTitle: String?,
        onClickListener: DialogInterface.OnClickListener?
    ) {
        alertDialog = AlertDialog.Builder(activity!!)
        alertDialog!!.setCancelable(false)
        alertDialog!!.setTitle(dialogTitle)
        alertDialog!!.setView(view)
        alertDialog!!.setPositiveButton(POSITIVE_BUTTON_TEXT, onClickListener)
        alertDialog!!.setNegativeButton(NEGATIVE_BUTTON_TEXT, onClickListener)
        alertDialog!!.show()
    }

    fun showCustomDialog(
        activity: Activity?,
        view: View?,
        dialogTitle: String?,
        message: String?,
        onClickListener: DialogInterface.OnClickListener?
    ) {
        alertDialog = AlertDialog.Builder(activity!!)
        alertDialog!!.setCancelable(false)
        alertDialog!!.setTitle(dialogTitle)
        alertDialog!!.setView(view)
        alertDialog!!.setMessage(message)
        alertDialog!!.setPositiveButton(POSITIVE_BUTTON_TEXT, onClickListener)
        alertDialog!!.setNegativeButton(NEGATIVE_BUTTON_TEXT, onClickListener)
        alertDialog!!.show()
    }


    /*
    public static AlertDialog.Builder showCustomDialog(Activity activity,int layout,String dialogTitle ,String message,DialogInterface.OnClickListener onClickListener)
    {
        alertDialog = new AlertDialog.Builder(activity, CONFIRM_DIALOG_STYLE);
        alertDialog.setCancelable(false);
        alertDialog.setTitle(dialogTitle);
        alertDialog.setView(layout);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(POSITIVE_BUTTON_TEXT, onClickListener);
        alertDialog.setNegativeButton(NEGATIVE_BUTTON_TEXT, onClickListener);

        return  alertDialog;
    }*/
    fun showCustomDialog(
        activity: Activity?,
        layout: Int,
        dialogTitle: String?,
        message: String?,
        onClickListener: DialogInterface.OnClickListener?
    ) {
        alertDialog = AlertDialog.Builder(activity!!)
        alertDialog!!.setCancelable(false)
        alertDialog!!.setTitle(dialogTitle)
        alertDialog!!.setView(layout)
        alertDialog!!.setMessage(message)
        alertDialog!!.setPositiveButton(POSITIVE_BUTTON_TEXT, onClickListener)
        alertDialog!!.setNegativeButton(NEGATIVE_BUTTON_TEXT, onClickListener)
        alertDialog!!.show()
    }


    fun showSingleChoiceDialog(
        activity: Activity?,
        dialogTitle: String?,
        sourceList: Array<CharSequence?>?,
        onClickListener: DialogInterface.OnClickListener?
    ) {
        alertDialog = AlertDialog.Builder(activity!!)
        alertDialog!!.setCancelable(false)
        alertDialog!!.setTitle(dialogTitle)
        alertDialog!!.setSingleChoiceItems(sourceList, 0, onClickListener)
        // alertDialog.setPositiveButton(POSITIVE_BUTTON_TEXT, onClickListener);
        // alertDialog.setNegativeButton(NEGATIVE_BUTTON_TEXT, onClickListener);
        alertDialog!!.show()
    }

    fun showConfirmAlertDialog(
        activity: Activity?,
        dialogTitle: String?,
        message: String?,
        onClickListener: DialogInterface.OnClickListener?
    ) {
        alertDialog = AlertDialog.Builder(activity!!)
        alertDialog!!.setCancelable(false)
        alertDialog!!.setTitle(dialogTitle)
        alertDialog!!.setMessage(message)
        alertDialog!!.setPositiveButton(YES_BUTTON_TEXT, onClickListener)
        alertDialog!!.setNegativeButton(NO_BUTTON_TEXT, onClickListener)
        alertDialog!!.show()
    }
}