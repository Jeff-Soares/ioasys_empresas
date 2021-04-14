package br.com.ioasys.empresas.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ioasys.empresas.presentation.CompanySearchState
import br.com.ioasys.empresas.presentation.FavoriteState

fun <T> ViewModel.companySearchState() = lazy {
    MutableLiveData<CompanySearchState<T>>()
}

fun <T> ViewModel.favoriteState() = lazy {
    MutableLiveData<FavoriteState<T>>()
}