package br.com.ioasys.empresas.data.remote

import br.com.ioasys.empresas.BuildConfig
import br.com.ioasys.empresas.data.remote.login.LoginService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfiguration {

    companion object {
        fun <T> getApiService(service: Class<T>): T = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_API)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(service)

        private fun getClient(): OkHttpClient = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }
}