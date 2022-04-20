package tn.esprit.lolretrofit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class accountfound : AppCompatActivity() {
    lateinit var btnLogin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accountfound)
        btnLogin = findViewById(R.id.findAccount)


        btnLogin.setOnClickListener{
            val mainIntent = Intent(this, changepassword::class.java)


            startActivity(mainIntent)
            finish()
        }
    }
}