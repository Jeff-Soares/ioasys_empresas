package br.com.ioasys.empresas.presentation.data

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import br.com.ioasys.empresas.R

data class LoginFields(
    var email: String = "",
    var password: String = ""
) : BaseObservable() {

    val emailError: ObservableField<Int?> = ObservableField()
    val passwordError: ObservableField<Int?> = ObservableField()
    var emailErrorVisibility: ObservableField<Int?> = ObservableField()

    fun validateEmail(): Boolean {
        resetErrors()
        if (email.trim().isEmpty()) {
            emailError.set(R.string.email_required)
            return false
        } else {
            val isValid: Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            if (!isValid) {
                emailError.set(R.string.email_invalid)
                return false
            }
        }
        emailError.set(null)
        return true
    }

    fun validatePassword(): Boolean {
        resetErrors()
        if (password.trim().isEmpty()) {
            passwordError.set(R.string.pass_required)
            return false
        }
        passwordError.set(null)
        return true
    }

    fun validCredentials(valid: Boolean) {
        if (!valid) {
            emailError.set(R.string.invalid_credentials)
            passwordError.set(R.string.invalid_credentials)
            emailErrorVisibility.set(View.GONE)
        } else {
            resetErrors()
        }
    }

    private fun resetErrors() {
        emailError.set(null)
        passwordError.set(null)
        emailErrorVisibility.set(View.VISIBLE)
    }

}