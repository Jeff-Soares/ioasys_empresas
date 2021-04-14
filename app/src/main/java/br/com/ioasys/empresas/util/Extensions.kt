package br.com.ioasys.empresas.util

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import br.com.ioasys.empresas.BuildConfig
import br.com.ioasys.empresas.R
import com.bumptech.glide.Glide

fun ImageView.setCompanyImage(url: String?) {
    Glide.with(this.context)
        .load(BuildConfig.BASE_URL + url)
        .centerCrop()
        .placeholder(R.drawable.company_img)
        .dontAnimate()
        .into(this)
}

fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(context, this.toString(), duration).apply { show() }
}
