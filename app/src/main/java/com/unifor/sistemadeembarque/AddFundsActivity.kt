package com.unifor.sistemadeembarque

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class AddFundsActivity : AppCompatActivity() {
    private lateinit var currentBalance: TextView
    private lateinit var database: FirebaseDatabase
    private lateinit var addFundsValueButton: Button
    private lateinit var newBalanceToSumET: EditText
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_funds)

        initView()
        initFirebaseData()

        addFundsValueButton.setOnClickListener {
            if (newBalanceToSumET.text.isEmpty()) {
                Toast.makeText(
                    this@AddFundsActivity,
                    "Por favor insira um valor valido",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val ref = database.getReference("Users").child(uid)
                ref.get().addOnSuccessListener {
                    currentBalance.text = "R$ " + it.child("balance").value.toString()
                    val finalValue = it.child("balance").value.toString().toDouble() + newBalanceToSumET.text.toString().toDouble()

                    val u: HashMap<String, Any> = HashMap()
                    u["balance"] = finalValue
                    ref.updateChildren(u)
                    currentBalance.text = finalValue.toString()
                }
            }
        }
    }

    private fun initFirebaseData() {
        database = FirebaseDatabase.getInstance()
        uid = replacer(FirebaseAuth.getInstance().currentUser?.uid.toString())
    }

    private fun initView() {
        currentBalance = findViewById<TextView>(R.id.currentBalanceTV)
        addFundsValueButton = findViewById<Button>(R.id.addFundsValueButton)
        newBalanceToSumET = findViewById<EditText>(R.id.newBalanceToSumET)
    }

    private fun replacer(t: String): String {
        return t.replace(".", "")
            .replace("#", "")
            .replace("\$", "")
            .replace("[", "")
            .replace("]", "")
    }
}