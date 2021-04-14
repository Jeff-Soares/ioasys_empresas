package br.com.ioasys.empresas.di

import br.com.ioasys.empresas.data.remote.ApiConfiguration
import br.com.ioasys.empresas.data.remote.company.CompanyService
import br.com.ioasys.empresas.data.remote.login.LoginService
import org.koin.dsl.module

val dataRemoteModule = module {

    single { ApiConfiguration.getApiService(LoginService::class.java) }
    single { ApiConfiguration.getApiService(CompanyService::class.java) }

}