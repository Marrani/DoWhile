package com.gabriel.yourfavoritemovies.authentication.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.gabriel.yourfavoritemovies.MovieUtil
import com.google.android.material.snackbar.Snackbar
import com.gabriel.yourfavoritemovies.R
import com.gabriel.yourfavoritemovies.authentication.viewmodel.AuthenticationViewModel
import com.gabriel.yourfavoritemovies.home.view.HomeActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val viewModel: AuthenticationViewModel by lazy {
        ViewModelProvider(this).get(
            AuthenticationViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        bt_login.setOnClickListener {
            val email = etv_email.text.toString()
            val password = etv_password.text.toString()

           when {
               MovieUtil.validateEmailPassword(email, password) -> {
                   viewModel.loginEmailPassword(email, password)
               }
               else -> {
                   Snackbar.make(bt_login, "Login failed", Snackbar.LENGTH_LONG).show()
               }
           }
        }

        tv_login_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        initViewModel()
    }

   private fun initViewModel(){
       viewModel.stateLogin.observe(this, { state ->
           state?.let {
               navigateToHome(it)
           }
       })
   }

    private fun navigateToHome(status: Boolean) {
        when {
            status -> {
                startActivity(Intent(this, HomeActivity::class.java))
            }
        }
    }

    private fun showErrorMessage(message: String) {
        Snackbar.make(bt_login, message, Snackbar.LENGTH_LONG).show()
    }
}