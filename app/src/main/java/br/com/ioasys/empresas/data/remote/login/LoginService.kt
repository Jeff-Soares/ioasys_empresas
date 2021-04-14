package br.com.ioasys.empresas.data.remote.login

import br.com.ioasys.empresas.BuildConfig
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface LoginService {

    @POST("users/auth/sign_in")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<Headers>

}