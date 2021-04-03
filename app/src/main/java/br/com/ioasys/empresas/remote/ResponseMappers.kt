package br.com.ioasys.empresas.remote

import br.com.ioasys.empresas.data.Company
import br.com.ioasys.empresas.data.CompanyType

fun CompanyResponse.toModel(): Company{
    return Company(
        id = id,
        name = enterpriseName,
        pathImage = photo,
        city = city,
        country = country,
        description = description,
        sharePrice = sharePrice,
        type = enterpriseType.toModel()
    )
}

fun CompanyTypeResponse.toModel(): CompanyType{
    return CompanyType(
        id = id,
        typeName = enterpriseTypeName
    )
}