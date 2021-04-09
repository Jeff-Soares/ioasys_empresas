package br.com.ioasys.empresas.data

import br.com.ioasys.empresas.data.local.LocalDataSource
import br.com.ioasys.empresas.data.model.GetCompaniesResponse
import br.com.ioasys.empresas.data.remote.CompanyService
import br.com.ioasys.empresas.data.remote.LoginRequest
import br.com.ioasys.empresas.data.remote.ResultWrapper
import br.com.ioasys.empresas.util.*
import br.com.ioasys.empresas.presentation.model.Company
import okhttp3.Headers

class Repository(
    private val service: CompanyService,
    private val localDataSource: LocalDataSource
) {

    suspend fun login(email: String, password: String): ResultWrapper<Headers?> {
        return wrapResponse {
            service.login(
                LoginRequest(email = email, password = password)
            )
        } then ::getResultHeaders then ::saveHeaders
    }

    suspend fun getEnterprisesByName(
        query: String,
        headers: Headers
    ): ResultWrapper<List<Company>> {
        return wrapResponse {
            service.getEnterprisesByName(
                headers = headers.toMap(),
                name = query
            )
        } then ::getResultBody then ::mapCompanyResponse
    }

    private suspend fun saveHeaders(result: ResultWrapper<Headers?>): ResultWrapper<Headers?> {
        val headers = result.data
        headers?.let { localDataSource.saveHeadersToLocalDatabase(it) }
        return ResultWrapper.put(headers)
    }

    suspend fun getHeaders(): Headers {
        return localDataSource.getHeadersFromLocalDatabase()
    }

    private fun mapCompanyResponse(result: ResultWrapper<GetCompaniesResponse?>): ResultWrapper<List<Company>> {
        val companiesResponse = result.data
        return ResultWrapper.put(companiesResponse?.companies?.map { it.toModel() })
    }

}