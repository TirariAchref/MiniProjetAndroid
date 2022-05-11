package tn.esprit.lolretrofit

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import tn.esprit.lolretrofit.models.User
import tn.esprit.lolretrofit.utils.ApiInterface

class ProfileFragment : Fragment() {

    private lateinit var txtFullName: TextView
    private lateinit var txtName: TextView
    private lateinit var txtemail: TextView
    private lateinit var txtPhone: TextView
    private lateinit var buttonChangepassword: TextView
    private lateinit var imageme: ShapeableImageView
    lateinit var nowuser: User
    private lateinit var mSharedPref: SharedPreferences
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var rootView : View = inflater.inflate(R.layout.fragment_profile, container, false)
        txtFullName = rootView.findViewById(R.id.idfullname)
        txtFullName.isEnabled = false
        mSharedPref =  this.requireActivity()?.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        txtName = rootView.findViewById(R.id.idAddress)
        txtName.isEnabled = false
        txtPhone = rootView.findViewById(R.id.idphone)
        txtPhone.isEnabled = false
        txtemail = rootView.findViewById(R.id.idEmail)
        txtemail.isEnabled = false
        imageme = rootView.findViewById(R.id.idUrlImg)
        buttonChangepassword= rootView.findViewById(R.id.edit)
        val gson = Gson()
        val  us =  mSharedPref.getString(myuser, "")

        nowuser = gson.fromJson(us,User::class.java)
        var imagee =""
        if( nowuser.imageUrl!=null){
            imagee = "uploads/"+ nowuser.imageUrl.subSequence(8,nowuser.imageUrl.length)

        }
        Glide.with(imageme).load(ApiInterface.BASE_URL + imagee).placeholder(R.drawable.ic_account).circleCrop()
            .error(R.drawable.ic_baseline_account_circle_24).into(imageme)

        val name = requireArguments().getString(fullname,"NULL")
        val ph = requireArguments().getString("phone","NULL")
        val emaill = requireArguments().getString("email","NULL")
        txtFullName.text = " $name"
        txtName.text = " $name"
        txtPhone.text = " $ph"
        txtemail.text = " $emaill"


        buttonChangepassword.setOnClickListener{
            val intent = Intent(activity, upadateprofile::class.java)
            startActivity(intent)



        }
        return rootView
    }
    companion object {

        @JvmStatic
        fun newInstance(full: String,phone :String,email:String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(fullname, full)
                    putString("phone", phone)
                    putString("email", email)

                }
            }
    }

}