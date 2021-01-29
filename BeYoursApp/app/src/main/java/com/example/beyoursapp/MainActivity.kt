package com.example.beyoursapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private companion object{
        private const val TAG = "MainActivity"
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.miLogout) {
            Log.i(TAG, "Logout")
            // Logout user
            auth.signOut()
            val intentLogout = Intent(this, LoginActivity::class.java)
            intentLogout.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intentLogout)
        }
        return super.onOptionsItemSelected(item)
    }

    fun openProfile(view: View) {
        var intentProfile = Intent(this, ProfileActivity::class.java)
        startActivity(intentProfile)
    }

    fun openMarket(view: View) {
        var intentMarket = Intent(this, MarketplaceActivity::class.java)
        startActivity(intentMarket)
    }

    fun openSearch(view: View) {}
    fun openDonate(view: View) {
        var intentDonate = Intent(this, DonateActivity::class.java)
        startActivity(intentDonate)
    }

    fun openChat(view: View) {
        var intentChat = Intent(this, ChatActivity::class.java)
        startActivity(intentChat)
    }

    private var doubleBackToExitPressedOnce: Boolean = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Tekan back lagi untuk keluar", Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)

    }
}