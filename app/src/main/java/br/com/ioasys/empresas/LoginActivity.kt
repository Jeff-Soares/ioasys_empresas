package br.com.ioasys.empresas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Empresas)
        setContentView(R.layout.activity_login)
    }

}