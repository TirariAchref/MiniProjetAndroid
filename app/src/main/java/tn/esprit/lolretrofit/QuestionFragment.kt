package tn.esprit.lolretrofit

import android.content.Intent
import android.os.Bundle
import android.util.ArrayMap
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.lolretrofit.QuestionList.QuestionAdapter
import tn.esprit.lolretrofit.models.Question
import tn.esprit.lolretrofit.models.User
import tn.esprit.lolretrofit.utils.ApiInterface


class QuestionFragment : Fragment() {

    lateinit var recylcerChampion: RecyclerView
    lateinit var recylcerChampionAdapter: QuestionAdapter
    var champList : MutableList<Question> = ArrayList()
    lateinit var btnadd: FloatingActionButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_question, container, false)

        btnadd = rootView.findViewById(R.id.fab)
        btnadd.setOnClickListener{
            val intent = Intent(activity, AddQuestion::class.java)
            startActivity(intent)

        }

        recylcerChampion = rootView.findViewById(R.id.recyclerChampion)



        doLogin()


        recylcerChampionAdapter = QuestionAdapter(champList)
        recylcerChampion.adapter = recylcerChampionAdapter
        recylcerChampion.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)


        return rootView

    }

    private fun doLogin(){

        val apiInterface = ApiInterface.create()



        apiInterface.AllQuestion().enqueue(object : Callback<MutableList<Question>> {

            override fun onResponse(call: Call< MutableList<Question> >, response: Response< MutableList<Question> >) {

                val user = response.body()

                if (user != null) {
                    champList=user
                    recylcerChampionAdapter = QuestionAdapter(champList)
                    recylcerChampion.adapter = recylcerChampionAdapter

                }



            }

            override fun onFailure(call: Call< MutableList<Question>>, t: Throwable) {

            }

        })


    }

}