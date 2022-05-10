package tn.esprit.lolretrofit

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.ArrayMap
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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

class ForgetPassword : AppCompatActivity() {
    lateinit var btnLogin: Button
    lateinit var mSharedPref: SharedPreferences
    lateinit var txtLogin: TextInputEditText
    lateinit var txtLayoutLogin: TextInputLayout
    var gson = Gson()
    lateinit var mainIntent : Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbarback)
        setSupportActionBar(toolbar)


        toolbar.setNavigationOnClickListener {

            mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }

        btnLogin = findViewById(R.id.findAccount)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        txtLogin = findViewById(R.id.txtEmail)
        txtLayoutLogin = findViewById(R.id.txtLayoutEmailConfirmation)
        btnLogin.setOnClickListener{
            txtLayoutLogin!!.error = null

            if (txtLogin?.text!!.isEmpty()) {
                txtLayoutLogin!!.error = "must not be empty"
                return@setOnClickListener
            }
            if (!isEmailValid(txtLogin?.text.toString())){
                txtLayoutLogin!!.error = "Check your email !"

                return@setOnClickListener
            }


            mainIntent = Intent(this, verificationcode::class.java)
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


        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        apiInterface.getuserbyemail(txtLogin!!.text.toString()).enqueue(object : Callback<MutableList<User>>{

            override fun onResponse(call: Call<MutableList<User>>, response: Response<MutableList<User>>) {

                val user = response.body()

                if (user != null){
                    if(user.size != 1){
                        Toast.makeText(this@ForgetPassword, "User not found", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@ForgetPassword, "User Found", Toast.LENGTH_SHORT).show()

                        for (name in user) {
                            Log.d("user",name.toString())

                            val json = gson.toJson(name)
                            print("////////////////////////////////////////////////")
                            Log.d("json",json.toString())
                            mSharedPref.edit().apply {
                                putString(myuser, json)

                            }.apply()
                            startActivity(mainIntent)
                            finish()
                        }

                    }

                }else{
                    Toast.makeText(this@ForgetPassword, "User not found", Toast.LENGTH_SHORT).show()
                }


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

            override fun onFailure(call: Call<MutableList<User>>, t: Throwable) {
                Toast.makeText(this@ForgetPassword, t.message, Toast.LENGTH_SHORT).show()


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

        })


    }
}