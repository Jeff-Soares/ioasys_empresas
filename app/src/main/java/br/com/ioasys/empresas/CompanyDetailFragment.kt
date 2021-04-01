package br.com.ioasys.empresas

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide

class CompanyDetailFragment : Fragment() {

    private val args: CompanyDetailFragmentArgs by navArgs()

    private lateinit var toolbar: Toolbar
    private lateinit var companyImageDetail: AppCompatImageView
    private lateinit var companyName: AppCompatTextView
    private lateinit var companyType: AppCompatTextView
    private lateinit var companyCountry: AppCompatTextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_company_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.company_toolbar_detail)
        companyImageDetail = view.findViewById(R.id.company_img_detail)
        companyName = view.findViewById(R.id.company_name_detail)
        companyType = view.findViewById(R.id.company_description_detail)
//        companyCountry = view.findViewById(R.id.company_country_detail)
        configureView()
        setupToolbar()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupToolbar(){
        val upArrow: Drawable = resources.getDrawable(R.drawable.ic_arrow_back, null)
        (activity as AppCompatActivity).run {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(upArrow)
        }

    }

    private fun configureView (){
        companyName.text = args.name
//        companyType.text = args.type
//        companyCountry.text = args.country
        setImage(args.imageUrl)
    }

    private fun setImage(url: String?){
        Glide.with(this)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.company_img)
            .dontAnimate()
            .into(companyImageDetail)
    }

}