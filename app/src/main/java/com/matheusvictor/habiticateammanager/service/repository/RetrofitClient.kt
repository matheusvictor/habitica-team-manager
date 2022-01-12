package com.matheusvictor.habiticateammanager.service.repository

import com.matheusvictor.habiticateammanager.service.constants.AppConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {

        private lateinit var retrofit: Retrofit
        private const val BASE_URL = AppConstants.API.BASE_URL
        private var username: String = ""
        private var apiToken: String = ""

        private fun getRetrofitInstance(): Retrofit {

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor { chain ->
                val request =
                    chain.request()
                        .newBuilder()
                        .addHeader(AppConstants.HEADER.USERNAME, this.username)
                        .addHeader(AppConstants.HEADER.API_TOKEN, this.apiToken)
                        .build()
                chain.proceed(request)
            }

            if (!::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit
        }

        fun addHeaders(username: String, apiToken: String) {
            this.username = username
            this.apiToken = apiToken
        }

        fun <S> createService(serviceClass: Class<S>): S {
            return getRetrofitInstance().create(serviceClass)
        }

    }

}
