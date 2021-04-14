package br.com.ioasys.empresas.di

import android.content.Context
import android.content.SharedPreferences
import br.com.ioasys.empresas.data.Constants
import br.com.ioasys.empresas.data.local.DatabaseConfiguration
import br.com.ioasys.empresas.data.local.LocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataLocalModule = module {

    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.SHARED_PREF_FILE, Context.MODE_PRIVATE)
    }

    single { LocalDataSource(provideSharedPreferences(androidContext()), get()) }

    single { DatabaseConfiguration.getDatabaseInstance(androidContext()).provideCompanyDao() }


}