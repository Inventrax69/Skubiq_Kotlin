package com.example.skubiq_kotlin.utils

import android.app.Activity
import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import com.example.skubiq_kotlin.R

object SoundUtils {

    fun alertConfirm(activity: Activity, context: Context) {
        try {
            val uri = "android.resource://" + activity.packageName + "/" + R.raw.success
            //Strign uri = "http://bla-bla-bla.com/bla-bla.wav"
            val notification = Uri.parse(uri)
            val r = RingtoneManager.getRingtone(context.applicationContext, notification)
            r.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun alertError(activity: Activity, context: Context) {
        try {
            val uri = "android.resource://" + activity.packageName + "/" + R.raw.error
            //Strign uri = "http://bla-bla-bla.com/bla-bla.wav"
            val notification = Uri.parse(uri)
            val r = RingtoneManager.getRingtone(context.applicationContext, notification)
            r.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun alertSuccess(activity: Activity, context: Context) {
        try {
            val uri = "android.resource://" + activity.packageName + "/" + R.raw.success
            //Strign uri = "http://bla-bla-bla.com/bla-bla.wav"
            val notification = Uri.parse(uri)
            val r = RingtoneManager.getRingtone(context.applicationContext, notification)
            r.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun alertCriticalError(activity: Activity, context: Context) {
        try {
            val uri = "android.resource://" + activity.packageName + "/" + R.raw.critical
            //Strign uri = "http://bla-bla-bla.com/bla-bla.wav"
            val notification = Uri.parse(uri)
            val r = RingtoneManager.getRingtone(context.applicationContext, notification)
            r.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun alertWarning(activity: Activity, context: Context) {
        try {
            val uri = "android.resource://" + activity.packageName + "/" + R.raw.warning
            //Strign uri = "http://bla-bla-bla.com/bla-bla.wav"
            val notification = Uri.parse(uri)
            val r = RingtoneManager.getRingtone(context.applicationContext, notification)
            r.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}