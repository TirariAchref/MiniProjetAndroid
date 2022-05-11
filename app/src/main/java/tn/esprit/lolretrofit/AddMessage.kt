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
import tn.esprit.lolretrofit.models.Message
import tn.esprit.lolretrofit.models.Question
import tn.esprit.lolretrofit.models.User
import tn.esprit.lolretrofit.utils.ApiInterface

class AddMessage : AppCompatActivity() {
    private lateinit var txtEdit: EditText
    private lateinit var txtEditSub: EditText
    private lateinit var txtEditSubto: EditText

    lateinit var btnADDQuestion: Button
    lateinit var nowuser: User
    private lateinit var mSharedPref: SharedPreferences
    lateinit var amainIntent : Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_message)
        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);


        toolbar.setNavigationOnClickListener {


            finish()
        }
        txtEdit = findViewById(R.id.Edittext)
        txtEditSub = findViewById(R.id.EdittextSubject)
        txtEditSubto = findViewById(R.id.Edittextto)
        btnADDQuestion= findViewById(R.id.btnAddQuestion)

        amainIntent = Intent(this, HomeActivity::class.java)
        btnADDQuestion.setOnClickListener{
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
        jsonParams["message"] = txtEdit.text.toString()
        jsonParams["objectt"] = txtEditSub.text.toString()
        jsonParams["name"] = nowuser.nom
        jsonParams["from"] = nowuser.email
        jsonParams["to"] = txtEditSubto.text.toString()


        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        apiInterface.addMessage(body).enqueue(object : Callback<Message> {

            override fun onResponse(call: Call<Message>, response: Response<Message>) {

                val user = response.body()

                if (user != null){
                    Toast.makeText(this@AddMessage, "Message Aded", Toast.LENGTH_SHORT).show()


                    startActivity(amainIntent)
                    finish()
                }else{
                    Toast.makeText(this@AddMessage, "can not Add Message", Toast.LENGTH_SHORT).show()
                }


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

            override fun onFailure(call: Call<Message>, t: Throwable) {
                Toast.makeText(this@AddMessage, t.message, Toast.LENGTH_SHORT).show()


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

        })


    }
}