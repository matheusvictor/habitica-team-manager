package com.matheusvictor.habiticateammanager.service.repository.remote

import com.matheusvictor.habiticateammanager.service.model.LoginDataModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {

    @POST("user/auth/local/login")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginDataModel>

}