package br.com.ioasys.empresas.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.ioasys.empresas.presentation.model.CompanyType

@Entity(tableName = "companies_table")
data class CompanyLocal(
    @PrimaryKey
    val id: Int,
    val name: String,
    val pathImage: String?,
    val city: String?,
    val country: String,
    val description: String?,
    var favorite: Boolean = false,
    @Embedded(prefix = "type_") val type: CompanyLocalType
)

data class CompanyLocalType(
    val id: Int,
    val name: String
)