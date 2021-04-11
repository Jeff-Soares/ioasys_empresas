package br.com.ioasys.empresas.data.remote.login

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
