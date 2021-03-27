package br.com.ioasys.empresas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import br.com.ioasys.empresas.models.Company

class RecyclerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)


        val compAdapter = CompanyAdapter(companies)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_company)
        recyclerView.adapter = compAdapter

    }

    private val companies: MutableList<Company> = mutableListOf(
        Company(1, "Empresa1", "https://thispersondoesnotexist.com/", "Negocio", "Brasil"),
        Company(2, "Empresa2", "https://thispersondoesnotexist.com/", "Negocio", "Brasil"),
        Company(3, "Empresa3", "https://thispersondoesnotexist.com/", "Negocio", "Brasil"),
        Company(4, "Empresa4", "https://thispersondoesnotexist.com/", "Negocio", "Brasil"),
        Company(5, "Empresa5", "https://thispersondoesnotexist.com/", "Negocio", "Brasil"),
        Company(6, "Empresa6", "https://thispersondoesnotexist.com/", "Negocio", "Brasil")
    )
}