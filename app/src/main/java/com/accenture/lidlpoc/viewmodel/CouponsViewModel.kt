package com.accenture.lidlpoc.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.accenture.lidlpoc.domain.api.LidlApi
import com.accenture.lidlpoc.domain.generics.LidlApiResult
import com.accenture.lidlpoc.domain.model.ErrorResponse
import com.accenture.lidlpoc.domain.repository.LidlRepository
import com.accenture.lidlpoc.domain.usecase.ActivateCouponUseCase
import com.accenture.lidlpoc.domain.usecase.CancelCouponUseCase
import com.accenture.lidlpoc.domain.usecase.GetCouponDetailUseCase
import com.accenture.lidlpoc.domain.usecase.GetCouponsUseCase
import com.accenture.lidlpoc.ui.common.LidlViewResult
import com.accenture.lidlpoc.ui.data.CouponDetailResponse
import com.accenture.lidlpoc.ui.data.CouponResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CouponsViewModel : ViewModel() {

    var getCouponsUseCase: GetCouponsUseCase? = null
    var activateCouponsUseCase: ActivateCouponUseCase? = null
    var cancelCouponUseCase: CancelCouponUseCase? = null
    var detailCouponUseCase: GetCouponDetailUseCase? = null

    val couponLiveData: LiveData<LidlViewResult<List<CouponResponse>, ErrorResponse>>
        get() = _getCouponsViewState

    private var _getCouponsViewState =
        MutableLiveData<LidlViewResult<List<CouponResponse>, ErrorResponse>>()

    val couponDetailLiveData: LiveData<LidlViewResult<CouponDetailResponse, ErrorResponse>>
        get() = _getCouponDetailViewState

    private var _getCouponDetailViewState =
        MutableLiveData<LidlViewResult<CouponDetailResponse, ErrorResponse>>()

    fun getCoupons() {
        _getCouponsViewState.value = LidlViewResult.Loading
        viewModelScope.launch {
            getCouponsUseCase?.invoke(null)?.collect { result ->
                when (result) {
                    is LidlApiResult.Error -> {
                        _getCouponsViewState.value = LidlViewResult.Error(result.error)
                    }
                    is LidlApiResult.Success -> {
                        _getCouponsViewState.value = LidlViewResult.Success(result.success?.map {
                            CouponResponse(
                                it.id,
                                it.couponTitle,
                                it.couponRemainingText,
                                it.couponImage,
                                it.isActive,
                                it.disabled
                            )
                        })
                    }
                }

            }
        }
    }

    fun getCouponDetail(couponId: Int) {
        _getCouponDetailViewState.value = LidlViewResult.Loading
        viewModelScope.launch {
            detailCouponUseCase?.invoke(couponId)?.collect { result ->
                when (result) {
                    is LidlApiResult.Error -> {
                        _getCouponDetailViewState.value = LidlViewResult.Error(result.error)
                    }
                    is LidlApiResult.Success -> {
                        _getCouponDetailViewState.value = LidlViewResult.Success(
                            CouponDetailResponse(
                                result.success!!.id,
                                result.success.active,
                                result.success.itemTitle,
                                result.success.itemName,
                                result.success.itemDesc
                            )
                        )
                    }
                }
            }
        }
    }

    fun activateCoupon(couponId: Int) {
        _getCouponsViewState.value = LidlViewResult.Loading
        viewModelScope.launch {
            activateCouponsUseCase?.invoke(couponId)?.collect { result ->
                when (result) {
                    is LidlApiResult.Error -> {
                        Log.d("error", "ERROR")
                    }
                    is LidlApiResult.Success -> {
                        getCoupons()
                    }
                }
            }
        }
    }

    fun cancelCoupon(couponId: Int) {
        _getCouponsViewState.value = LidlViewResult.Loading
        viewModelScope.launch {
            cancelCouponUseCase?.invoke(couponId)?.collect { result ->
                when (result) {
                    is LidlApiResult.Error -> {
                        Log.d("error", "ERROR")
                    }
                    is LidlApiResult.Success -> {
                        getCoupons()
                    }
                }
            }
        }
    }

}
