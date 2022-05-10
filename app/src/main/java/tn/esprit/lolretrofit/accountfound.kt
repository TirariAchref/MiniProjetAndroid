package tn.esprit.lolretrofit

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class accountfound : AppCompatActivity() {
    lateinit var btnLogin: Button
    lateinit var txtLogin: TextInputEditText
    lateinit var txtLayoutLogin: TextInputLayout
    lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accountfound)

        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbarback)
        setSupportActionBar(toolbar)


        toolbar.setNavigationOnClickListener {

          val  mainIntent = Intent(this, verificationcode::class.java)
            startActivity(mainIntent)
            finish()
        }
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        txtLogin = findViewById(R.id.txtEmail)
        txtLayoutLogin = findViewById(R.id.txtLayoutEmailConfirmation)
        btnLogin = findViewById(R.id.findAccount)


        btnLogin.setOnClickListener{
            if (txtLogin?.text!!.isEmpty()) {
                txtLayoutLogin!!.error = "must not be empty"
                return@setOnClickListener
            }
            if (txtLogin!!.text.toString()!=mSharedPref.getString("code","")) {
                txtLayoutLogin!!.error = "code doesn't match"
                return@setOnClickListener
            }
            val mainIntent = Intent(this, changepassword::class.java)


            startActivity(mainIntent)
            finish()
        }
    }
}