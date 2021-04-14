package br.com.ioasys.empresas.data.local

import br.com.ioasys.empresas.presentation.model.Company
import br.com.ioasys.empresas.presentation.model.CompanyType

fun Company.toLocalModel() =
    CompanyLocal(
        id = id,
        name = name,
        pathImage = pathImage,
        city = city,
        country = country,
        description = description,
        favorite = true
    )

fun CompanyLocal.fromLocalModel() =
    Company(
        id = id,
        name = name,
        pathImage = pathImage,
        city = city,
        country = country,
        description = description,
        favorite = favorite,
        sharePrice = null,
        type = CompanyType(0, "null")
    )
