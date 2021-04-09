package br.com.ioasys.empresas.data.local

import android.content.SharedPreferences
import okhttp3.Headers

class LocalDataSource(
    private val sharedPreferences: SharedPreferences,
    private val headersDao: HeadersDao
) {

    suspend fun saveHeadersToLocalDatabase(headers: Headers){
        headersDao.saveHeaders(headers.toLocalModel())
    }

    suspend fun getHeadersFromLocalDatabase(): Headers{
        return headersDao.getHeaders().fromLocalModel()
    }
}