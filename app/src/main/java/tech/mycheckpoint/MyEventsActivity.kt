package tech.mycheckpoint

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
//import com.google.firebase.auth.FirebaseAuth
import com.google.gson.GsonBuilder
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import kotlinx.android.synthetic.main.nav_header_my_events.view.*
import tech.mycheckpoint.databinding.ActivityMyEventsBinding
import tech.mycheckpoint.models.Calendars.Calendar
import tech.mycheckpoint.models.Calendars.Calendars
import java.io.IOException


class MyEventsActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var defaultBinding: ActivityMyEventsBinding
    private lateinit var id: String
//    lateinit var auth: FirebaseAuth

    lateinit var mGoogleSignInClient: GoogleSignInClient;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        defaultBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_events)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_day, R.id.nav_three, R.id.nav_week,
                R.id.nav_month, R.id.nav_settings, R.id.nav_logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val headerLayout =  navView.getHeaderView(0)

        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            val personName = account.displayName
            val personEmail = account.email
            val personId = account.id
            val photoURL = account.photoUrl

            headerLayout.findViewById<TextView>(R.id.user_name).text = personName
            headerLayout.user_email.text = personEmail
            Glide.with(this).load(photoURL).into(headerLayout.findViewById(R.id.user_pic))
            id = personId?: "-1"
        }

        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken("263642754920-7er4otbpkfdraudr95s4e77bi6rll7vg.apps.googleusercontent.com")
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            signOut()
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
        }
//        auth = FirebaseAuth.getInstance()
//        Calendars.makeRequest()
        fetchData()
    }

    private fun fetchData() {
        println("Attempting to fetch data")
        var text: String
        val request = Request.Builder().url(Calendars.URL).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(response: Response?) {
                val body = response?.body()?.string()
                println(body)
                val gson = GsonBuilder().create()
                val results = gson.fromJson(body, Calendar::class.java)
                println("Calendars: ${results}")
            }

            override fun onFailure(request: Request?, e: IOException?) {
                println("Failed to execute request: ${e}")
            }
        })
    }

    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this, object : OnCompleteListener<Void?> {
                override fun onComplete(task: Task<Void?>) {
                    println("Logged out")

                }
            })
        val navIntent: Intent = Intent(this, MainActivity::class.java)
        startActivity(navIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.my_events, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
