package br.com.ioasys.empresas.presentation.data

data class Company(
    val id: Int,
    val name: String,
    val pathImage: String?,
    val city: String?,
    val country: String,
    val description: String?,
    val sharePrice: Double?,
    val type: CompanyType
)

data class CompanyType(
    val id : Int,
    val typeName: String
)
