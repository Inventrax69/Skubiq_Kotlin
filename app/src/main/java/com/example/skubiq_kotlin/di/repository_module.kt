package com.example.skubiq_kotlin.di

import com.example.skubiq_kotlin.contract.LoginSignupRepo
import com.example.skubiq_kotlin.repository.LoginSignupApisRepository
import org.koin.dsl.module


/**
 * Created Koin module for Repository class
 */

val repositoryModule = module {
    single<LoginSignupRepo> { LoginSignupApisRepository(get()) }
   // single<HomeRepo> { HomeRepository(get()) }
}