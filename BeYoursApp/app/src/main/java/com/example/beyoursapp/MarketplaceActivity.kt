package com.example.beyoursapp

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputFilter
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_marketplace.*
import kotlinx.android.synthetic.main.alert_dialog_edit_toy_info.*
import kotlinx.android.synthetic.main.user_list_item_view.*

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class MarketplaceActivity : AppCompatActivity() {
    private companion object{
        private const val TAG = "MarketplaceActivity"
    }

    private lateinit var auth: FirebaseAuth
    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace)

        auth = Firebase.auth

        val query = db.collection("users")
        val options = FirestoreRecyclerOptions.Builder<User>().setQuery(query, User::class.java)
            .setLifecycleOwner(this).build()
        val adapter = object : FirestoreRecyclerAdapter<User, UserViewHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
                var inflate = LayoutInflater.from(this@MarketplaceActivity).inflate(R.layout.user_list_item_view, parent, false)
                return UserViewHolder(inflate)
            }

            override fun onBindViewHolder(holder: UserViewHolder, position: Int, model: User) {
                val email: TextView = holder.itemView.findViewById(R.id.emailUser)
                val namauser: TextView = holder.itemView.findViewById(R.id.namaUser)
                val deskripsi: TextView = holder.itemView.findViewById(R.id.deskripsiMainan)
                val namamainan: TextView = holder.itemView.findViewById(R.id.namaMainan)
                val gambar: ImageView = holder.itemView.findViewById(R.id.gambarMainan)
                email.text = model.email
                namauser.text = model.displayName
                deskripsi.text = model.description
                namamainan.text = model.toyName
                Picasso.get()
                    .load(model.imageUrl)
                    .into(gambar);
            }
        }

        myRecyclerView.adapter = adapter
        myRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.miLogout) {
            Log.i(MarketplaceActivity.TAG, "Logout")
            // Logout user
            auth.signOut()
            val intentLogout = Intent(this, LoginActivity::class.java)
            intentLogout.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intentLogout)
        }
        else if (item.itemId == R.id.miTambah) {
            Log.i(MarketplaceActivity.TAG, "Muncul alert dialog untuk edit info mainan")
            showAlertDialogEditInfo()
        }
        else if (item.itemId == R.id.miUpload) {
            var intentupload = Intent(this, UploadimageActivity::class.java)
            startActivity(intentupload)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialogEditInfo() {

        val alertDialogInflater = layoutInflater.inflate(R.layout.alert_dialog_edit_toy_info, null)
        val dialog = AlertDialog.Builder(this)

        val masukNamaMainan = alertDialogInflater.findViewById<EditText>(R.id.inputNamaMainan)
        val masukDeskripsiMainan = alertDialogInflater.findViewById<EditText>(R.id.inputDeskripsi)
        val tombolOK = alertDialogInflater.findViewById<Button>(R.id.btnOK)

        tombolOK.setOnClickListener {
            Log.i(TAG, "Tombol OK diklik")

            val namaMainan = masukNamaMainan.text.toString()
            val deskripsiMainan = masukDeskripsiMainan.text.toString()
            if (namaMainan.isBlank() || deskripsiMainan.isBlank()) {
                Toast.makeText(this, "Mohon masukkan nama dan deskripsi mainan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val currentUser = auth.currentUser
            if (currentUser == null) {
                Toast.makeText(this, "Tidak bisa diupdate, user tidak masuk ke app", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Update firestore dengan data baru
            db.collection("users").document(currentUser.uid)
                .update("toyName", namaMainan)
            db.collection("users").document(currentUser.uid)
                .update("description", deskripsiMainan)
        }

        dialog.setView(alertDialogInflater)
        dialog.setTitle("Edit Data Mainan Barter")
        var dialogEditMainan = dialog.create()
        dialogEditMainan.show()
    }
}