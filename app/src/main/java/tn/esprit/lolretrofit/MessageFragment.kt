package tn.esprit.lolretrofit

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.lolretrofit.MessageList.MessageAdapter
import tn.esprit.lolretrofit.QuestionList.QuestionAdapter
import tn.esprit.lolretrofit.models.Message
import tn.esprit.lolretrofit.models.Question
import tn.esprit.lolretrofit.models.User
import tn.esprit.lolretrofit.utils.ApiInterface

class MessageFragment : Fragment() {

    lateinit var recylcerChampion: RecyclerView
    lateinit var recylcerChampionAdapter: MessageAdapter
    var champList : MutableList<Message> = ArrayList()
    lateinit var btnadd: FloatingActionButton
    private lateinit var mSharedPref: SharedPreferences
    lateinit var nowuser: User
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_message, container, false)
        mSharedPref = this.requireActivity().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        btnadd = rootView.findViewById(R.id.fab)
        val gson = Gson()
        val  us =  mSharedPref.getString(myuser, "")

        nowuser = gson.fromJson(us,User::class.java)
        btnadd.setOnClickListener{
            val intent = Intent(activity, AddMessage::class.java)
            startActivity(intent)

        }

        recylcerChampion = rootView.findViewById(R.id.recyclerChampion)



        doLogin()


        recylcerChampionAdapter = MessageAdapter(champList)
        recylcerChampion.adapter = recylcerChampionAdapter
        recylcerChampion.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)


        return rootView
    }

    private fun doLogin(){

        val apiInterface = ApiInterface.create()



        apiInterface.AllMessage().enqueue(object : Callback<MutableList<Message>> {

            override fun onResponse(call: Call<MutableList<Message>>, response: Response<MutableList<Message>>) {

                val user = response.body()

                if (user != null) {

                    for(a in user){
                        if(a.from == nowuser.email || a.to == nowuser.email ){
                            champList.add(a)
                        }
                    }

                    recylcerChampionAdapter = MessageAdapter(ArrayList(champList.asReversed()))
                    recylcerChampion.adapter = recylcerChampionAdapter

                }



            }

            override fun onFailure(call: Call<MutableList<Message>>, t: Throwable) {

            }

        })


    }

}