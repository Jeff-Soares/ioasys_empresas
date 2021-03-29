package br.com.ioasys.empresas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.com.ioasys.empresas.models.Company

class CompanyListFragment : Fragment() {

    private val compAdapter by lazy { CompanyAdapter(::clickItem) }
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_company_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.context.setTheme(R.style.Theme_CompanyList)
        toolbar = view.findViewById(R.id.toolbar_company)

        recyclerView = view.findViewById(R.id.recycler_view_company)
        recyclerView.adapter = compAdapter

        compAdapter.setItems(companies)

    }

    private val companies: MutableList<Company> = mutableListOf(
        Company(1, "Empresa1", "https://thispersondoesnotexist.com/image", "Negocio", "Brasil"),
        Company(2, "Empresa2", "https://thispersondoesnotexist.com/image", "Negocio", "Brasil"),
        Company(3, "Empresa3", "https://thispersondoesnotexist.com/image", "Negocio", "Brasil"),
        Company(4, "Empresa4", "https://thispersondoesnotexist.com/image", "Negocio", "Brasil"),
        Company(5, "Empresa5", "https://thispersondoesnotexist.com/image", "Negocio", "Brasil"),
        Company(6, "Empresa6", "https://thispersondoesnotexist.com/image", "Negocio", "Brasil")
    )

    private fun clickItem(company: Company){
        findNavController().navigate(
            CompanyListFragmentDirections.actionCompanyListFragmentToCompanyDetailFragment(
                company.name,
                company.pathImage,
                company.type,
                company.country
            )
        )
    }

}
