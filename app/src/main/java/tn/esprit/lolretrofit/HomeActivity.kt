package tn.esprit.lolretrofit

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.livedata.ChatDomain
import tn.esprit.lolretrofit.models.User


class HomeActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView
    lateinit var nowuser: User
    private lateinit var mSharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val client =
            ChatClient.Builder("nueuxazsvj3k", this).logLevel(ChatLogLevel.ALL).build()
        ChatDomain.Builder(client, this).build()
        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, QuestionFragment()).commit()
        ///bottom bar
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        bottomNav = findViewById(R.id.bottomNavigationView)

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.mihome -> {
                    changeFragment(QuestionFragment(),"")
                    toolbar.setTitle("Home");
                }
                R.id.mimessage -> {
                    changeFragment(MessageFragment(),"")
                    toolbar.setTitle("Message");
                }
                R.id.messenger -> {
                    changeFragment(MainActivityChat(),"")
                    toolbar.setTitle("Messenger");
                }
                R.id.miprofil -> {
                    val gson = Gson()
                    val  us =  mSharedPref.getString(myuser, "")

                    nowuser = gson.fromJson(us,User::class.java)
                    print(nowuser)
                    val skillsFragment = ProfileFragment.newInstance(

                     nowuser.nom,
                    nowuser.phone,
                    nowuser.email)

                    changeFragment(skillsFragment, "")
                    toolbar.setTitle("Profil");

                }
                R.id.miservice ->{
                    val gson = Gson()
                    val  us =  mSharedPref.getString(myuser, "")

                    nowuser = gson.fromJson(us,User::class.java)
                    print(nowuser)
                    val skillsFragmentt = MenuFragment.newInstance(

                        nowuser.nom)
                    changeFragment(skillsFragmentt,"")
                    toolbar.setTitle("Menu");
                }
            }
            true
        }



    }

    private fun changeFragment(fragment: Fragment, name: String) {

        if (name.isEmpty())
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        else
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("").commit()

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.logoutMenu ->{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("logout")
                builder.setMessage("Are you sure you want to logout?")
                builder.setPositiveButton("Yes"){ dialogInterface, which ->
                    getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit().clear().apply()
                    val mainIntent = Intent(this, MainActivity::class.java)


                    startActivity(mainIntent)
                    finish()
                }
                builder.setNegativeButton("No"){dialogInterface, which ->
                    dialogInterface.dismiss()
                }
                builder.create().show()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}