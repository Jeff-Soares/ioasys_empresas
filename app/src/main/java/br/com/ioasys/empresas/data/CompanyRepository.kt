package br.com.ioasys.empresas.data

import br.com.ioasys.empresas.data.remote.ResultWrapper
import br.com.ioasys.empresas.presentation.model.Company

interface CompanyRepository {

    suspend fun getEnterprisesByName(query: String): ResultWrapper<List<Company>>

    suspend fun saveFavoriteEnterprise(company: Company)

    suspend fun removeFavoriteEnterprise(company: Company)

    suspend fun getFavoritesEnterprises(): List<Company>

    fun logout()

}