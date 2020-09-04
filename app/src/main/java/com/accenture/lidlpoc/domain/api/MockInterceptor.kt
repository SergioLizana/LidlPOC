package com.accenture.lidlpoc.domain.api

import com.accenture.lidlpoc.BuildConfig
import com.accenture.lidlpoc.domain.model.DataModel
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody


class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val gson = Gson()
            val uri = chain.request().url.toUri().toString()
            val responseString = when {
                uri.endsWith("getCoupons") -> gson.toJson(DataModel.couponList)
                uri.contains("activateCoupon", true) -> {
                    DataModel.activateCoupon(uri.substring(uri.length - 1).toInt())
                    gson.toJson(true)
                }
                uri.contains("cancelCoupon", true) -> {
                    DataModel.cancelCoupon(uri.substring(uri.length - 1).toInt())
                    gson.toJson(true)
                }
                uri.contains("couponDetail", true) -> {
                    gson.toJson(DataModel.getCouponDetail(uri.substring(uri.length - 1).toInt()))
                }
                else -> ""
            }

            return chain.proceed(chain.request())
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    responseString.toByteArray()
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
                .addHeader("content-type", "application/json")
                .build()
        } else {
            //just to be on safe side.
            throw IllegalAccessError(
                "MockInterceptor is only meant for Testing Purposes and " +
                        "bound to be used only with DEBUG mode"
            )
        }
    }


}