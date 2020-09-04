package com.accenture.lidlpoc.ui.data

data class CouponResponse(
    var id: Int,
    var couponTitle: String,
    var couponRemainingText: String,
    var couponImage: String,
    var isActive: Boolean,
    var disabled: Boolean
)