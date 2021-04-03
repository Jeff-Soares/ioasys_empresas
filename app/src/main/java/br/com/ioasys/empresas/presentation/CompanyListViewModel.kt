package br.com.ioasys.empresas.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ioasys.empresas.data.Company
import br.com.ioasys.empresas.remote.CompanyService
import br.com.ioasys.empresas.remote.GetCompaniesResponse
import br.com.ioasys.empresas.remote.toModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class CompanyListViewModel(
    private val service: CompanyService
): ViewModel() {
    private val _companiesLiveData = MutableLiveData<List<Company>>()
    val companiesLiveData: LiveData<List<Company>> = _companiesLiveData

    fun getCompanies(accessToken: String, client: String, uid: String, query: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = service.getEnterprisesByName(
                accessToken = accessToken,
                client = client,
                uid = uid,
                name = query
            )
            handleResponse(response)
        }
    }

    private fun handleResponse(response: Response<GetCompaniesResponse>){
        if (response.isSuccessful){
            _companiesLiveData.value = response.body()?.companies?.map { it.toModel() }
        }
    }

}