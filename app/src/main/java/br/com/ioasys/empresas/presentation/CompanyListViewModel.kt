package br.com.ioasys.empresas.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ioasys.empresas.data.CompanyRepository
import br.com.ioasys.empresas.presentation.model.Company
import br.com.ioasys.empresas.data.remote.ResultWrapper.*
import br.com.ioasys.empresas.util.viewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompanyListViewModel(
    private val repository: CompanyRepository
) : ViewModel() {
    private val _companiesLiveData by viewState<List<Company>>()
    val companiesLiveData: LiveData<ViewState<List<Company>>> = _companiesLiveData

    fun getCompaniesByName(query: String) {
        viewModelScope.launch(Dispatchers.Main) {
            when (val response = repository.getEnterprisesByName(query = query)) {
                is Success -> onSearchSuccess(response.data)
                is Failure -> onSearchError()
            }
        }
    }

    private fun onSearchSuccess(list: List<Company>?) {
        list?.let { _companiesLiveData.value = ViewState.success(list) }
    }

    private fun onSearchError() {
        _companiesLiveData.value = ViewState.error(Throwable("Search Failed"))
    }

}