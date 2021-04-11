package br.com.ioasys.empresas.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.ioasys.empresas.R
import br.com.ioasys.empresas.databinding.ActivityLoginBinding
import br.com.ioasys.empresas.presentation.LoginViewModel
import br.com.ioasys.empresas.presentation.LoginViewModelFactory
import br.com.ioasys.empresas.presentation.ViewState.State.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Empresas)

        setupBindings()
        setObservers()
    }

    private fun setupBindings() {
        loginBinding = DataBindingUtil
            .setContentView(this, R.layout.activity_login)
        viewModel = LoginViewModelFactory.create(this, this)
        loginBinding.viewModel = viewModel
    }

    private fun setObservers() {
        viewModel.loginStateLiveData.observe(this, { result ->
            when (result.state) {
                SUCCESS -> onLoginSuccess()
                ERROR -> onLoginError()
                LOADING -> onLoginLoading(result.isLoading)
            }
        })
    }

    private fun onLoginSuccess() {
        startActivity(Intent(this, CompanyActivity::class.java))
        finish()
    }

    private fun onLoginError() {
        Toast.makeText(
            this,
            getString(R.string.login_failed),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun onLoginLoading(loading: Boolean) {
        loginBinding.loadingGroup.visibility = if (loading) View.VISIBLE else View.GONE
    }

}