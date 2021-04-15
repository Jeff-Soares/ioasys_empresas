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
        favorite = true,
        type = type.toLocalModel()
    )

fun CompanyType.toLocalModel() =
    CompanyLocalType(
        id = id,
        name = typeName
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
        type = type.fromLocalModel()
    )

fun CompanyLocalType.fromLocalModel() =
    CompanyType(
        id = id,
        typeName = name
    )