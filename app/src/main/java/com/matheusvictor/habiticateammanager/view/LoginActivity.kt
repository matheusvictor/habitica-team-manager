package com.matheusvictor.habiticateammanager.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.matheusvictor.habiticateammanager.R
import com.matheusvictor.habiticateammanager.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mLoginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        mLoginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        button_login.setOnClickListener {
            if (fieldIsNotEmpty()) handleLogin()
        }

        observe()

    }

    private fun observe() {

        mLoginViewModel.login.observe(this, Observer {
            if (it.validationSuccess()) {
                Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, it.validationFailure(), Toast.LENGTH_SHORT)
                    .show()
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
    
}
