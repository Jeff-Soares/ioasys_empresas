package br.com.ioasys.empresas.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ioasys.empresas.presentation.CompanySearchState
import br.com.ioasys.empresas.presentation.FavoriteState
import br.com.ioasys.empresas.presentation.model.SingleLiveEvent

fun <T> ViewModel.companySearchState() = lazy {
    MutableLiveData<CompanySearchState<T>>()
}

fun <T> ViewModel.favoriteState() = lazy {
    SingleLiveEvent<FavoriteState<T>>()
}