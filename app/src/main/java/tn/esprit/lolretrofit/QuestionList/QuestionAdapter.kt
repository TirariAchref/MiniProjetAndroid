package tn.esprit.lolretrofit.QuestionList

import android.content.Intent
import android.util.ArrayMap
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.lolretrofit.QuestionDetails
import tn.esprit.lolretrofit.R
import tn.esprit.lolretrofit.models.Question
import tn.esprit.lolretrofit.models.User
import tn.esprit.lolretrofit.myuser
import tn.esprit.lolretrofit.utils.ApiInterface

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
        val imaClient = QuestionList[position].imageClient
        val idQuestion = QuestionList[position].id
        var imagee= QuestionList[position].imageClient
        holder.QuestionDescription.text = description
        holder.QuestionSubject.text = subject
if( imaClient!=null){
     imagee = "uploads/"+ imaClient.subSequence(8,imaClient.length)
    Log.d("image",imagee)
}


  Glide.with(holder.QuestionPic).load(ApiInterface.BASE_URL + imagee).placeholder(R.drawable.ic_account).circleCrop()
                            .error(R.drawable.ic_baseline_account_circle_24).into(holder.QuestionPic)




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