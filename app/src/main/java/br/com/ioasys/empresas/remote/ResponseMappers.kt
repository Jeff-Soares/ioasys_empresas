package br.com.ioasys.empresas.remote

import br.com.ioasys.empresas.models.Company
import br.com.ioasys.empresas.models.CompanyType

fun CompanyResponse.toModel(): Company{
    return Company(
        id = id,
        name = enterpriseName,
        pathImage = photo?: "",
        description = description,
        country = country,
        type = enterpriseType.toModel()
    )
}

fun CompanyTypeResponse.toModel(): CompanyType{
    return CompanyType(
        id = id,
        typeName = enterpriseTypeName
    )
}