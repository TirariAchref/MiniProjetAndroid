package tn.esprit.lolretrofit.QuestionList

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.lolretrofit.QuestionDetails
import tn.esprit.lolretrofit.R
import tn.esprit.lolretrofit.models.Question

class QuestionAdapter(val QuestionList: MutableList<Question>) : RecyclerView.Adapter<QuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemquestion, parent, false)

        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {


        val description = QuestionList[position].description
        val subject = QuestionList[position].subject

        val idClient = QuestionList[position].idClient
        val idQuestion = QuestionList[position].id
        holder.QuestionDescription.text = description
        holder.QuestionSubject.text = subject

        holder.itemView.setOnClickListener{ v ->

            val intent = Intent(v.context, QuestionDetails::class.java)
            intent.putExtra("description",description);
            intent.putExtra("subject",subject);
            intent.putExtra("idClient",idClient);
            intent.putExtra("idQuestion",idQuestion);
            v.context.startActivity(intent)

        }




    }

    override fun getItemCount() = QuestionList.size

}