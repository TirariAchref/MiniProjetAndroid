package tn.esprit.lolretrofit.ReponseList

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.lolretrofit.QuestionDetails
import tn.esprit.lolretrofit.QuestionList.QuestionViewHolder
import tn.esprit.lolretrofit.R
import tn.esprit.lolretrofit.models.Question
import tn.esprit.lolretrofit.models.Reponse
import tn.esprit.lolretrofit.models.User
import tn.esprit.lolretrofit.utils.ApiInterface

class ReponseAdapter(val ReponseList: MutableList<Reponse>) : RecyclerView.Adapter<ReponseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReponseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.reponseitem, parent, false)

        return ReponseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReponseViewHolder, position: Int) {

        val description = ReponseList[position].description
        val idUser = ReponseList[position].idUser

        val idClient = ReponseList[position].idUser
        val nomClient = ReponseList[position].nameUser
        val idQuestion = ReponseList[position].id


        holder.txtEdit.setText(description)

        holder.Reponsefrom.setText(nomClient)


    }

    override fun getItemCount() = ReponseList.size

}
