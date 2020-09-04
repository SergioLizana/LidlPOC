package com.accenture.lidlpoc.domain.api

import com.accenture.lidlpoc.utils.DOMAIN
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class LidlClient {

    private lateinit var retrofitBuilder: Retrofit.Builder

    constructor() {
        initApiClient(DOMAIN)
    }

    constructor(baseUrl: String) {
        initApiClient(baseUrl)
    }

    /**
     * ApiClient initializer.
     * It initializes a Retrofit.Builder instance that will be used to create each service call.
     * @param baseUrl Backend base URL
     */
    private fun initApiClient(baseUrl: String) {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(MockInterceptor())
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()


        val gBuilder = GsonBuilder()
        val gson = gBuilder.create()

        retrofitBuilder = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))

    }

    /**
     * Set a new backend base URL
     * @param baseUrl
     */
    fun setBaseUrl(baseUrl: String) {
        retrofitBuilder.baseUrl(baseUrl)
    }

    /**
     * @param baseUrl Specific base URL for the service being created
     * @param serviceClass Interface Class which has to be implemented by [this#retrofitBuilder]
     * @return Implemented #servicesClass interface, ready to be called
     */
    fun <T> createService(baseUrl: String, serviceClass: Class<T>): T =
        retrofitBuilder
            .baseUrl(baseUrl)
            .build()
            .create(serviceClass)

    /**
     * @param serviceClass Interface Class which has to be implemented by [this#retrofitBuilder]
     * @return Implemented #servicesClass interface, ready to be called
     */
    fun <T> createService(serviceClass: Class<T>): T =
        retrofitBuilder
            .build()
            .create(serviceClass)

}