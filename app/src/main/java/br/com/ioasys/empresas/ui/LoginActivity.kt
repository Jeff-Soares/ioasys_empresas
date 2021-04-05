package br.com.ioasys.empresas.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import br.com.ioasys.empresas.R
import br.com.ioasys.empresas.databinding.ActivityLoginBinding
import br.com.ioasys.empresas.presentation.LoginViewModel
import br.com.ioasys.empresas.presentation.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Empresas)

        setupBindings()
        setObservers()
    }

    private fun setupBindings() {
        val loginBinding: ActivityLoginBinding = DataBindingUtil
            .setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this, ViewModelFactory())
            .get(LoginViewModel::class.java)
        loginBinding.viewModel = viewModel
    }

    private fun setObservers() {
        viewModel.headersLiveData.observe(this, { headers ->
            val mIntent = Intent(this, CompanyActivity::class.java)
            val mBundle = Bundle()
            mBundle.putString("accessToken", headers["access-token"])
            mBundle.putString("client", headers["client"])
            mBundle.putString("uid", headers["uid"])
            mIntent.putExtras(mBundle)

            startActivity(mIntent)
            finish()
        })
    }

}