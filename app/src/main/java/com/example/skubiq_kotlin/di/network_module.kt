package com.example.skubiq_kotlin.di
import android.content.Context
import com.bluboy.android.domain.network.LoginSignupApiService
import com.example.skubiq_kotlin.BuildConfig
import com.example.skubiq_kotlin.MyApplication
import com.example.skubiq_kotlin.domain.network.HomeApiService
import com.example.skubiq_kotlin.utility.SharedPreferencesUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit

/**
 * Created Koin module for Network classes
 */


val networkModule = module {

    single { createOkHttpClient(androidContext()) }

    single { createWebService<HomeApiService>(get(), "http://192.168.1.20:914/api/") }

    single { createWebService<LoginSignupApiService>(get(), " http://192.168.1.20:914/api/") }

}

fun createOkHttpClient(context: Context): OkHttpClient {

    val cookieManager = CookieManager()
    cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)

    val httpLoggingInterceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
    }

    val networkInterceptor = Interceptor { chain ->
        // Modify or inspect network requests here
        val request = chain.request().newBuilder()
        val sharedPreferencesUtil = SharedPreferencesUtil(context, "Loginactivity")
        val access_token: String = sharedPreferencesUtil.getString("access_token", "")
        request.addHeader("Authorization", "Bearer $access_token")
        chain.proceed(request.build())
    }


    val client = OkHttpClient.Builder()
        .connectTimeout(120L, TimeUnit.SECONDS)
        .readTimeout(120L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .addNetworkInterceptor(networkInterceptor)
        .cookieJar(MyCookieJar())
        .build()
    return client
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}