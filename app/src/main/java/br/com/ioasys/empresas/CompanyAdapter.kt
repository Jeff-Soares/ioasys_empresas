package br.com.ioasys.empresas

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.com.ioasys.empresas.databinding.ItemCompanyBinding
import br.com.ioasys.empresas.models.Company
import com.bumptech.glide.Glide

class CompanyAdapter (private val callback: (Company) -> Unit): RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder>() {

    private var companies: List<Company> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val binding = ItemCompanyBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return CompanyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.bind(companies[position])
    }

    override fun getItemCount(): Int = companies.size

    inner class CompanyViewHolder (private val binding: ItemCompanyBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(company: Company) {
            with (binding){
                companyName.text = company.name
                companyType.text = company.description
                companyCountry.text = company.country
                root.setOnClickListener{callback.invoke(company)}
                setImage(companyImg, company.pathImage)
            }
        }

        private fun setImage(view: ImageView, url: String){
            Glide.with(itemView.context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.company_img)
                .dontAnimate()
                .into(view)
        }

    }

    fun setItems(list: List<Company>){
        companies = list
        notifyDataSetChanged()
    }
}