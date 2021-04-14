package br.com.ioasys.empresas.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.ioasys.empresas.databinding.ItemCompanyBinding
import br.com.ioasys.empresas.presentation.model.Company
import br.com.ioasys.empresas.util.setCompanyImage

class CompanyListAdapter(private val clickListener: CompanyAdapterListener) :
    RecyclerView.Adapter<CompanyListAdapter.CompanyViewHolder>() {

    private var companies: MutableList<Company> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val binding = ItemCompanyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompanyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.bind(companies[position])
    }

    override fun getItemCount(): Int = companies.size

    inner class CompanyViewHolder(private val binding: ItemCompanyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(company: Company) {
            val position = adapterPosition
            with(binding) {
                companyName.text = company.name
                companyType.text = company.type.typeName
                companyCountry.text = company.country
                companyImg.setCompanyImage(company.pathImage)
                companyFavIcon.visibility = if(company.favorite) View.VISIBLE else View.GONE
                root.setOnClickListener { clickListener.onClickItem(company) }
                root.setOnLongClickListener { clickListener.onLongClickItem(company, position) }
            }
        }

    }

    fun setItems(list: List<Company>) {
        companies = list.toMutableList()
        notifyDataSetChanged()
    }

    fun setFavorite(position: Int){
        companies[position].favorite = true
        notifyItemChanged(position)
    }

    fun removeFavorite(position: Int){
        companies[position].favorite = false
        notifyItemChanged(position)
    }

    fun removeItem(position: Int){
        companies.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

}