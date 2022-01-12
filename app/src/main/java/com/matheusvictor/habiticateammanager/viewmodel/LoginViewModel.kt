package com.matheusvictor.habiticateammanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.matheusvictor.habiticateammanager.service.constants.AppConstants
import com.matheusvictor.habiticateammanager.service.listener.APIListener
import com.matheusvictor.habiticateammanager.service.listener.ValidationListener
import com.matheusvictor.habiticateammanager.service.model.LoginDataModel
import com.matheusvictor.habiticateammanager.service.repository.remote.RetrofitClient
import com.matheusvictor.habiticateammanager.service.repository.UserRepository
import com.matheusvictor.habiticateammanager.service.repository.local.SecurityPreferences

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val mUserRepository = UserRepository(application)
    private val mSharedPreferences = SecurityPreferences(application)

    //Login using API
    private val mLogin = MutableLiveData<ValidationListener>()
    val login: LiveData<ValidationListener> = mLogin

    private val mLoggedUser = MutableLiveData<Boolean>()
    val loggedUser: LiveData<Boolean> = mLoggedUser

    fun doLogin(username: String, password: String) {

        mUserRepository.login(username, password, object : APIListener<LoginDataModel> {

            override fun onSuccess(result: LoginDataModel, statusCode: Int) {

                with(mSharedPreferences) {
                    store(AppConstants.SHARED.USERNAME, result.data.username)
                    store(AppConstants.SHARED.API_TOKEN, result.data.apiToken)
                }

                RetrofitClient.addHeaders(result.data.username, result.data.apiToken)
                mLogin.value = ValidationListener()

            }

            override fun onFailure(errorMessage: String) {
                mLogin.value = ValidationListener(errorMessage)
            }

        })
    }

    fun verifyLoggedUser() {

        val username: String = mSharedPreferences.get(AppConstants.SHARED.USERNAME)
        val apiToken: String = mSharedPreferences.get(AppConstants.SHARED.API_TOKEN)

        val isLoggedUser: Boolean = (username != "" && apiToken != "")

        mLoggedUser.value = isLoggedUser

    }

}
