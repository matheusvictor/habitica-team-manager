package com.matheusvictor.habiticateammanager.service.repository

import android.content.Context
import com.matheusvictor.habiticateammanager.service.constants.AppConstants
import com.matheusvictor.habiticateammanager.service.listener.APIListener
import com.matheusvictor.habiticateammanager.service.model.LoginDataModel
import com.matheusvictor.habiticateammanager.service.repository.remote.RetrofitClient
import com.matheusvictor.habiticateammanager.service.repository.remote.UserService
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(context: Context) {

    private val mRemoteService = RetrofitClient.createService(UserService::class.java)

    fun login(username: String, password: String, listener: APIListener<LoginDataModel>) {

        val call: Call<LoginDataModel> = mRemoteService.login(username, password)

        call.enqueue(object : Callback<LoginDataModel> {

            override fun onFailure(call: Call<LoginDataModel>, t: Throwable) {
                listener.onFailure(t.message.toString())
            }

            override fun onResponse(call: Call<LoginDataModel>,
                                    response: Response<LoginDataModel>)
            {

                val code = response.code()

                if (code == AppConstants.HTTP.SUCCESS) {
                    response.body()?.let {
                        listener.onSuccess(it, response.code())
                    }
                } else {
                    response.errorBody()?.let {
                        listener.onFailure(JSONObject(it.string()).getString("message"))
                    }
                }
            }

        })

    }

}
