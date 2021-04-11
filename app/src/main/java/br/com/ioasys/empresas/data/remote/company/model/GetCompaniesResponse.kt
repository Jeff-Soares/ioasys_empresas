package br.com.ioasys.empresas.data.remote.company.model

import com.google.gson.annotations.SerializedName

data class GetCompaniesResponse (
    @SerializedName("enterprises")
    val companies: List<CompanyResponse>

)
