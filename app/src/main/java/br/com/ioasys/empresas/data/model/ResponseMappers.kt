package br.com.ioasys.empresas.data

import br.com.ioasys.empresas.data.model.CompanyResponse
import br.com.ioasys.empresas.data.model.CompanyTypeResponse
import br.com.ioasys.empresas.presentation.model.Company
import br.com.ioasys.empresas.presentation.model.CompanyType

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