package tn.esprit.lolretrofit.MessageList

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.lolretrofit.R



    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val MessagePic : ImageView
        val MessageDescription : TextView
        val MessageSubject: TextView = itemView.findViewById<TextView>(R.id.Subject)
        val MessageName: TextView = itemView.findViewById<TextView>(R.id.name)
        init {
            MessagePic = itemView.findViewById<ImageView>(R.id.picc)
            MessageDescription = itemView.findViewById<TextView>(R.id.Description)
        }

    }
