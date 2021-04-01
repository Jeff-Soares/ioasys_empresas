package br.com.ioasys.empresas

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import br.com.ioasys.empresas.databinding.ActivityLoginBinding
import br.com.ioasys.empresas.remote.CompanyService
import br.com.ioasys.empresas.remote.LoginRequest
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response


class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var submit: AppCompatButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Empresas)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            emailInputLayout = loginEmailInputLayout
            passwordInputLayout = loginPassInputLayout
            email = loginEmailInputText
            password = loginPassInputText
            submit = loginSubmit
        }

        password.addTextChangedListener(ValidationTextWatcher(passwordInputLayout))
        email.addTextChangedListener(ValidationTextWatcher(emailInputLayout))

        submit.setOnClickListener{
            if (!validateEmail()) return@setOnClickListener
            if (!validatePassword()) return@setOnClickListener

            CoroutineScope(Dispatchers.Main).launch {
                val response = CompanyService.newInstance().login(LoginRequest(
                    email = email.text.toString(),
                    password = password.text.toString()
                ))
                handleLogin(response)
            }

        }

    }

    private fun handleLogin(response: Response<Unit>){
        if (response.isSuccessful) {
//            Log.i("LOGIN", "Access Token = ${response.headers()["access-token"]}")
//            Log.i("LOGIN", "Client = ${response.headers()["client"]}")
//            Log.i("LOGIN", "Uid = ${response.headers()["uid"]}")

            val intent = Intent(this, CompanyActivity::class.java)
            val mBundle = Bundle()
            mBundle.putString("accessToken", response.headers()["access-token"])
            mBundle.putString("client", response.headers()["client"])
            mBundle.putString("uid", response.headers()["uid"])
            intent.putExtras(mBundle)
//            intent.putExtra("accessToken", response.headers()["access-token"])
//            intent.putExtra("client", response.headers()["client"])
//            intent.putExtra("uid", response.headers()["uid"])
            startActivity(intent)
            finish()
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

    inner class ValidationTextWatcher(private val view: View): TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            when (view.id) {
                R.id.login_pass_input_text -> validatePassword()
                R.id.login_email_input_text -> validateEmail()
            }
        }
    }

}