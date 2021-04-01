package br.com.ioasys.empresas.models

data class Company(
    val id: Int,
    val name: String,
    val pathImage: String,
    val country: String,
    val description: String,
    val type: CompanyType
)

data class CompanyType(
    val id : Int,
    val typeName: String
)
