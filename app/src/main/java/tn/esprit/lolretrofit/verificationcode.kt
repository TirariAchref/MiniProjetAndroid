package tn.esprit.lolretrofit

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.gson.Gson
import tn.esprit.lolretrofit.models.User

class verificationcode : AppCompatActivity() {
    lateinit var btnLogin: Button
    private lateinit var txtFullName: TextView
    private lateinit var mSharedPref: SharedPreferences
    lateinit var nowuser: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verificationcode)
        txtFullName = findViewById(R.id.idfullname)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        val gson = Gson()
        val  us =  mSharedPref.getString(myuser, "")

        nowuser = gson.fromJson(us, User::class.java)
        txtFullName.text = nowuser.nom
        btnLogin = findViewById(R.id.findAccount)
        btnLogin.setOnClickListener{
            val mainIntent = Intent(this, accountfound::class.java)


            startActivity(mainIntent)
            finish()
        }
    }
}