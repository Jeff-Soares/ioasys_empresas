package br.com.ioasys.empresas.remote

import com.google.gson.annotations.SerializedName

data class CompanyResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("enterprise_name")
    val enterpriseName: String,
    @SerializedName("photo")
    val photo: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("country")
    val country: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("share_price")
    val sharePrice: Double,
    @SerializedName("enterprise_type")
    val enterpriseType: CompanyTypeResponse
)

data class CompanyTypeResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("enterprise_type_name")
    val enterpriseTypeName: String
)