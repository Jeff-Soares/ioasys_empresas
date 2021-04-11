package br.com.ioasys.empresas.data.local

import android.content.SharedPreferences
import br.com.ioasys.empresas.data.Constants.HEADER_ACCESS_TOKEN
import br.com.ioasys.empresas.data.Constants.HEADER_CLIENT
import br.com.ioasys.empresas.data.Constants.HEADER_UID
import br.com.ioasys.empresas.data.Constants.KEY_ACCESS_TOKEN
import br.com.ioasys.empresas.data.Constants.KEY_CLIENT
import br.com.ioasys.empresas.data.Constants.KEY_UID
import okhttp3.Headers

class LocalDataSource(
    private val sharedPreferences: SharedPreferences,
    private val headersDao: HeadersDao
) {

    fun saveHeadersToPreferences(headers: Headers) {
        sharedPreferences.apply {
            val editor = edit()
            editor.putString(KEY_ACCESS_TOKEN, headers[HEADER_ACCESS_TOKEN])
            editor.putString(KEY_CLIENT, headers[HEADER_CLIENT])
            editor.putString(KEY_UID, headers[HEADER_UID])
            editor.apply()
        }
    }

    fun getHeadersFromPreferences(): Headers {
        return sharedPreferences.run {
            val token = getString(KEY_ACCESS_TOKEN, "") ?: ""
            val client = getString(KEY_CLIENT, "") ?: ""
            val uid = getString(KEY_UID, "") ?: ""
            Headers.Builder()
                .add(HEADER_ACCESS_TOKEN, token)
                .add(HEADER_CLIENT, client)
                .add(HEADER_UID, uid)
                .build()
        }
    }

    suspend fun saveHeadersToLocalDatabase(headers: Headers) {
        headersDao.saveHeaders(headers.toLocalModel())
    }

    suspend fun getHeadersFromLocalDatabase(): Headers {
        return headersDao.getHeaders().fromLocalModel()
    }
}