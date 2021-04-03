package br.com.ioasys.empresas.remote

import br.com.ioasys.empresas.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface CompanyService {

    @POST("users/auth/sign_in")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<Unit>

    @GET("enterprises")
    suspend fun getEnterprises(
        @Header("access-token") accessToken: String,
        @Header("client") client: String,
        @Header("uid") uid: String
    ): Response<GetCompaniesResponse>

    @GET("enterprises")
    suspend fun getEnterprisesByName(
        @Header("access-token") accessToken: String,
        @Header("client") client: String,
        @Header("uid") uid: String,
        @Query("name") name: String
    ): Response<GetCompaniesResponse>

    companion object {
        fun newInstance(): CompanyService = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_API)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(CompanyService::class.java)

        private fun getClient(): OkHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply{
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }
}