package com.accenture.lidlpoc.domain.repository

import com.accenture.lidlpoc.domain.api.LidlApi
import com.accenture.lidlpoc.domain.generics.LidlApiResult
import com.accenture.lidlpoc.domain.model.Coupon
import com.accenture.lidlpoc.domain.model.CouponDetail
import com.accenture.lidlpoc.domain.model.ErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LidlRepository(private val lidlApi: LidlApi) : ILidlRepository {
    override suspend fun getCoupons(): Flow<LidlApiResult<List<Coupon>?, ErrorResponse>> {
        val response = lidlApi.getCoupons()
        return flow {
            if (response.isSuccessful) {
                this.emit(LidlApiResult.Success(response.body()))
            } else {
                this.emit(LidlApiResult.Error(ErrorResponse(response.code(), response.message())))
            }
        }.flowOn(Dispatchers.Main)
    }

    override suspend fun activateCoupon(couponId: Int): Flow<LidlApiResult<Boolean?, ErrorResponse>> {
        val response = lidlApi.activateCoupon(couponId)
        return flow {
            if (response.isSuccessful) {
                this.emit(LidlApiResult.Success(response.body()))
            } else {
                this.emit(LidlApiResult.Error(ErrorResponse(response.code(), response.message())))
            }
        }.flowOn(Dispatchers.Main)
    }

    override suspend fun cancelCoupon(couponId: Int): Flow<LidlApiResult<Boolean?, ErrorResponse>> {
        val response = lidlApi.cancelCoupon(couponId)
        return flow {
            if (response.isSuccessful) {
                this.emit(LidlApiResult.Success(response.body()))
            } else {
                this.emit(LidlApiResult.Error(ErrorResponse(response.code(), response.message())))
            }
        }.flowOn(Dispatchers.Main)
    }

    override suspend fun getCouponDetail(couponId: Int): Flow<LidlApiResult<CouponDetail?, ErrorResponse>> {
        val response = lidlApi.getCouponDetail(couponId)
        return flow {
            if (response.isSuccessful) {
                this.emit(LidlApiResult.Success(response.body()))
            } else {
                this.emit(LidlApiResult.Error(ErrorResponse(response.code(), response.message())))
            }
        }.flowOn(Dispatchers.Main)
    }
}