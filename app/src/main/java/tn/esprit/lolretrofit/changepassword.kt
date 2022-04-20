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
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.lolretrofit.models.User
import tn.esprit.lolretrofit.utils.ApiInterface

class changepassword : AppCompatActivity() {
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
        setContentView(R.layout.activity_changepassword)
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

            if (txtPassword!!.text.toString() != txtPasswordConfirmed!!.text.toString()) {
                txtLayoutPassword!!.error = "Password don't match"
                txtLayoutPasswordConfirmed!!.error = "Password don't match"
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
        jsonParams["password"] = txtPassword!!.text.toString()
        jsonParams["phone"] = nowuser.phone
        jsonParams["nom"] = nowuser.nom


        val body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            JSONObject(jsonParams).toString()
        )

        apiInterface.UpdateUser(body,nowuser.id).enqueue(object : Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {

                val user = response.body()

                if (user != null){
                    Toast.makeText(this@changepassword, "Password changed", Toast.LENGTH_SHORT).show()
                    Log.d("user",user.toString())


                    startActivity(mainIntent)
                    finish()
                }else{
                    Toast.makeText(this@changepassword, "Password can not change", Toast.LENGTH_SHORT).show()
                }


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@changepassword, t.message, Toast.LENGTH_SHORT).show()


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

        })


    }
}