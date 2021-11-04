package com.unifor.sistemadeembarque

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CashierActivity : AppCompatActivity() {
    private lateinit var cashierEmail: TextView
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cashier)
        initView()
        initFirebaseData()
    }

    private fun initView() {
        cashierEmail = findViewById<TextView>(R.id.cashierNameTV)
    }

    private fun initFirebaseData() {
        val userId = intent.getStringExtra("user_id")
        val email = intent.getStringExtra("email_id")

        database = FirebaseDatabase.getInstance().getReference("Users")
        val uid = replacer(FirebaseAuth.getInstance().currentUser?.uid.toString())


        database.child(uid).get().addOnSuccessListener {
            if (it.exists()) {
                cashierEmail.text = "Email: " + it.child("email").value.toString()
                val currentTrip = Trip()

                Toast.makeText(
                    this@CashierActivity,
                    "Usuario logado",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this@CashierActivity,
                    "Usuario nao existente",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.addOnFailureListener {
            Toast.makeText(
                this@CashierActivity,
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