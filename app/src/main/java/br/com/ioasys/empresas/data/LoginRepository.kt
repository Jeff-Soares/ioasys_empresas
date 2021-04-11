package br.com.ioasys.empresas.data

import br.com.ioasys.empresas.data.remote.ResultWrapper
import okhttp3.Headers

interface LoginRepository {

    suspend fun login(email: String, password: String): ResultWrapper<Headers?>

    fun getHeaders(): Headers

}