package com.example.skubiq_kotlin.di

import com.example.skubiq_kotlin.LoginSignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created Koin module for ViewModel class
 */
val viewModelModule = module {
   /* viewModel { SplashViewModel(get()) }
    viewModel { HomeViewModel(get()) }*/
    viewModel { LoginSignupViewModel(get()) }
}