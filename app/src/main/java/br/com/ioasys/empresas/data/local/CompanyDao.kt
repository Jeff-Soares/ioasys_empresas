package br.com.ioasys.empresas.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import retrofit2.http.DELETE

@Dao
interface CompanyDao {

    @Insert
    suspend fun saveCompany(company: CompanyLocal)

    @Query("SELECT * FROM companies_table")
    suspend fun getCompanies(): List<CompanyLocal>

    @Delete
    suspend fun removeCompany(company: CompanyLocal)

}