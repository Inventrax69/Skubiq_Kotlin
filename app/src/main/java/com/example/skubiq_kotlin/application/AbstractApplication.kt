package com.example.skubiq_kotlin.application

import android.content.Context
import androidx.fragment.app.FragmentActivity

object AbstractApplication {

    var CONTEXT: Context? = null
    var fragmentActivity: FragmentActivity? = null

    fun get(): Context? {
        return CONTEXT
    }

}
