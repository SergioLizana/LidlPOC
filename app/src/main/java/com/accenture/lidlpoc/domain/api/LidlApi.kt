package com.accenture.lidlpoc.domain.api

import com.accenture.lidlpoc.domain.model.Coupon
import com.accenture.lidlpoc.domain.model.CouponDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LidlApi {
    @GET("getCoupons")
    suspend fun getCoupons(): Response<List<Coupon>>

    @GET("activateCoupon")
    suspend fun activateCoupon(@Query("couponId") couponId: Int): Response<Boolean>

    @GET("cancelCoupon")
    suspend fun cancelCoupon(@Query("couponId") couponId: Int): Response<Boolean>

    @GET("couponDetail")
    suspend fun getCouponDetail(@Query("couponId") couponId: Int): Response<CouponDetail>
}