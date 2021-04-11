package br.com.ioasys.empresas.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import br.com.ioasys.empresas.data.CompanyRepository
import br.com.ioasys.empresas.data.Constants
import br.com.ioasys.empresas.data.local.DatabaseConfiguration
import br.com.ioasys.empresas.data.local.HeadersDao
import br.com.ioasys.empresas.data.local.LocalDataSource
import br.com.ioasys.empresas.data.remote.company.CompanyRepositoryImpl
import br.com.ioasys.empresas.data.remote.company.CompanyService

class CompanyViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CompanyRepository::class.java)
            .newInstance(provideRepository())
    }

    companion object {
        fun create(from: ViewModelStoreOwner, context: Context): CompanyListViewModel {
            return ViewModelProvider(from, CompanyViewModelFactory(context))
                .get(CompanyListViewModel::class.java)
        }
    }

    private fun provideRepository() =
        CompanyRepositoryImpl(
            provideCompanyService(),
            provideLocalDataSource()
        )

    private fun provideCompanyService() =
        CompanyService.newInstance()

    private fun provideLocalDataSource() =
        LocalDataSource(
            provideSharedPreferences(),
            provideHeadersDao()
        )

    private fun provideSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(Constants.SHARED_PREF_FILE, Context.MODE_PRIVATE)
    }

    private fun provideHeadersDao(): HeadersDao {
        return DatabaseConfiguration.getDatabaseInstance(context).provideHeadersDao()
    }
}