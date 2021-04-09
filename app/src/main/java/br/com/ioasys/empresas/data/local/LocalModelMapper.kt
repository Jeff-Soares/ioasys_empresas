package br.com.ioasys.empresas.data.local

import br.com.ioasys.empresas.data.Constants.HEADER_ACCESS_TOKEN
import br.com.ioasys.empresas.data.Constants.HEADER_CLIENT
import br.com.ioasys.empresas.data.Constants.HEADER_UID
import br.com.ioasys.empresas.data.Constants.KEY_ACCESS_TOKEN
import br.com.ioasys.empresas.data.Constants.KEY_UID
import br.com.ioasys.empresas.data.Constants.KEY_CLIENT
import okhttp3.Headers

fun Headers.toLocalModel() =
    HeadersLocal(
        token = this[KEY_ACCESS_TOKEN] ?: "",
        uid = this[KEY_UID] ?: "",
        client = this[KEY_CLIENT] ?: ""
    )

fun HeadersLocal.fromLocalModel() =
    Headers.Builder()
        .add(HEADER_ACCESS_TOKEN, token)
        .add(HEADER_UID, uid)
        .add(HEADER_CLIENT, client)
        .build()
