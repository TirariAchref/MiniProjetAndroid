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

class MenuFragment : Fragment() {

    private lateinit var txtFullName: TextView
    private lateinit var buttonChangepassword: TextView
    private lateinit var buttonMyQuestion: TextView
    private lateinit var logout: TextView
    private lateinit var mSharedPref: SharedPreferences
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var rootView : View = inflater.inflate(R.layout.fragment_menu, container, false)

        txtFullName = rootView.findViewById(R.id.idfullname)
        buttonChangepassword= rootView.findViewById(R.id.UpdateProfile)
        buttonMyQuestion= rootView.findViewById(R.id.mylist)
        logout= rootView.findViewById(R.id.logOut)
        txtFullName.isEnabled = false

        val name = requireArguments().getString(fullname,"NULL")
        mSharedPref = this.requireActivity().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        txtFullName.text = " $name"

        buttonChangepassword.setOnClickListener{
            val intent = Intent(activity, changepassword::class.java)
            startActivity(intent)


        }
        buttonMyQuestion.setOnClickListener{
            val intent = Intent(activity, MyQuestion::class.java)
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