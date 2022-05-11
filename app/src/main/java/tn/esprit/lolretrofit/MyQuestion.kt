package tn.esprit.lolretrofit

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.lolretrofit.QuestionList.QuestionAdapter
import tn.esprit.lolretrofit.models.Question
import tn.esprit.lolretrofit.models.User
import tn.esprit.lolretrofit.utils.ApiInterface

class MyQuestion : AppCompatActivity() {

    lateinit var recylcerChampion: RecyclerView
    lateinit var recylcerChampionAdapter: QuestionAdapter
    var champList : MutableList<Question> = ArrayList()
    lateinit var nowuser: User
    private lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_question)
        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        val gson = Gson()
        val  us =  mSharedPref.getString(myuser, "")

        nowuser = gson.fromJson(us,User::class.java)

        toolbar.setNavigationOnClickListener {


            finish()
        }
        recylcerChampion = findViewById(R.id.recyclerChampion)



        doLogin()


        recylcerChampionAdapter = QuestionAdapter(champList)
        recylcerChampion.adapter = recylcerChampionAdapter
        recylcerChampion.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL ,false)
    }

    private fun doLogin(){

        val apiInterface = ApiInterface.create()



        apiInterface.AllQuestion().enqueue(object : Callback<MutableList<Question>> {

            override fun onResponse(call: Call<MutableList<Question>>, response: Response<MutableList<Question>>) {

                val user = response.body()

                if (user != null) {
                    for(a in user){
                        if(a.idClient==nowuser.id){
                            champList.add(a)
                        }
                    }

                    recylcerChampionAdapter = QuestionAdapter(ArrayList(champList.asReversed()))
                    recylcerChampion.adapter = recylcerChampionAdapter

                }



            }

            override fun onFailure(call: Call<MutableList<Question>>, t: Throwable) {

            }

        })


    }
}