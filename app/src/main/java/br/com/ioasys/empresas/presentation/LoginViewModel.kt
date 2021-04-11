package br.com.ioasys.empresas.presentation

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ioasys.empresas.data.LoginRepository
import br.com.ioasys.empresas.presentation.model.LoginFields
import br.com.ioasys.empresas.data.remote.ResultWrapper
import br.com.ioasys.empresas.data.remote.ResultWrapper.Success
import br.com.ioasys.empresas.data.remote.ResultWrapper.Failure
import br.com.ioasys.empresas.util.viewState
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Headers

class LoginViewModel(
    private val repository: LoginRepository
) : ViewModel() {
    private val _loginStateLiveData by viewState<Unit>()
    val loginStateLiveData: LiveData<ViewState<Unit>> = _loginStateLiveData
    val login: LoginFields = LoginFields()

    fun login() {
        if (login.validateEmail() && login.validatePassword()) {
            _loginStateLiveData.value = ViewState.loading(true)
            viewModelScope.launch(Dispatchers.Main) {
                handleLogin(repository.login(login.email, login.password))
            }
        }
    }

    private fun handleLogin(response: ResultWrapper<Headers?>) {
        when (response) {
            is Success -> onLoginSuccess(response.data)
            is Failure -> onLoginError()
        }
        _loginStateLiveData.value = ViewState.loading(false)
    }

    private fun onLoginSuccess(headers: Headers?) {
        headers?.let { _loginStateLiveData.value = ViewState.success(Unit) }
    }

    private fun onLoginError() {
        _loginStateLiveData.value = ViewState.error(Throwable("Login Failed"))
        login.setInvalidCredentialsError()
    }

    fun getPasswordOnFocusChangeListener(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                login.resetErrors()
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
    val visibilityState = observable.get() ?: return
    view.getChildAt(1)?.let { it.visibility = visibilityState }
}
