package tn.esprit.lolretrofit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.lolretrofit.ReponseList.ReponseAdapter
import tn.esprit.lolretrofit.models.Reponse
import tn.esprit.lolretrofit.models.User
import tn.esprit.lolretrofit.utils.ApiInterface

class AllReponse : AppCompatActivity() {
    var idQuestion : String? = null
    lateinit var recylcerReponse: RecyclerView
    lateinit var recylcerReponseAdapter: ReponseAdapter
    var ReponseList : MutableList<Reponse> = ArrayList()
    var UserList : MutableList<User> = ArrayList()
    lateinit var btnadd: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_reponse)
        val myIntent = intent
        val description = myIntent.getStringExtra("description")

        val subject = myIntent.getStringExtra("subject")
        val idClient = myIntent.getStringExtra("idClient")

        idQuestion = myIntent.getStringExtra("idQuestion")
        btnadd = findViewById(R.id.fab)
        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {


            finish()
        }

        recylcerReponse = findViewById(R.id.recyclerReponse)



        doLogin()


        recylcerReponseAdapter = ReponseAdapter(ReponseList)
        recylcerReponse.adapter = recylcerReponseAdapter
        recylcerReponse.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL ,false)
        btnadd.setOnClickListener{
            val mainIntent = Intent(this, addreponse::class.java)

            mainIntent.putExtra("description",description);
            mainIntent.putExtra("subject",subject);
            mainIntent.putExtra("idClient",idClient);
            mainIntent.putExtra("idQuestion",idQuestion);

            startActivity(mainIntent)

        }

    }

    private fun FindById(Id :String){

        val apiInterface = ApiInterface.create()





        apiInterface.getByid(Id).enqueue(object : Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {

                val user = response.body()

                if (user != null){
                    UserList.add(user)
                }else{

                }



            }

            override fun onFailure(call: Call<User>, t: Throwable) {




            }

        })


    }
    private fun doLogin(){

        val apiInterface = ApiInterface.create()



        apiInterface.AllReponse().enqueue(object : Callback<MutableList<Reponse>> {

            override fun onResponse(call: Call<MutableList<Reponse>>, response: Response<MutableList<Reponse>>) {

                val user = response.body()

                if (user != null) {
                    var ReponseListTrue : MutableList<Reponse> = ArrayList()
                    ReponseList=user
                    for (a in ReponseList){

                        if(a.idQuestion == idQuestion){

                            FindById(a.idUser)

                            ReponseListTrue.add(a)

                        }

                    }

                    recylcerReponseAdapter = ReponseAdapter(ArrayList(ReponseListTrue.asReversed()))
                    recylcerReponse.adapter = recylcerReponseAdapter

                }



            }

            override fun onFailure(call: Call<MutableList<Reponse>>, t: Throwable) {

            }

        })


    }
}