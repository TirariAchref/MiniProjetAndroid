package tn.esprit.lolretrofit


import android.Manifest.permission.*
import android.R.attr.data
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import com.koushikdutta.ion.Response
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import tn.esprit.lolretrofit.models.User
import tn.esprit.lolretrofit.utils.ApiInterface
import java.io.File


class UploadImage : AppCompatActivity() {
    lateinit var imgsel: Button
    lateinit var upload:Button
    lateinit var img: ImageView
    lateinit  var path: String
    lateinit var uri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_image)
        Ion.getDefault(this).configure().setLogging("ion-sample", Log.DEBUG);
        img = findViewById(R.id.img);

        imgsel = findViewById(R.id.selimg);
        upload =findViewById(R.id.uploadimg);
        upload.setVisibility(View.INVISIBLE);
        upload.setOnClickListener {
            doLogin()
        }


        imgsel.setOnClickListener {
            val fintent = Intent(Intent.ACTION_GET_CONTENT)
            fintent.type = "image/jpeg"
            try {
                startActivityForResult(fintent, 100)
            } catch (e: ActivityNotFoundException) {
            }
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) return
        when (requestCode) {
            100 -> if (resultCode == RESULT_OK) {
                uri = data.data!!
                img.setImageURI(data.data)
                upload.visibility = View.VISIBLE
            }
        }
    }



    private fun doLogin(){

        val apiInterface = ApiInterface.create()



        val file = File(uri.path)
        val requestFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val imageBody: MultipartBody.Part = MultipartBody.Part.createFormData("Image", file.getName(), requestFile)


        apiInterface.upload(imageBody,"62569e823e025e70cf6adc15").enqueue(object : Callback<User> {

            override fun onResponse(call: Call<User>, response: retrofit2.Response<User>) {

                val user = response.body()

                if (user != null){
                    Toast.makeText(this@UploadImage, "Sign Up Success", Toast.LENGTH_SHORT).show()
                    Log.d("user",user.toString())

                }else{
                    Toast.makeText(this@UploadImage, "can not sign up", Toast.LENGTH_SHORT).show()
                }


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@UploadImage, t.message, Toast.LENGTH_SHORT).show()


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

        })


    }

}