package com.example.skubiq_kotlin.utility

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

object FragmentsUtils {

    fun replaceFragmentWithBackStack(
        activity: FragmentActivity,
        fragmentContainer: Int,
        fragment: Fragment?
    ) {
        try {
            val fragmentManager = activity.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragment?.let {
                fragmentTransaction.replace(fragmentContainer, it)
            }
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        } catch (ex: Exception) {
            // Handle exceptions here
        }
    }
}
