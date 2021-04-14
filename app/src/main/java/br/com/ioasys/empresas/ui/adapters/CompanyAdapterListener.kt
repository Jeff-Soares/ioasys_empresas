package br.com.ioasys.empresas.ui.adapters

import br.com.ioasys.empresas.presentation.model.Company

interface CompanyAdapterListener{

    fun onClickItem(company: Company)

    fun onLongClickItem(company: Company, index: Int): Boolean

}