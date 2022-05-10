package tn.esprit.lolretrofit

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.ArrayMap
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.lolretrofit.models.User
import tn.esprit.lolretrofit.utils.ApiInterface

class SignUp : AppCompatActivity() {

    lateinit var txtLogin: TextInputEditText
    lateinit var txtLayoutLogin: TextInputLayout

    lateinit var txtPassword: TextInputEditText
    lateinit var txtLayoutPassword: TextInputLayout
    lateinit var txtName: TextInputEditText
    lateinit var txtLayoutName: TextInputLayout

    lateinit var txtPasswordConfirmed: TextInputEditText
    lateinit var txtLayoutPasswordConfirmed: TextInputLayout

    lateinit var btnLogin: Button
    lateinit var txtPhone: TextInputEditText

    lateinit var buttonLogin: TextView
    var gson = Gson()
    lateinit var mSharedPref: SharedPreferences
    lateinit var txtLayoutPhone: TextInputLayout
    lateinit var mainIntent : Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        buttonLogin= findViewById(R.id.alreadyHaveAccount)
        txtLogin = findViewById(R.id.txtEmail)
        txtLayoutLogin = findViewById(R.id.txtLayoutEmail)

        txtPassword = findViewById(R.id.txtpassword)
        txtLayoutPassword = findViewById(R.id.txtLayoutpassword)

        txtName = findViewById(R.id.txtName)
        txtLayoutName = findViewById(R.id.txtLayoutName)

        txtPasswordConfirmed = findViewById(R.id.txttpasswordConfirmed)
        txtLayoutPasswordConfirmed = findViewById(R.id.txtLayoutpasswordCofirmed)

        txtPhone = findViewById(R.id.Phone)
        txtLayoutPhone = findViewById(R.id.LayoutPhone)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        btnLogin = findViewById(R.id.btnSubmit)
        buttonLogin.setOnClickListener{
            val mainIntent = Intent(this, MainActivity::class.java)


            startActivity(mainIntent)
            finish()
        }

        btnLogin!!.setOnClickListener {

            txtLayoutLogin!!.error = null
            txtLayoutPassword!!.error = null
            txtLayoutName!!.error = null
            txtLayoutPasswordConfirmed!!.error = null
            txtLayoutPhone!!.error = null



            if (txtName?.text!!.isEmpty()) {
                txtLayoutName!!.error = "must not be empty"
                return@setOnClickListener
            }
            if (txtLogin?.text!!.isEmpty()) {
                txtLayoutLogin!!.error = "must not be empty"
                return@setOnClickListener
            }
            if (!isEmailValid(txtLogin?.text.toString())){
                txtLayoutLogin!!.error = "Check your email !"

                return@setOnClickListener
            }
            if (txtPassword?.text!!.isEmpty()) {
                txtLayoutPassword!!.error = "must not be empty"
                return@setOnClickListener
            }

            if (txtPasswordConfirmed?.text!!.isEmpty()) {
                txtLayoutPasswordConfirmed!!.error = "must not be empty"
                return@setOnClickListener
            }
            if (txtPhone?.text!!.isEmpty()) {
                txtLayoutPhone!!.error = "must not be empty"
                return@setOnClickListener
            }
            if (txtPassword!!.text.toString() != txtPasswordConfirmed!!.text.toString()) {
                txtLayoutPassword!!.error = "Password don't match"
                txtLayoutPasswordConfirmed!!.error = "Password don't match"
                return@setOnClickListener
            }
            if (txtPhone?.text!!.length !=8) {
                txtLayoutPhone!!.error = "Number must have 8 digits"
                return@setOnClickListener
            }


            mainIntent = Intent(this, HomeActivity::class.java)

            doLogin()
        }
    }

    fun isEmailValid(email: String?): Boolean {
        return !(email == null || TextUtils.isEmpty(email)) && Patterns.EMAIL_ADDRESS.matcher(email)
            .matches()
    }
    private fun doLogin(){

        val apiInterface = ApiInterface.create()


        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        val jsonParams: MutableMap<String?, Any?> = ArrayMap()
//put something inside the map, could be null
//put something inside the map, could be null
        jsonParams["email"] = txtLogin!!.text.toString()
        jsonParams["password"] = txtPassword!!.text.toString()
        jsonParams["phone"] = txtPhone!!.text.toString()
        jsonParams["nom"] = txtName!!.text.toString()


        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        apiInterface.SignIn(body).enqueue(object : Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {

                val user = response.body()

                if (user != null){
                    Toast.makeText(this@SignUp, "Sign Up Success", Toast.LENGTH_SHORT).show()
                    Log.d("user",user.toString())

                    val json = gson.toJson(user)
                    print("////////////////////////////////////////////////")
                    Log.d("json",json.toString())
                    mSharedPref.edit().apply {
                        putString(myuser, json)

                    }.apply()
                    startActivity(mainIntent)
                    finish()
                }else{
                    Toast.makeText(this@SignUp, "can not sign up", Toast.LENGTH_SHORT).show()
                }


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@SignUp, t.message, Toast.LENGTH_SHORT).show()


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

        })


    }
}