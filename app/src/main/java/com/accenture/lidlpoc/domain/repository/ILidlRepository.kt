package com.accenture.lidlpoc.domain.repository

import com.accenture.lidlpoc.domain.generics.LidlApiResult
import com.accenture.lidlpoc.domain.model.Coupon
import com.accenture.lidlpoc.domain.model.CouponDetail
import com.accenture.lidlpoc.domain.model.ErrorResponse
import kotlinx.coroutines.flow.Flow

interface ILidlRepository {
    suspend fun getCoupons(): Flow<LidlApiResult<List<Coupon>?, ErrorResponse>>
    suspend fun activateCoupon(couponId: Int): Flow<LidlApiResult<Boolean?, ErrorResponse>>
    suspend fun cancelCoupon(couponId: Int): Flow<LidlApiResult<Boolean?, ErrorResponse>>
    suspend fun getCouponDetail(couponId: Int): Flow<LidlApiResult<CouponDetail?, ErrorResponse>>
}