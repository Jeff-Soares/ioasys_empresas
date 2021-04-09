package br.com.ioasys.empresas.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ioasys.empresas.presentation.ViewState

fun <T> ViewModel.viewState() = lazy {
    MutableLiveData<ViewState<T>>()
}