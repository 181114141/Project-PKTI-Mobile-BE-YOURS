package com.example.beyoursapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class DonateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)
    }

    fun openWeb(view: View) {
        val googleSearch = Uri.parse("https://www.google.com/search?q=daftar panti asuhan medan")
        val googleIntent = Intent(Intent.ACTION_VIEW, googleSearch)
        if(googleIntent.resolveActivity(packageManager)!= null) {
            startActivity(googleIntent)
        }
        else{
            Log.e("Intent Implisit", "Intent Gagal")
        }
    }
}