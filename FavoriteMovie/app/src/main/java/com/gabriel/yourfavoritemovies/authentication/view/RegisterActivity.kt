package com.gabriel.yourfavoritemovies.authentication.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.gabriel.yourfavoritemovies.MovieUtil.validateNameEmailPassword
import com.google.android.material.snackbar.Snackbar
import com.gabriel.yourfavoritemovies.R
import com.gabriel.yourfavoritemovies.authentication.viewmodel.AuthenticationViewModel
import com.gabriel.yourfavoritemovies.home.view.HomeActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private val viewModel: AuthenticationViewModel by lazy {
        ViewModelProvider(this).get(
            AuthenticationViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_register.setOnClickListener {
            val name = etv_email_register.text.toString()
            val email = etv_email_register.text.toString()
            val password = etv_password_register.text.toString()

            when {
                validateNameEmailPassword(name, email, password) -> {
                    viewModel.registerUser(email, password)
                }
            }

            initViewModel()
        }
    }

    private fun initViewModel(){
        viewModel.stateRegister.observe(this, {state ->
            state?.let{
                navigateToHome(it)
            }
        })

        viewModel.loading.observe(this, {loading ->
            loading?.let{
                showLoading(it)
            }
        })

        viewModel.error.observe(this, {loading ->
            loading?.let{
                showErrorMessage(it)
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
        Snackbar.make(btn_register, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showLoading(status: Boolean) {
        when {
            status -> {
                pb_register.visibility = View.VISIBLE
            }
            else -> {
                pb_register.visibility = View.GONE
            }
        }
    }
}