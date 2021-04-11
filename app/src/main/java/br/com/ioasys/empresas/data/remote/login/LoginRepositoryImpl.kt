package br.com.ioasys.empresas.data.remote.login

import br.com.ioasys.empresas.data.LoginRepository
import br.com.ioasys.empresas.data.local.LocalDataSource
import br.com.ioasys.empresas.data.remote.ResultWrapper
import br.com.ioasys.empresas.util.*
import okhttp3.Headers

class LoginRepositoryImpl(
    private val service: LoginService,
    private val localDataSource: LocalDataSource
) : LoginRepository {

    override suspend fun login(email: String, password: String): ResultWrapper<Headers?> {
        return wrapResponse {
            service.login(
                LoginRequest(email = email, password = password)
            )
        } then ::getResultHeaders then ::saveHeaders
    }

    override fun getHeaders(): Headers {
        return localDataSource.getHeadersFromPreferences()
    }

    private fun saveHeaders(result: ResultWrapper<Headers?>): ResultWrapper<Headers?> {
        val headers = result.data
        headers?.let { localDataSource.saveHeadersToPreferences(it) }
        return ResultWrapper.put(headers)
    }

}