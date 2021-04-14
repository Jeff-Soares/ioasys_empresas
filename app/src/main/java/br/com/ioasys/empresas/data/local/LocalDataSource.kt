package br.com.ioasys.empresas.data.local

import br.com.ioasys.empresas.presentation.model.Company
import okhttp3.Headers

interface LocalDataSource {

    fun saveHeadersToPreferences(headers: Headers)

    fun getHeadersFromPreferences(): Headers

    suspend fun saveFavoritesToLocalDatabase(company: Company)

    suspend fun removeFavoritesFromLocalDatabase(company: Company)

    suspend fun getFavoritesFromLocalDatabase(): List<Company>

    fun logout()

}