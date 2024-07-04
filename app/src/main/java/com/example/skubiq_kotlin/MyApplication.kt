package com.example.skubiq_kotlin

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import com.example.skubiq_kotlin.di.networkModule
import com.example.skubiq_kotlin.di.repositoryModule
import com.example.skubiq_kotlin.di.viewModelModule
import com.pixplicity.easyprefs.library.Prefs
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

open class MyApplication : Application() {
    companion object {
        var context: Context? = null
//        var errorMessage = ""
//        var errorCode = 0
    }

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        context = this
        startKoin{
            androidContext(this@MyApplication)
            modules(arrayListOf(
            networkModule,
            repositoryModule,
            viewModelModule
//                roomModule
        ))}

       /* val contextModule = module {

            single<Context> { androidContext() }
        }*/


        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()

    }
}