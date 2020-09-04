package com.accenture.lidlpoc.domain.model

data class CouponDetail(
    val id: Int,
    val active: Boolean,
    val itemTitle: String,
    val itemName: String,
    val itemDesc: String
)
