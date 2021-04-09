package br.com.ioasys.empresas.util

import br.com.ioasys.empresas.data.remote.ResultWrapper
import br.com.ioasys.empresas.data.remote.ResultWrapper.Success
import br.com.ioasys.empresas.data.remote.ResultWrapper.Failure
import okhttp3.Headers
import retrofit2.Response
import java.lang.Exception

suspend fun <T> wrapResponse(request: suspend () -> Response<T>): ResultWrapper<Response<T>> {
    return try {
        val result = request()
        when (result.isSuccessful) {
            true -> Success(result)
            false -> throw Exception("The request response was not successful")
        }
    } catch (error: Exception) {
        Failure(Throwable(error.message))
    }
}

fun getResultHeaders(result: ResultWrapper<Response<Headers>>): ResultWrapper<Headers?> {
    return ResultWrapper.put(result.data?.headers())
}

fun <T> getResultBody(result: ResultWrapper<Response<T>>): ResultWrapper<T?> {
    return ResultWrapper.put(result.data?.body())
}
