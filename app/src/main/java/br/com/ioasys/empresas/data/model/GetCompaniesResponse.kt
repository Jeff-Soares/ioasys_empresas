package br.com.ioasys.empresas.data.model

import com.google.gson.annotations.SerializedName

data class GetCompaniesResponse (
    @SerializedName("enterprises")
    val companies: List<CompanyResponse>

)
