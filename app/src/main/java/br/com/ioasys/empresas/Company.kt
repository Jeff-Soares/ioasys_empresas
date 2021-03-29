package br.com.ioasys.empresas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment

class Company : AppCompatActivity() {

    private val navController: NavController by lazy { findNavController(R.id.company_fragment_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CompanyList)
        setContentView(R.layout.activity_company)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}