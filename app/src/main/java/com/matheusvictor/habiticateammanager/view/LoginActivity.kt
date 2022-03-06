package com.matheusvictor.habiticateammanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.matheusvictor.habiticateammanager.R
import com.matheusvictor.habiticateammanager.service.constants.AppConstants
import com.matheusvictor.habiticateammanager.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity() {

    private lateinit var mLoginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        mLoginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        mLoginViewModel.isFingerprintAuthenticationAvailable()

        button_login.setOnClickListener {
            if (fieldIsNotEmpty()) handleLogin()
        }

        observe()
//        verifyLoggedUser()

    }

    private fun observe() {

        mLoginViewModel.login.observe(this, Observer {
            if (it.validationSuccess()) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(applicationContext, it.validationFailure(), Toast.LENGTH_SHORT)
                    .show()
            }
        })

//        mLoginViewModel.loggedUser.observe(this, Observer {
//            if(it){
//                Toast.makeText(applicationContext, "Is logged", Toast.LENGTH_LONG).show()
//            }
//        })

        mLoginViewModel.fingerPrint.observe(this, Observer {
            if (it) {
                showFingerprintAuthentication()
            }
        })

    }

    private fun handleLogin() {

        val username = edit_text_username.text.toString()
        val password = edit_text_password.text.toString()

        mLoginViewModel.doLogin(username, password)

    }

    private fun fieldIsNotEmpty(): Boolean {

        val username = edit_text_username.text.toString()
        val password = edit_text_password.text.toString()

        return if (username.isNotBlank() && password.isNotBlank()) {
            true
        } else {
            if (username.isBlank()) {
                edit_text_username.error = "Username is necessary"
            }
            if (password.isBlank()) {
                edit_text_password.error = "Password is necessary"
            }
            false
        }
    }

    private fun verifyLoggedUser(){
        mLoginViewModel.verifyLoggedUser()
    }

    private fun showFingerprintAuthentication() {

        val executor: Executor = ContextCompat.getMainExecutor(this)

        val biometricPrompt = BiometricPrompt(
            this@LoginActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                }
            })

        val biometricInfo: BiometricPrompt.PromptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Welcome again!")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(biometricInfo)
    }

}
