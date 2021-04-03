package br.com.ioasys.empresas.ui

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.ioasys.empresas.BuildConfig
import br.com.ioasys.empresas.R
import br.com.ioasys.empresas.databinding.FragmentCompanyDetailBinding
import com.bumptech.glide.Glide

class CompanyDetailFragment : Fragment() {

    private val args: CompanyDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentCompanyDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompanyDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            companyNameDetail.text = args.name
            companyDescriptionDetail.text = args.description
            companyDescriptionDetail.movementMethod = ScrollingMovementMethod()
            if (!args.imageUrl.isNullOrEmpty()) setImage(companyImgDetail, args.imageUrl)
        }
        setupToolbar()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupToolbar(){
        val upArrow: Drawable = resources.getDrawable(R.drawable.ic_arrow_back, null)
        (activity as AppCompatActivity).run {
            setSupportActionBar(binding.companyToolbarDetail)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(upArrow)
        }

    }

    private fun setImage(view: ImageView, url: String?){
        Glide.with(this)
            .load(BuildConfig.BASE_URL + url)
            .centerCrop()
            .placeholder(R.drawable.company_img)
            .dontAnimate()
            .into(view)
    }

}