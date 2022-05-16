package tn.esprit.lolretrofit

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.ArrayMap
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
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
import tn.esprit.lolretrofit.models.Reponse
import tn.esprit.lolretrofit.models.User
import tn.esprit.lolretrofit.utils.ApiInterface

class addreponse : AppCompatActivity() {
    private lateinit var txtEdit: EditText
    var idQuestion : String? = null
    var description : String? = null
    var subject : String? = null
    var idClient : String? = null
    lateinit var nowuser: User
    lateinit var btnADDReponse: Button
    private lateinit var mSharedPref: SharedPreferences
    lateinit var amainIntent : Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addreponse)

        val myIntent = intent
        description = myIntent.getStringExtra("description")
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        subject = myIntent.getStringExtra("subject")
        idClient = myIntent.getStringExtra("idClient")

        idQuestion = myIntent.getStringExtra("idQuestion")
        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)




        toolbar.setNavigationOnClickListener {

            val  mainIntent = Intent(this, AllReponse::class.java)

            mainIntent.putExtra("description",description);
            mainIntent.putExtra("subject",subject);
            mainIntent.putExtra("idClient",idClient);
            mainIntent.putExtra("idQuestion",idQuestion);
            startActivity(mainIntent)
            finish()
        }

        txtEdit = findViewById(R.id.Edittext)

        btnADDReponse= findViewById(R.id.btnListReponse)

        amainIntent = Intent(this, AllReponse::class.java)
        btnADDReponse.setOnClickListener{
            if (txtEdit?.text!!.isEmpty()) {
                Toast.makeText(this@addreponse, "Reponse must not be empty", Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            }
            doLogin()
        }
    }
    private fun doLogin(){
        val gson = Gson()
        val  us =  mSharedPref.getString(myuser, "")

        nowuser = gson.fromJson(us,User::class.java)
        val apiInterface = ApiInterface.create()


        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        val jsonParams: MutableMap<String?, Any?> = ArrayMap()
//put something inside the map, could be null
//put something inside the map, could be null
        jsonParams["description"] = txtEdit.text.toString()
        jsonParams["idUser"] = nowuser.id
        jsonParams["nameUser"] = nowuser.nom
        jsonParams["idQuestion"] = idQuestion





        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        apiInterface.addReponse(body).enqueue(object : Callback<Reponse> {

            override fun onResponse(call: Call<Reponse>, response: Response<Reponse>) {

                val user = response.body()

                if (user != null){
                    Toast.makeText(this@addreponse, "Reponse Aded", Toast.LENGTH_SHORT).show()

                    amainIntent.putExtra("description",description);
                    amainIntent.putExtra("subject",subject);
                    amainIntent.putExtra("idClient",idClient);
                    amainIntent.putExtra("idQuestion",idQuestion);
                    startActivity(amainIntent)
                    finish()
                }else{
                    Toast.makeText(this@addreponse, "can not Add Reponse", Toast.LENGTH_SHORT).show()
                }


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

            override fun onFailure(call: Call<Reponse>, t: Throwable) {
                Toast.makeText(this@addreponse, t.message, Toast.LENGTH_SHORT).show()


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

        })


    }
}