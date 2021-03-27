package br.com.ioasys.empresas.models

data class Company(
    val id: Int,
    val name: String,
    val pathImage: String,
    val type: String,
    val country: String
)
