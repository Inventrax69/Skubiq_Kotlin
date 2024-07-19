package com.example.skubiq_kotlin.di

import com.example.skubiq_kotlin.LoginSignupViewModel
import com.example.skubiq_kotlin.viewmodels.HouseKeepingViewModel
import com.example.skubiq_kotlin.viewmodels.InboundViewModel
import com.example.skubiq_kotlin.viewmodels.ScanViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created Koin module for ViewModel class
 */
val viewModelModule = module {
   /* viewModel { SplashViewModel(get()) }
    viewModel { HomeViewModel(get()) }*/
    viewModel { LoginSignupViewModel(get()) }
    viewModel { InboundViewModel(get()) }
    viewModel { ScanViewModel(get()) }
    viewModel { HouseKeepingViewModel(get()) }
}