package br.com.ioasys.empresas.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.EditText
import br.com.ioasys.empresas.presentation.ViewState.State.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.ioasys.empresas.R
import br.com.ioasys.empresas.databinding.FragmentCompanyListBinding
import br.com.ioasys.empresas.presentation.model.Company
import br.com.ioasys.empresas.presentation.CompanyListViewModel
import br.com.ioasys.empresas.ui.adapters.CompanyListAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CompanyListFragment : Fragment() {

    private val compAdapter by lazy { CompanyListAdapter(::clickItem) }
    private lateinit var binding: FragmentCompanyListBinding
    private val companyViewModel by viewModel<CompanyListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyListBinding.inflate(
            layoutInflater,
            container,
            false
        )
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.context.setTheme(R.style.Theme_CompanyList)

        binding.recyclerViewCompany.adapter = compAdapter

        configureSearchView()
        setObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.company_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onResume() {
        super.onResume()
        if (!binding.companySearch.isIconified)
            binding.logoToolbar.visibility = View.GONE
    }

    private fun setObservers() {
        companyViewModel.companiesLiveData.observe(viewLifecycleOwner, { result ->
            when (result.state) {
                SUCCESS -> onSearchSuccess(result.data)
                ERROR -> onSearchError()
                LOADING -> onSearchLoading(result.isLoading)
            }
        })
    }

    private fun onSearchSuccess(list: List<Company>?) {
        binding.emptyRecyclerviewText.visibility = View.GONE
        compAdapter.setItems(list ?: listOf())
    }

    private fun onSearchError() {
        compAdapter.setItems(listOf())
        binding.emptyRecyclerviewText.text = getString(R.string.no_results)
        binding.emptyRecyclerviewText.visibility = View.VISIBLE
    }

    private fun onSearchLoading(loading: Boolean) {
        TODO("Implementar frase de loading na tela (ex: Buscando empresas...")
    }

    private fun configureSearchView() {
        val companySearch: SearchView = binding.companySearch

        companySearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?) = false
            override fun onQueryTextSubmit(query: String?): Boolean {
                companyViewModel.getCompaniesByName(query = companySearch.query.toString())
                return false
            }
        })

        companySearch.setOnSearchClickListener {
            binding.logoToolbar.visibility = View.GONE
        }

        companySearch.setOnCloseListener {
            binding.logoToolbar.visibility = View.VISIBLE
            return@setOnCloseListener false
        }

        companySearch.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                companySearch.setQuery("", false)
                companySearch.clearFocus()
                companySearch.isIconified = true
            }
        }
    }

    private fun clickItem(company: Company) {
        findNavController().navigate(
            CompanyListFragmentDirections.actionCompanyListFragmentToCompanyDetailFragment(
                name = company.name,
                imageUrl = company.pathImage,
                description = company.description
            )
        )
    }

}
