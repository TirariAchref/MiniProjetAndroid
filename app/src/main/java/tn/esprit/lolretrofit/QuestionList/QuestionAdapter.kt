package tn.esprit.lolretrofit.QuestionList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.lolretrofit.R
import tn.esprit.lolretrofit.models.Question

class QuestionAdapter(val QuestionList: MutableList<Question>) : RecyclerView.Adapter<QuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemquestion, parent, false)

        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {

        val name = QuestionList[position].description
        val title = QuestionList[position].subject


        holder.QuestionName.text = name
        holder.QuestionTitle.text = title



    }

    override fun getItemCount() = QuestionList.size

}