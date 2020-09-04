package com.accenture.lidlpoc.domain.model

data class Coupon(
    var id: Int,
    var couponTitle: String,
    var couponRemainingText: String,
    var couponImage: String,
    var isActive: Boolean,
    var disabled: Boolean
)