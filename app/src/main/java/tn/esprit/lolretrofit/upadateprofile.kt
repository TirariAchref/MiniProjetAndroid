package tn.esprit.lolretrofit

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
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

class upadateprofile : AppCompatActivity() {
    lateinit var btnLogin: Button
    lateinit var txtPassword: TextInputEditText
    lateinit var txtLayoutPassword: TextInputLayout
    lateinit var mainIntent : Intent
    private lateinit var mSharedPref: SharedPreferences
    lateinit var nowuser: User
    lateinit var txtPasswordConfirmed: TextInputEditText
    lateinit var txtLayoutPasswordConfirmed: TextInputLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upadateprofile)

        val toolbar: Toolbar = findViewById(R.id.toolbarback)
        setSupportActionBar(toolbar)


        toolbar.setNavigationOnClickListener {


            finish()
        }
        btnLogin = findViewById(R.id.findAccount)

        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        val gson = Gson()
        val  us =  mSharedPref.getString(myuser, "")

        nowuser = gson.fromJson(us, User::class.java)
        txtPassword = findViewById(R.id.txtEmail)
        txtLayoutPassword = findViewById(R.id.txtLayoutEmailConfirmation)



        txtPasswordConfirmed = findViewById(R.id.txtEmailm)
        txtLayoutPasswordConfirmed = findViewById(R.id.txtLayoutEmailConfirmationc)
        btnLogin.setOnClickListener{

            txtLayoutPassword!!.error = null

            txtLayoutPasswordConfirmed!!.error = null

            if (txtPassword?.text!!.isEmpty()) {
                txtLayoutPassword!!.error = "must not be empty"
                return@setOnClickListener
            }

            if (txtPasswordConfirmed?.text!!.isEmpty()) {
                txtLayoutPasswordConfirmed!!.error = "must not be empty"
                return@setOnClickListener
            }


            mainIntent = Intent(this, MainActivity::class.java)

            doLogin()
        }


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
        jsonParams["email"] = nowuser.email
        jsonParams["nom"] = txtPassword!!.text.toString()
        jsonParams["phone"] = txtPasswordConfirmed!!.text.toString()



        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        apiInterface.updateusernotpass(body,nowuser.id).enqueue(object : Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {

                val user = response.body()

                if (user != null){
                    Toast.makeText(this@upadateprofile, "User Updated", Toast.LENGTH_SHORT).show()
                    Log.d("user",user.toString())


                    startActivity(mainIntent)
                    finish()
                }else{
                    Toast.makeText(this@upadateprofile, "User can not Update", Toast.LENGTH_SHORT).show()
                }


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@upadateprofile, t.message, Toast.LENGTH_SHORT).show()


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

        })


    }
}