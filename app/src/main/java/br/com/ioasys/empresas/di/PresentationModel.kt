package br.com.ioasys.empresas.di

import androidx.navigation.NavController
import br.com.ioasys.empresas.presentation.CompanyListViewModel
import br.com.ioasys.empresas.presentation.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel { LoginViewModel(get()) }
    viewModel { CompanyListViewModel(get()) }

}