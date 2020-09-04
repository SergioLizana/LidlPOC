package com.accenture.lidlpoc.ui.data

data class CouponDetailResponse(
    val id: Int,
    val active: Boolean,
    val itemTitle: String,
    val itemName: String,
    val itemDesc: String
)