package br.com.ioasys.empresas.remote

import com.google.gson.annotations.SerializedName

data class GetCompaniesResponse (
    @SerializedName("enterprises")
    val companies: List<CompanyResponse>

)
