package br.com.ioasys.empresas.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "headers_table")
data class HeadersLocal(
    val token: String,
    val client: String,
    val uid: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
