package com.unifor.sistemadeembarque

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.ValueEventListener
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class LoginActivity : AppCompatActivity() {
    private lateinit var goToRegister: TextView
    private lateinit var loginEmail: EditText
    private lateinit var loginPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        initView()
        loginButton.setOnClickListener {
            when {
                TextUtils.isEmpty(loginEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Por favor entre com um email valido",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(loginPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Por favor entre com uma senha valida",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email: String = loginEmail.text.toString().trim { it <= ' ' }
                    val password: String = loginPassword.text.toString().trim { it <= ' ' }

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task->
                            if(task.isSuccessful) {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Login Completo!",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val uid = replacer(FirebaseAuth.getInstance().currentUser?.uid.toString())

                                database = FirebaseDatabase.getInstance().getReference("Users")
                                database.child(uid).get().addOnSuccessListener {
                                    if (it.exists()) {
                                        val type = it.child("type").value
                                        var intent = Intent(this@LoginActivity, PassengerActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                                        if (type == "Cobrador") {
                                            intent = Intent(this@LoginActivity, CashierActivity::class.java)
                                        }

                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(
                                            this@LoginActivity,
                                            "Usuario nao existente",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }.addOnFailureListener {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Failed Getting User",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
//                                val user = database.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object: ValueEventListener {
//                                    override fun onCancelled(data: DatabaseError) {}
//                                    override fun onDataChange(data: DataSnapshot) {
//                                        var type = data.child("type").getValue(String::class.java)
//                                    }
//                                })
//                                println(user)

                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }
        goToRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun replacer(t: String): String {
        return t.replace(".", "")
            .replace("#", "")
            .replace("\$", "")
            .replace("[", "")
            .replace("]", "")
    }

    private fun initView() {
        goToRegister = findViewById<TextView>(R.id.goToRegisterButton)
        loginEmail = findViewById<EditText>(R.id.loginEmailET)
        loginPassword = findViewById<EditText>(R.id.loginPasswordET)
        loginButton = findViewById<Button>(R.id.loginButton)
    }
}
