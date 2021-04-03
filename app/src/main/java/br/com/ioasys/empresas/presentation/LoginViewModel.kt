package br.com.ioasys.empresas.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ioasys.empresas.remote.CompanyService
import br.com.ioasys.empresas.remote.LoginRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Headers
import retrofit2.Response

class LoginViewModel(
    private val service: CompanyService
): ViewModel() {
    private val _headersLiveData = MutableLiveData<Headers>()
    val headersLiveData: LiveData<Headers> = _headersLiveData

    fun login(email: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = service.login(
                LoginRequest(
                    email = email,
                    password = password
                )
            )
            handleLogin(response)
        }
    }

    private fun handleLogin(response: Response<Unit>) {
        if (response.isSuccessful){
            _headersLiveData.value = response.headers()
        }
    }

}