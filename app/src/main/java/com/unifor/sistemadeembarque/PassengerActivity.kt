package com.unifor.sistemadeembarque

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

class PassengerActivity : AppCompatActivity() {
    private lateinit var passengerEmail: TextView
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passenger)
        initView()
        initFirebaseData()
    }


    private fun initView() {
        passengerEmail = findViewById<TextView>(R.id.passengerEmailTV)
    }

    private fun initFirebaseData() {
        val userId = intent.getStringExtra("user_id")
        val email = intent.getStringExtra("email_id")

        database = FirebaseDatabase.getInstance().getReference("Users")
        val uid = replacer(FirebaseAuth.getInstance().currentUser?.uid.toString())
        database.child(uid).get().addOnSuccessListener {
            if (it.exists()) {
                passengerEmail.text = "Email: " + it.child("email").value.toString()
                Toast.makeText(
                    this@PassengerActivity,
                    "Usuario logado",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this@PassengerActivity,
                    "Usuario nao existente",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.addOnFailureListener {
            Toast.makeText(
                this@PassengerActivity,
                "Failed Getting User",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun replacer(t: String): String {
        return t.replace(".", "")
            .replace("#", "")
            .replace("\$", "")
            .replace("[", "")
            .replace("]", "")
    }
}