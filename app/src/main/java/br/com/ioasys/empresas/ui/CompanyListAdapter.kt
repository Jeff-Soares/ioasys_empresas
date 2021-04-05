package br.com.ioasys.empresas.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.com.ioasys.empresas.BuildConfig
import br.com.ioasys.empresas.R
import br.com.ioasys.empresas.databinding.ItemCompanyBinding
import br.com.ioasys.empresas.presentation.data.Company
import com.bumptech.glide.Glide

class CompanyListAdapter (private val callback: (Company) -> Unit): RecyclerView.Adapter<CompanyListAdapter.CompanyViewHolder>() {

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
                companyType.text = company.type.typeName
                companyCountry.text = company.country
                if (!company.pathImage.isNullOrEmpty()) setImage(companyImg, company.pathImage)
                root.setOnClickListener{ callback.invoke(company) }
            }
        }

        private fun setImage(view: ImageView, url: String?){
            Glide.with(itemView.context)
                .load(BuildConfig.BASE_URL + url)
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

    fun isEmpty(): Boolean{
        if (companies.isEmpty()) return true
        return false
    }
}