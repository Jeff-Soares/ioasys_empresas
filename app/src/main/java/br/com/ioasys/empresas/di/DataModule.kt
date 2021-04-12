package br.com.ioasys.empresas.di

import br.com.ioasys.empresas.data.CompanyRepository
import br.com.ioasys.empresas.data.LoginRepository
import br.com.ioasys.empresas.data.remote.company.CompanyRepositoryImpl
import br.com.ioasys.empresas.data.remote.login.LoginRepositoryImpl
import org.koin.dsl.module

val dataModule = module {

    single<LoginRepository> { LoginRepositoryImpl(get(),get()) }
    single<CompanyRepository> { CompanyRepositoryImpl(get(), get()) }

}