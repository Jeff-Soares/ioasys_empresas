package br.com.ioasys.empresas.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ioasys.empresas.data.CompanyRepository
import br.com.ioasys.empresas.presentation.model.Company
import br.com.ioasys.empresas.data.remote.ResultWrapper.*
import br.com.ioasys.empresas.util.companySearchState
import br.com.ioasys.empresas.util.favoriteState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompanyListViewModel(
    private val repository: CompanyRepository
) : ViewModel() {
    private val _companiesLiveData by companySearchState<List<Company>>()
    val companiesLiveData: LiveData<CompanySearchState<List<Company>>> = _companiesLiveData
    private val _favoritesLiveData by favoriteState<Int>()
    val favoritesLiveData: LiveData<FavoriteState<Int>> = _favoritesLiveData

    fun getCompaniesByName(query: String) {
        _companiesLiveData.value = CompanySearchState.loading(true)
        viewModelScope.launch(Dispatchers.Main) {
            when (val response = repository.getEnterprisesByName(query = query)) {
                is Success -> onSearchSuccess(response.data)
                is Failure -> onSearchError()
            }
        }
    }

    private suspend fun onSearchSuccess(list: List<Company>?) {
        list?.let {
            val fav = repository.getFavoritesEnterprises()
            list.map { if (fav.contains(it)) it.favorite = true }
            _companiesLiveData.value = CompanySearchState.success(list)
        }
    }

    private fun onSearchError() {
        _companiesLiveData.value = CompanySearchState.error(Throwable("Search Failed"))
    }

    fun getCompaniesFromFavorites() {
        viewModelScope.launch(Dispatchers.Main) {
            _companiesLiveData.value = CompanySearchState.success(repository.getFavoritesEnterprises())
        }
    }

    fun logout (){
        repository.logout()
    }

    fun favoriteDialogConfirm(company: Company, index: Int){
        if (!company.favorite) {
            saveCompanyIntoFavorites(company)
            _favoritesLiveData.value = FavoriteState.successAdd(index)
        } else {
            removeCompanyFromFavorites(company)
            _favoritesLiveData.value = FavoriteState.successRemoved(index)
        }
    }

    private fun saveCompanyIntoFavorites(company: Company) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.saveFavoriteEnterprise(company)
        }
    }

    private fun removeCompanyFromFavorites(company: Company) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.removeFavoriteEnterprise(company)
        }
    }

}