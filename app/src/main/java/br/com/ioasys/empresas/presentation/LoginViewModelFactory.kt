package br.com.ioasys.empresas.presentation

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import br.com.ioasys.empresas.data.Constants
import br.com.ioasys.empresas.data.LoginRepository
import br.com.ioasys.empresas.data.local.DatabaseConfiguration
import br.com.ioasys.empresas.data.local.HeadersDao
import br.com.ioasys.empresas.data.local.LocalDataSource
import br.com.ioasys.empresas.data.remote.login.LoginRepositoryImpl
import br.com.ioasys.empresas.data.remote.login.LoginService

class LoginViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LoginRepository::class.java)
            .newInstance(provideRepository())
    }

    companion object {
        fun create(from: ViewModelStoreOwner, context: Context): LoginViewModel {
            return ViewModelProvider(from, LoginViewModelFactory(context))
                .get(LoginViewModel::class.java)
        }
    }

    private fun provideRepository() =
        LoginRepositoryImpl(
            provideLoginService(),
            provideLocalDataSource()
        )

    private fun provideLoginService() =
        LoginService.newInstance()

    private fun provideLocalDataSource() =
        LocalDataSource(
            provideSharedPreferences(),
            provideHeadersDao()
        )

    private fun provideSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(Constants.SHARED_PREF_FILE, MODE_PRIVATE)
    }

    private fun provideHeadersDao(): HeadersDao {
        return DatabaseConfiguration.getDatabaseInstance(context).provideHeadersDao()
    }
}