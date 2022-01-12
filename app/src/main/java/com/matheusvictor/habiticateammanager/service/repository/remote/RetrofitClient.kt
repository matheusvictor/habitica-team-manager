package com.matheusvictor.habiticateammanager.service.repository.remote

import com.matheusvictor.habiticateammanager.service.constants.AppConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {

    companion object {

        private lateinit var mRetrofit: Retrofit

        private var username: String = ""
        private var apiToken: String = ""
        private const val BASE_URL = AppConstants.API.BASE_URL

        private fun getRetrofitInstance(): Retrofit {

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader(AppConstants.HEADER.USERNAME, username)
                    .addHeader(AppConstants.HEADER.API_TOKEN, apiToken)
                    .build()
                chain.proceed(request)
            }

            if (!Companion::mRetrofit.isInitialized) {
                mRetrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return mRetrofit
        }

        fun addHeaders(username: String, apiToken: String) {
            Companion.username = username
            Companion.apiToken = apiToken
        }

        fun <S> createService(serviceClass: Class<S>): S {
            return getRetrofitInstance().create(serviceClass)
        }

    }

}
