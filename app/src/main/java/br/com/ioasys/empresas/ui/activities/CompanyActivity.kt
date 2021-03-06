package br.com.ioasys.empresas.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import br.com.ioasys.empresas.R

class CompanyActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CompanyList)
        setContentView(R.layout.activity_company)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.company_fragment_view)
                as NavHostFragment
        navController = navHostFragment.navController

        navController.setGraph(R.navigation.nav_graph, intent.extras)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}