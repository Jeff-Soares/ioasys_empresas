package br.com.ioasys.empresas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Empresas)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.loginSubmit).setOnClickListener{
            val intent = Intent(this, RecyclerActivity::class.java)
            startActivity(intent)
            finish()
        }
    }



}