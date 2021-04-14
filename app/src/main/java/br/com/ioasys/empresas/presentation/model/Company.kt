package br.com.ioasys.empresas.presentation.model

data class Company(
    val id: Int,
    val name: String,
    val pathImage: String?,
    val city: String?,
    val country: String,
    val description: String?,
    val sharePrice: Double?,
    var favorite: Boolean = false,
    val type: CompanyType
){
    override fun equals(other: Any?): Boolean {
        (other as Company)
        return id == other.id
    }
}

data class CompanyType(
    val id : Int,
    val typeName: String
)
