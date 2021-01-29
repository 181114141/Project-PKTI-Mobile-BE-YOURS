package com.example.beyoursapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    private companion object{
        private const val TAG = "ProfileActivity"
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val db = FirebaseFirestore.getInstance()
        auth = Firebase.auth

        val txtViewNama = findViewById<TextView>(R.id.txtViewNama)
        val txtViewID = findViewById<TextView>(R.id.txtViewId)
        val txtInputNamaID = findViewById<EditText>(R.id.txtInputNamaID)
        val txtInputTelepon = findViewById<EditText>(R.id.txtInputTelepon)
        val txtInputAlamat = findViewById<EditText>(R.id.txtInputAlamat)

        val currentUser = auth.currentUser

        val docRef = db.collection("users").document(currentUser?.uid!!)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")

                    txtViewNama.text = document.getString("displayName")
                    txtViewID.text = document.getString("idName")
                    txtInputNamaID.hint = document.getString("idName")
                    txtInputTelepon.hint = document.getString("phone")
                    txtInputAlamat.hint = document.getString("address")

                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        btnSimpan.setOnClickListener {
            if (txtInputNamaID.text.isBlank() || txtInputTelepon.text.isBlank() || txtInputAlamat.text.isBlank()) {
                Toast.makeText(this, "Mohon data profile dilengkapi", Toast.LENGTH_SHORT).show()
            }
            else {
                db.collection("users").document(currentUser.uid)
                    .update("idName", txtInputNamaID.text.toString())
                db.collection("users").document(currentUser.uid)
                    .update("phone", txtInputTelepon.text.toString())
                db.collection("users").document(currentUser.uid)
                    .update("address", txtInputAlamat.text.toString())
                Toast.makeText(this, "Data profile berhasil diubah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}