package tn.esprit.lolretrofit.MessageList

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.lolretrofit.QuestionDetails
import tn.esprit.lolretrofit.QuestionList.QuestionViewHolder
import tn.esprit.lolretrofit.R
import tn.esprit.lolretrofit.messagedetails
import tn.esprit.lolretrofit.models.Message
import tn.esprit.lolretrofit.models.Question

class MessageAdapter(val MessageList: MutableList<Message>) : RecyclerView.Adapter<MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemmmessage, parent, false)

        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {


        val description = MessageList[position].message
        val subject = MessageList[position].objectt

        val name = MessageList[position].name
        val from = MessageList[position].from
        val to = MessageList[position].to
        holder.MessageDescription.text = description
        holder.MessageSubject.text = subject
        holder.MessageName.text = name
        holder.itemView.setOnClickListener{ v ->

            val intent = Intent(v.context, messagedetails::class.java)

            intent.putExtra("description",description);
            intent.putExtra("subject",subject);
            intent.putExtra("from",from);

            v.context.startActivity(intent)


        }




    }

    override fun getItemCount() = MessageList.size

}