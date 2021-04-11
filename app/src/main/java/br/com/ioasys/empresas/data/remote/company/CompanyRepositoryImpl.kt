package br.com.ioasys.empresas.data.remote.company

import br.com.ioasys.empresas.data.CompanyRepository
import br.com.ioasys.empresas.data.local.LocalDataSource
import br.com.ioasys.empresas.data.remote.company.model.GetCompaniesResponse
import br.com.ioasys.empresas.data.remote.ResultWrapper
import br.com.ioasys.empresas.data.toModel
import br.com.ioasys.empresas.util.*
import br.com.ioasys.empresas.presentation.model.Company

class CompanyRepositoryImpl(
    private val service: CompanyService,
    private val localDataSource: LocalDataSource
): CompanyRepository {

    override suspend fun getEnterprisesByName(query: String): ResultWrapper<List<Company>> {
        return wrapResponse {
            service.getEnterprisesByName(
                headers = localDataSource.getHeadersFromPreferences().toMap(),
                name = query
            )
        } then ::getResultBody then ::mapCompanyResponse
    }

    private fun mapCompanyResponse(result: ResultWrapper<GetCompaniesResponse?>): ResultWrapper<List<Company>> {
        val companiesResponse = result.data
        return ResultWrapper.put(companiesResponse?.companies?.map { it.toModel() })
    }

}