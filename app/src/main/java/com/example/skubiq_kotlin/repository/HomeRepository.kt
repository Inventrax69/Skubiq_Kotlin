package com.bluboy.android.data.repository


import com.bluboy.android.data.models.CMSPagePRQ
import com.bluboy.android.data.models.CMSPageRS
import com.bluboy.android.presentation.utility.AppConstant
import com.bluboy.android.presentation.utility.PrefKeys
import com.example.skubiq_kotlin.BaseRepository
import com.example.skubiq_kotlin.BuildConfig
import com.example.skubiq_kotlin.Either
import com.example.skubiq_kotlin.contract.HomeRepo
import com.example.skubiq_kotlin.domain.exceptions.MyException
import com.example.skubiq_kotlin.domain.network.HomeApiService

class HomeRepository constructor(
    private val homeApiService: HomeApiService
//    private val appDao: AppDao
) : HomeRepo, BaseRepository() {
    override suspend fun getCMSPageData(cmsPagePRQ: CMSPagePRQ): Either<MyException, CMSPageRS> {
        TODO("Not yet implemented")
    }


}