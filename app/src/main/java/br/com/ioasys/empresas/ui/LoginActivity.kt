package br.com.ioasys.empresas.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import br.com.ioasys.empresas.R
import br.com.ioasys.empresas.databinding.ActivityLoginBinding
import br.com.ioasys.empresas.presentation.LoginViewModel
import br.com.ioasys.empresas.presentation.ViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var submit: AppCompatButton
    private lateinit var viewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Empresas)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, ViewModelFactory()).get(LoginViewModel::class.java)

        with(binding) {
            emailInputLayout = loginEmailInputLayout
            passwordInputLayout = loginPassInputLayout
            email = loginEmailInputText
            password = loginPassInputText
            submit = loginSubmit
        }

        password.addTextChangedListener(ValidationTextWatcher(passwordInputLayout))
        email.addTextChangedListener(ValidationTextWatcher(emailInputLayout))

        submit.setOnClickListener {
            if (!validateEmail()) return@setOnClickListener
            if (!validatePassword()) return@setOnClickListener

            viewModel.login(email.text.toString(), password.text.toString())

//            CoroutineScope(Dispatchers.Main).launch {
//                val response = CompanyService.newInstance().login(LoginRequest(
//                        email = email.text.toString(),
//                        password = password.text.toString()
//                ))
//                handleLogin(response)
//            }
        }
        setObservers()
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

    private fun handleLogin(response: Response<Unit>) {
        if (response.isSuccessful) {
            validCredentials(true)
            val mIntent = Intent(this, CompanyActivity::class.java)
            val mBundle = Bundle()

            mBundle.putString("accessToken", response.headers()["access-token"])
            mBundle.putString("client", response.headers()["client"])
            mBundle.putString("uid", response.headers()["uid"])
            mIntent.putExtras(mBundle)

            startActivity(mIntent)
            finish()
        } else {
            validCredentials(false)
        }
    }

    private fun requestFocus(view: View) {
        if (view.requestFocus()) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

    private fun validatePassword(): Boolean {
        val passRequired = getString(R.string.pass_required)

        if (password.text.toString().trim().isEmpty()) {
            passwordInputLayout.error = passRequired
            requestFocus(password)
            return false
        } else {
            passwordInputLayout.isErrorEnabled = false
        }
        return true
    }

    private fun validateEmail(): Boolean {
        val emailRequired = getString(R.string.email_required)
        val emailInvalid = getString(R.string.email_invalid)

        if (email.text.toString().trim().isEmpty()) {
            emailInputLayout.error = emailRequired
            requestFocus(email)
            return false
        } else {
            val emailId: String = email.text.toString()
            val isValid: Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches()
            if (!isValid) {
                emailInputLayout.error = emailInvalid
                requestFocus(email)
                return false
            } else {
                emailInputLayout.isErrorEnabled = false
            }
        }
        return true
    }

    private fun validCredentials(isValid: Boolean) {
        if (!isValid) {
            val invalidCredential = getString(R.string.invalid_credentials)
            passwordInputLayout.error = invalidCredential
            emailInputLayout.error = " "
            emailInputLayout.getChildAt(1).visibility = View.GONE
        } else {
            emailInputLayout.isErrorEnabled = false
            passwordInputLayout.isErrorEnabled = false
        }
    }

    inner class ValidationTextWatcher(private val view: View) : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            emailInputLayout.isErrorEnabled = false
            passwordInputLayout.isErrorEnabled = false

            when (view.id) {
                R.id.login_pass_input_text -> validatePassword()
                R.id.login_email_input_text -> validateEmail()
            }
        }
    }

}