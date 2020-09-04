package com.accenture.lidlpoc.ui.common

import android.view.View

interface OnClickListListener<T> {
    fun onClickListener(data: T, v: View)
}