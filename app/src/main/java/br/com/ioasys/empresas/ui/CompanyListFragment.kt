package br.com.ioasys.empresas.ui

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import br.com.ioasys.empresas.R
import br.com.ioasys.empresas.data.Company
import br.com.ioasys.empresas.presentation.CompanyListViewModel
import br.com.ioasys.empresas.presentation.ViewModelFactory

class CompanyListFragment : Fragment() {

    private val compAdapter by lazy { CompanyAdapter(::clickItem) }
    private val args: CompanyListFragmentArgs by navArgs()
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarImg: ImageView
    private lateinit var searchView: SearchView
    private lateinit var textView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: CompanyListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_company_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.context.setTheme(R.style.Theme_CompanyList)
        toolbar = view.findViewById(R.id.toolbar_company)
        toolbarImg = view.findViewById(R.id.logo_toolbar)
        textView = view.findViewById(R.id.empty_recyclerview_text)
        searchView = view.findViewById(R.id.company_search)

        viewModel = ViewModelProvider(this, ViewModelFactory()).get(CompanyListViewModel::class.java)

        if (compAdapter.isEmpty()) textView.visibility = View.VISIBLE

        recyclerView = view.findViewById(R.id.recycler_view_company)
        recyclerView.adapter = compAdapter

        configureSearchView(searchView)
        setObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.company_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onResume() {
        super.onResume()
        if (!searchView.isIconified) toolbarImg.visibility = View.GONE
    }

    private fun setObservers() {
        viewModel.companiesLiveData.observe(viewLifecycleOwner, { list ->
            if (!list.isNullOrEmpty()) {
                textView.visibility = View.GONE
                compAdapter.setItems(list)
            } else {
                textView.text = getString(R.string.no_results)
                textView.visibility = View.VISIBLE
                compAdapter.setItems(listOf())
            }
        })
    }

    private fun configureSearchView(sv: SearchView) {
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getCompanies(
                    accessToken = args.accessToken,
                    client = args.client,
                    uid = args.uid,
                    query = sv.query.toString()
                )
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        sv.setOnSearchClickListener {
            toolbarImg.visibility = View.GONE
        }

        sv.setOnCloseListener {
            toolbarImg.visibility = View.VISIBLE
            return@setOnCloseListener false
        }

        sv.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                searchView.setQuery("", false)
                searchView.clearFocus()
                searchView.isIconified = true
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
