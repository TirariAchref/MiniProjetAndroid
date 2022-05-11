package tn.esprit.lolretrofit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class QuestionDetails : AppCompatActivity() {
    private lateinit var txtEdit: EditText
    lateinit var TextSubject: TextView
    lateinit var btnListReponse: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_details)
        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)




        toolbar.setNavigationOnClickListener {


            finish()
        }

        val myIntent = intent
        val description = myIntent.getStringExtra("description")

        val subject = myIntent.getStringExtra("subject")
        val idClient = myIntent.getStringExtra("idClient")

        val idQuestion = myIntent.getStringExtra("idQuestion")
        txtEdit = findViewById(R.id.Edittext)

        TextSubject = findViewById(R.id.QuestionSubject)
        btnListReponse= findViewById(R.id.btnListReponse)
        txtEdit.setText(description)
        TextSubject.setText(subject)
        TextSubject.isEnabled =false
        btnListReponse!!.setOnClickListener {

            val mainIntent = Intent(this, AllReponse::class.java)

            mainIntent.putExtra("description",description);
            mainIntent.putExtra("subject",subject);
            mainIntent.putExtra("idClient",idClient);
            mainIntent.putExtra("idQuestion",idQuestion);


            startActivity(mainIntent)



        }


    }
}