package tn.esprit.lolretrofit

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import tn.esprit.lolretrofit.models.User

class qr_code : AppCompatActivity() {
    private lateinit var ivCODE : ImageView
    private lateinit var edata : EditText
    private lateinit var btngn : Button
    lateinit var nowuser: User

    private lateinit var mSharedPref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code)
//toolbar
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        val gson = Gson()
        val  us =  mSharedPref.getString(myuser, "")

        nowuser = gson.fromJson(us,User::class.java)
        val toolbar: Toolbar = findViewById(R.id.toolbarback)
        setSupportActionBar(toolbar)


        toolbar.setNavigationOnClickListener {

            finish()
        }

        ivCODE = findViewById(R.id.imageView)



            val data = nowuser.nom.toString().trim() +"  "+ nowuser.email.toString().trim() +"    "+ nowuser.phone.toString().trim()


                val write = QRCodeWriter()
                try {
                    val bitMatrix = write.encode(data, BarcodeFormat.QR_CODE, 512, 512)
                    val width = bitMatrix.width
                    val height = bitMatrix.height
                    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                    for (x in 0 until width) {
                        for (y in 0 until height) {

                            bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                        }
                    }
                    ivCODE.setImageBitmap(bmp)
                }catch (e: WriterException){
                    e.printStackTrace()
                }




    }
}