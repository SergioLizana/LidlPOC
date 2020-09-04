package com.accenture.lidlpoc.ui.common

interface GenericBinder<T> {
    fun bind(data: T, listener: OnClickListListener<T>?)
}