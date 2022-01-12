package com.matheusvictor.habiticateammanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.matheusvictor.habiticateammanager.service.listener.APIListener
import com.matheusvictor.habiticateammanager.service.listener.ValidationListener
import com.matheusvictor.habiticateammanager.service.model.LoginDataModel
import com.matheusvictor.habiticateammanager.service.repository.remote.RetrofitClient
import com.matheusvictor.habiticateammanager.service.repository.UserRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val mUserRepository = UserRepository(application)

    //Login using API
    private val mLogin = MutableLiveData<ValidationListener>()
    val login: LiveData<ValidationListener> = mLogin

    fun doLogin(username: String, password: String) {

        mUserRepository.login(username, password, object : APIListener<LoginDataModel> {

            override fun onSuccess(result: LoginDataModel, statusCode: Int) {

                RetrofitClient.addHeaders(result.data.username, result.data.apiToken)
                mLogin.value = ValidationListener()

            }

            override fun onFailure(errorMessage: String) {
                mLogin.value = ValidationListener(errorMessage)
            }

        })
    }
}