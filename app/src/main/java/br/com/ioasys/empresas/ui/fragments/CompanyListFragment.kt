package br.com.ioasys.empresas.ui.fragments

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.ioasys.empresas.R
import br.com.ioasys.empresas.databinding.FragmentCompanyListBinding
import br.com.ioasys.empresas.presentation.CompanyListViewModel
import br.com.ioasys.empresas.presentation.CompanySearchState.State.*
import br.com.ioasys.empresas.presentation.FavoriteState
import br.com.ioasys.empresas.presentation.model.Company
import br.com.ioasys.empresas.ui.activities.LoginActivity
import br.com.ioasys.empresas.ui.adapters.CompanyAdapterListener
import br.com.ioasys.empresas.ui.adapters.CompanyListAdapter
import br.com.ioasys.empresas.util.toast
import org.koin.androidx.viewmodel.ext.android.viewModel


class CompanyListFragment : Fragment() {

    private lateinit var binding: FragmentCompanyListBinding
    private val companyViewModel by viewModel<CompanyListViewModel>()
    private val compAdapter by lazy {
        CompanyListAdapter(setupAdapterListener())
    }
    private lateinit var alertDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.context.setTheme(R.style.Theme_CompanyList)

        alertDialog = AlertDialog.Builder(requireContext(), R.style.Theme_MyAlertDialogStyle).create()
        binding.recyclerViewCompany.adapter = compAdapter
        setupSearchView()
        setupDrawerLayout(binding.drawerLayout, binding.toolbarCompany)
        setObservers()
    }

    private fun setObservers() {
        companyViewModel.companiesLiveData.observe(viewLifecycleOwner) { result ->
            when (result.state) {
                SUCCESS -> onSearchSuccess(result.data)
                ERROR -> onSearchError()
                LOADING -> onSearchLoading(result.isLoading)
            }
        }
        companyViewModel.favoriteLiveEvent.observe(viewLifecycleOwner) { result ->
            when (result.state) {
                FavoriteState.State.SUCCESS_ADD -> onFavoriteSuccessAdd(result.data!!)
                FavoriteState.State.SUCCESS_REMOVED -> onFavoriteSuccessRemoved(result.data!!)
                FavoriteState.State.ERROR -> onFavoriteError()
            }
        }
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

    private fun onSearchLoading(isLoading: Boolean) {
        if (isLoading) binding.emptyRecyclerviewText.text = getString(R.string.loading_search)
    }

    private fun onFavoriteSuccessAdd(index: Int) {
        compAdapter.setFavorite(index)
        getString(R.string.added_to_fav).toast(requireContext())
    }

    private fun onFavoriteSuccessRemoved(index: Int) {
        compAdapter.removeItem(index)
        getString(R.string.remove_from_fav).toast(requireContext())
    }

    private fun onFavoriteError() {
        "Error".toast(requireContext())
    }

    private fun setupSearchView() {
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

    private fun setupDrawerLayout(drawer: DrawerLayout, toolbar: Toolbar) {
        (activity as AppCompatActivity).run {
            setSupportActionBar(toolbar)
            val drawerToggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.menu_open, R.string.menu_close
            )
            binding.companyNavigationView.setNavigationItemSelectedListener { item ->
                drawer.closeDrawers()
                binding.companyListLayout.requestFocus()
                when (item.itemId) {
                    R.id.op_favorite -> companyViewModel.getCompaniesFromFavorites()
                    R.id.op_logout -> clickLogout()
                    R.id.op_about -> clickAbout()
                }
                return@setNavigationItemSelectedListener false
            }
            drawer.addDrawerListener(drawerToggle)
            drawer.post {
                drawerToggle.syncState()
            }
        }
    }

    private fun clickLogout() {
        companyViewModel.logout()
        requireActivity().startActivity(Intent(requireActivity(), LoginActivity::class.java))
    }

    private fun clickAbout() {
        findNavController().navigate(
            CompanyListFragmentDirections.actionCompanyListFragmentToAboutFragment()
        )
    }

    private fun setupAdapterListener() = object : CompanyAdapterListener {

        override fun onClickItem(company: Company) {
            findNavController().navigate(
                CompanyListFragmentDirections.actionCompanyListFragmentToCompanyDetailFragment(
                    name = company.name,
                    imageUrl = company.pathImage,
                    description = company.description
                )
            )
        }

        override fun onLongClickItem(company: Company, index: Int): Boolean {
            alertDialog.apply {
                setTitle(getString(R.string.alert_dialog_fav_title))
                setMessage(
                    String.format(
                        if (!company.favorite) getString(R.string.alert_dialog_fav_add)
                        else getString(R.string.alert_dialog_fav_delete),
                        company.name
                    )
                )
                setButton(DialogInterface.BUTTON_NEUTRAL, getString(R.string.cancel)) { _, _ -> }
                setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.yes)) { _, _ ->
                    companyViewModel.favoriteDialogConfirm(company, index)
                }
                show()
            }
            return true
        }
    }

    override fun onResume() {
        super.onResume()
        if (!binding.companySearch.isIconified) binding.logoToolbar.visibility = View.GONE
    }

}
