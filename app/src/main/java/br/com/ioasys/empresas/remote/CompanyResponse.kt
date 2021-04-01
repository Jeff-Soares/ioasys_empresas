package br.com.ioasys.empresas.remote

import com.google.gson.annotations.SerializedName

data class CompanyResponse(
    @SerializedName("city")
    val city: String?,
    @SerializedName("country")
    val country: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("email_enterprise")
    val emailEnterprise: String?,
    @SerializedName("enterprise_name")
    val enterpriseName: String,
    @SerializedName("enterprise_type")
    val enterpriseType: CompanyTypeResponse,
    @SerializedName("facebook")
    val facebook: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("linkedin")
    val linkedin: String?,
    @SerializedName("own_enterprise")
    val ownEnterprise: Boolean,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("photo")
    val photo: String?,
    @SerializedName("share_price")
    val sharePrice: Double,
    @SerializedName("twitter")
    val twitter: String?,
    @SerializedName("value")
    val value: Int
)

data class CompanyTypeResponse(
    @SerializedName("enterprise_type_name")
    val enterpriseTypeName: String,
    @SerializedName("id")
    val id: Int
)