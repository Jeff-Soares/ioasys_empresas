package br.com.ioasys.empresas.data.remote.company

import br.com.ioasys.empresas.BuildConfig
import br.com.ioasys.empresas.data.remote.company.model.GetCompaniesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface CompanyService {

    @GET("enterprises")
    suspend fun getEnterprises(
        @Header("access-token") accessToken: String,
        @Header("client") client: String,
        @Header("uid") uid: String
    ): Response<GetCompaniesResponse>

    @GET("enterprises")
    suspend fun getEnterprisesByName(
        @HeaderMap headers: Map<String,String>,
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