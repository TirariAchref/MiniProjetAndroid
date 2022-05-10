package tn.esprit.lolretrofit.ReponseList

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.lolretrofit.R


class ReponseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val Reponsefrom : TextView
   val txtEdit: EditText

    init {

        Reponsefrom = itemView.findViewById<TextView>(R.id.from)
        txtEdit = itemView.findViewById<EditText>(R.id.Edittext)
    }

}