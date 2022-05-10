package tn.esprit.lolretrofit

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.lolretrofit.models.User
import tn.esprit.lolretrofit.models.sendmail
import tn.esprit.lolretrofit.utils.ApiInterface

class verificationcode : AppCompatActivity() {
    lateinit var btnLogin: Button
    private lateinit var txtFullName: TextView
    private lateinit var mSharedPref: SharedPreferences
    lateinit var nowuser: User
    lateinit var mainIntent : Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verificationcode)



        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbarback)
        setSupportActionBar(toolbar)


        toolbar.setNavigationOnClickListener {

            mainIntent = Intent(this, ForgetPassword::class.java)
            startActivity(mainIntent)
            finish()
        }
        txtFullName = findViewById(R.id.idfullname)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        val gson = Gson()
        val  us =  mSharedPref.getString(myuser, "")

        nowuser = gson.fromJson(us, User::class.java)
        txtFullName.text = nowuser.nom
        btnLogin = findViewById(R.id.findAccount)
        btnLogin.setOnClickListener{
             mainIntent = Intent(this, accountfound::class.java)


          doLogin()
        }
    }

    fun rand(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        return (start..end).random()
    }
    private fun doLogin(){

        val apiInterface = ApiInterface.create()


        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        val gson = Gson()
        val  us =  mSharedPref.getString(myuser, "")

        val random = rand(1000,9999)
        Log.d("random",random.toString())
        nowuser = gson.fromJson(us, User::class.java)
        val jsonParams: MutableMap<String?, Any?> = ArrayMap()
//put something inside the map, could be null
//put something inside the map, could be null
        jsonParams["email"] = nowuser.email
        jsonParams["code"] = random.toString()

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        apiInterface.sendmail(body).enqueue(object : Callback<sendmail> {

            override fun onResponse(call: Call<sendmail>, response: Response<sendmail>) {

                val user = response.body()

                if (user != null){
                    Toast.makeText(this@verificationcode, "mail send", Toast.LENGTH_SHORT).show()


                    Log.d("send mail",user.toString())
                    mSharedPref.edit().apply{
                        putString("code", random.toString())
                    }.apply()

                    startActivity(mainIntent)
                    finish()
                }else{
                    Toast.makeText(this@verificationcode, "mail didn't send ", Toast.LENGTH_SHORT).show()
                }


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

            override fun onFailure(call: Call<sendmail>, t: Throwable) {
                Toast.makeText(this@verificationcode, t.message, Toast.LENGTH_SHORT).show()


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

        })


    }

}