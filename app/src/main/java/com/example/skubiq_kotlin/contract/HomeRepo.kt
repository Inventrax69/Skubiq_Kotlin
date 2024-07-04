package com.example.skubiq_kotlin.contract

import com.bluboy.android.data.models.CMSPagePRQ
import com.bluboy.android.data.models.CMSPageRS
import com.example.skubiq_kotlin.Either
import com.example.skubiq_kotlin.domain.exceptions.MyException


interface HomeRepo {
    suspend fun getCMSPageData(cmsPagePRQ: CMSPagePRQ): Either<MyException, CMSPageRS>
}