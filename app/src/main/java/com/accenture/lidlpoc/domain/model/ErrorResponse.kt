package com.accenture.lidlpoc.domain.model

data class ErrorResponse(
    val code: Int?,
    val message: String = ""
)