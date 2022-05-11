package tn.esprit.lolretrofit

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import tn.esprit.lolretrofit.models.User
import tn.esprit.lolretrofit.utils.ApiInterface

class MenuFragment : Fragment() {

    private lateinit var txtFullName: TextView
    private lateinit var buttonChangepassword: TextView
    private lateinit var buttonMyQuestion: TextView
    private lateinit var buttonQrCode: TextView
    private lateinit var buttonGoogleMap: TextView
    private lateinit var buttonUpdateImage: TextView
    private lateinit var logout: TextView
    private lateinit var mSharedPref: SharedPreferences
    private lateinit var imageme: ShapeableImageView
    lateinit var nowuser: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var rootView : View = inflater.inflate(R.layout.fragment_menu, container, false)

        txtFullName = rootView.findViewById(R.id.idfullname)
        buttonChangepassword= rootView.findViewById(R.id.UpdateProfile)
        buttonMyQuestion= rootView.findViewById(R.id.mylist)
        buttonGoogleMap= rootView.findViewById(R.id.GoogleMap)
        buttonQrCode= rootView.findViewById(R.id.QrCode)
        buttonUpdateImage= rootView.findViewById(R.id.EditImage)
        logout= rootView.findViewById(R.id.logOut)
        txtFullName.isEnabled = false
        mSharedPref =  this.requireActivity()?.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        imageme = rootView.findViewById(R.id.idUrlImg)
        val gson = Gson()
        val  us =  mSharedPref.getString(myuser, "")
        val  facebooktest =  mSharedPref.getString(Facebookk, "")
        nowuser = gson.fromJson(us,User::class.java)
        var imagee =""
        if( nowuser.imageUrl!=null){
            imagee = "uploads/"+ nowuser.imageUrl.subSequence(8,nowuser.imageUrl.length)

        }
        Glide.with(imageme).load(ApiInterface.BASE_URL + imagee).placeholder(R.drawable.ic_account).circleCrop()
            .error(R.drawable.ic_baseline_account_circle_24).into(imageme)

        val name = requireArguments().getString(fullname,"NULL")
        mSharedPref = this.requireActivity().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        txtFullName.text = " $name"
if(facebooktest =="TRUE"){
    buttonChangepassword.setVisibility(View.INVISIBLE);
}
        buttonChangepassword.setOnClickListener{
            val intent = Intent(activity, changepassword::class.java)
            startActivity(intent)


        }
        buttonUpdateImage.setOnClickListener{
            val intent = Intent(activity, UploadImage::class.java)
            startActivity(intent)


        }
        buttonMyQuestion.setOnClickListener{
            val intent = Intent(activity, MyQuestion::class.java)
            startActivity(intent)


        }
        buttonGoogleMap.setOnClickListener{
            val intent = Intent(activity, mapbox::class.java)
            startActivity(intent)


        }
        buttonQrCode.setOnClickListener{
            val intent = Intent(activity, qr_code::class.java)
            startActivity(intent)


        }
        logout.setOnClickListener{

            val builder = AlertDialog.Builder(rootView.context)
            builder.setTitle("logout")
            builder.setMessage("Are you sure you want to logout?")
            builder.setPositiveButton("Yes"){ dialogInterface, which ->
                this.requireActivity().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE).edit().clear().apply()
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)

            }
            builder.setNegativeButton("No"){dialogInterface, which ->
                dialogInterface.dismiss()
            }
            builder.create().show()

        }
        return rootView
    }
    companion object {

        @JvmStatic
        fun newInstance(full: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putString(fullname, full)


                }
            }
    }

}