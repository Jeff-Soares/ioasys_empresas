package br.com.ioasys.empresas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.ioasys.empresas.models.Company
import com.bumptech.glide.Glide


class CompanyAdapter (private val companies: MutableList<Company>): RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_company, parent,false)
        return CompanyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.bind(companies[position])
    }

    override fun getItemCount(): Int = companies.size

    inner class CompanyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.company_img)
        private val textViewName: TextView = itemView.findViewById(R.id.company_name)
        private val textViewType: TextView = itemView.findViewById(R.id.company_type)
        private val textViewCountry: TextView = itemView.findViewById(R.id.company_country)

        fun bind(company: Company) {
            textViewName.text = company.name
            textViewType.text = company.type
            textViewCountry.text = company.country
            setImage(itemView, imageView, company.pathImage)
        }

        private fun setImage(context: View, myView: ImageView, url: String): ImageView{
            Glide
                .with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.company_img)
                .into(myView)
            return myView
        }

    }

}