package br.com.ioasys.empresas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.ioasys.empresas.databinding.ListCompanyBinding
import br.com.ioasys.empresas.models.Company
import com.bumptech.glide.Glide

class CompanyAdapter (private val companies: MutableList<Company>): RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val binding = ListCompanyBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return CompanyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.bind(companies[position])
    }

    override fun getItemCount(): Int = companies.size

    inner class CompanyViewHolder (private val itemBinding: ListCompanyBinding): RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(company: Company) {
            with (itemBinding){
                companyName.text = company.name
                companyType.text = company.type
                companyCountry.text = company.country
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

}