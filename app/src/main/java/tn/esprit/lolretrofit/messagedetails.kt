package tn.esprit.lolretrofit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class messagedetails : AppCompatActivity() {
    private lateinit var txtEdit: EditText
    private lateinit var txtEditfrom: EditText
    private lateinit var txtEditSubject: EditText

    lateinit var btnListReponse: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messagedetails)
        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)




        toolbar.setNavigationOnClickListener {

            val  mainIntent = Intent(this, HomeActivity::class.java)
            startActivity(mainIntent)
            finish()
        }
        val myIntent = intent
        val description = myIntent.getStringExtra("description")

        val subject = myIntent.getStringExtra("subject")
        val from = myIntent.getStringExtra("from")
        txtEditfrom = findViewById(R.id.Edittextfrom)
        txtEditSubject = findViewById(R.id.EdittextSubject)
        txtEdit = findViewById(R.id.Edittext)

        btnListReponse= findViewById(R.id.btnListReponse)
        txtEdit.setText(description)
        txtEditSubject.setText(subject)
        txtEditfrom.setText(from)

        btnListReponse!!.setOnClickListener {

            val mainIntent = Intent(this, AddMessage::class.java)

            startActivity(mainIntent)
            finish()


        }

    }
}