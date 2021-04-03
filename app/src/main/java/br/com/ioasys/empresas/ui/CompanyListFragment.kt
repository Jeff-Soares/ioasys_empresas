package br.com.ioasys.empresas.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import br.com.ioasys.empresas.R
import br.com.ioasys.empresas.models.Company
import br.com.ioasys.empresas.remote.CompanyService
import br.com.ioasys.empresas.remote.GetCompaniesResponse
import br.com.ioasys.empresas.remote.toModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class CompanyListFragment : Fragment() {

    private val compAdapter by lazy { CompanyAdapter(::clickItem) }
    private val args: CompanyListFragmentArgs by navArgs()
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarImg: ImageView
    private lateinit var searchView: SearchView
    private lateinit var textView: TextView
    private lateinit var recyclerView: RecyclerView

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

//        toolbar.inflateMenu(R.menu.company_menu)

        if (compAdapter.isEmpty()) textView.visibility = View.VISIBLE

        recyclerView = view.findViewById(R.id.recycler_view_company)
        recyclerView.adapter = compAdapter

        configureSearchView(searchView)

//        getCompanies()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.company_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

//        toolbar.inflateMenu(R.menu.company_menu)
//
//        toolbar.setOnMenuItemClickListener {
//            toolbarImg.visibility = View.GONE
//            return@setOnMenuItemClickListener false
//        }

//        toolbarImg.visibility = View.GONE

//        val searchItem: MenuItem = menu.findItem(R.id.company_search)
//        val searchView: SearchView = searchItem.actionView as SearchView
//
//        searchView.setOnSearchClickListener { toolbarImg.visibility = View.GONE }
//
//        searchView.setOnCloseListener {
//            toolbarImg.visibility = View.VISIBLE
//            return@setOnCloseListener false
//        }

    }

    override fun onResume() {
        super.onResume()
        if (!searchView.isIconified) toolbarImg.visibility = View.GONE
    }

    private fun getCompanies(query: String) {
//        CoroutineScope(Dispatchers.Main).launch {
//            val response = CompanyService.newInstance().getEnterprises(
//                accessToken = args.accessToken,
//                client = args.client,
//                uid = args.uid
//            )
//            handleResponse(response)
//        }
        CoroutineScope(Dispatchers.Main).launch {
            val response = CompanyService.newInstance().getEnterprisesByName(
                accessToken = args.accessToken,
                client = args.client,
                uid = args.uid,
                name = query
            )
            handleResponse(response)
        }
    }

    private fun handleResponse(response: Response<GetCompaniesResponse>) {
        if (response.isSuccessful) {
            val result = response.body()?.companies?.map { it.toModel() } ?: listOf()
            if (!result.isNullOrEmpty()) {
                textView.visibility = View.GONE
                compAdapter.setItems(result)
            } else {
                textView.text = getString(R.string.no_results)
                textView.visibility = View.VISIBLE
                compAdapter.setItems(listOf())
            }
        }
    }

    private fun configureSearchView(sv: SearchView) {
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                getCompanies(sv.query.toString())
                Log.i("QueryText", sv.query.toString())
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
