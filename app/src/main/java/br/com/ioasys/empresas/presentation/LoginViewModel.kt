package br.com.ioasys.empresas.presentation

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ioasys.empresas.R
import br.com.ioasys.empresas.presentation.data.LoginFields
import br.com.ioasys.empresas.remote.CompanyService
import br.com.ioasys.empresas.remote.LoginRequest
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Headers
import retrofit2.Response

class LoginViewModel(
    private val service: CompanyService
) : ViewModel() {
    private val _headersLiveData = MutableLiveData<Headers>()
    val headersLiveData: LiveData<Headers> = _headersLiveData
    val login: LoginFields = LoginFields()

    fun login() {
        if (login.validateEmail() && login.validatePassword()) {
            CoroutineScope(Dispatchers.Main).launch {
                val response = service.login(
                    LoginRequest(
                        email = login.email,
                        password = login.password
                    )
                )
                handleLogin(response)
            }
        }
    }

    private fun handleLogin(response: Response<Unit>) {
        if (response.isSuccessful) {
            _headersLiveData.value = response.headers()
        } else {
            login.validCredentials(false)
        }
    }

    fun getPasswordOnFocusChangeListener(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                login.emailError.set(null)
                login.passwordError.set(null)
            }
        }
    }

}

@BindingAdapter("error")
fun setError(view: TextInputLayout, observable: ObservableField<Int?>) {
    val msg = observable.get()
    msg?.apply { view.error = view.context.getString(this) }
        ?: run { view.isErrorEnabled = false }
}

@BindingAdapter("onTextChange")
fun onTextChange(view: TextInputEditText, watcher: TextWatcher) {
    view.addTextChangedListener(watcher)
}

@BindingAdapter("errorVisibility")
fun setErrorVisibility(view: TextInputLayout, observable: ObservableField<Int?>) {
    val visibility = observable.get() ?: return
    view.getChildAt(1)?.apply { this.visibility = visibility }
}
