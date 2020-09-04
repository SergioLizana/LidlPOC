package com.accenture.lidlpoc.ui.common

import com.accenture.lidlpoc.ui.data.CouponResponse

interface OnClickButtonListListener {
    fun onClickButtonListener(active: Boolean, data: CouponResponse)
}