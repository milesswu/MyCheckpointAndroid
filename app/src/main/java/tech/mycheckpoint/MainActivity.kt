package tech.mycheckpoint

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val RC_SIGN_IN: Int = 0
    lateinit var mGoogleSignInClient: GoogleSignInClient;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signInButton: SignInButton = sign_in_button

        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken("263642754920-7er4otbpkfdraudr95s4e77bi6rll7vg.apps.googleusercontent.com")
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        signInButton.setOnClickListener { view: View -> signIn() }

    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode === RC_SIGN_IN) { // The Task returned from this call is always completed, no need to attach a listener.
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
//            val navIntent: Intent = Intent(this, MyEventsActivity::class.java)
//            startActivity(navIntent)
            updateUI(account)
        } catch (e: ApiException) { // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.i("Google Sign In Error", "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }

    override fun onStart() {
        super.onStart()

        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            val navIntent: Intent = Intent(this, MyEventsActivity::class.java)
            startActivity(navIntent)
        } else {
            Toast.makeText(this, "Failed to sign in. Try again.", Toast.LENGTH_LONG).show()
        }
    }
}
