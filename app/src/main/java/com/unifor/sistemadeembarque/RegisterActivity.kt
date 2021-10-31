package com.unifor.sistemadeembarque

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerButton: Button
    private lateinit var registerEmail: EditText
    private lateinit var registerPassword: EditText
    private lateinit var backToLoginTV: TextView
    private lateinit var database: DatabaseReference
    private lateinit var registerType: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
        initView()
        registerButton.setOnClickListener {
            when {
                TextUtils.isEmpty(registerEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Por favor entre com um email valido",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(registerPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Por favor entre com uma senha valida",
                        Toast.LENGTH_SHORT
                    ).show()
                } else -> {

                    val email: String = registerEmail.text.toString().trim { it <= ' ' }
                    val password: String = registerPassword.text.toString().trim { it <= ' ' }
                    val type: String = registerType.selectedItem.toString().trim { it <= ' ' }

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->
                                if (task.isSuccessful) {
                                    val firebaseUser:FirebaseUser = task.result!!.user!!

                                    database = FirebaseDatabase.getInstance().getReference("Users")

                                    val user = User(firebaseUser.uid, email, email, 0.0, type)

                                    database.child(replacer(firebaseUser.uid)).setValue(user).addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            registerEmail.text.clear()
                                            registerPassword.text.clear()
                                        } else {
                                            print(task.exception!!.message.toString())
                                        }
                                    }

                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "Cadastro Completo!",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    var intent = Intent(this@RegisterActivity, PassengerActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", firebaseUser.uid)
                                    intent.putExtra("email_id", email)

                                    if (type == "Cobrador") {
                                        // Send new user directly to it's respective Activity ( Cobrador / Passageiro )
                                        intent = Intent(this@RegisterActivity, CashierActivity::class.java)
                                    }

                                    startActivity(intent)
                                    finish()


                                } else {
                                    // Failed creation
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        )
                }
            }
        }

        backToLoginTV.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
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
        registerButton = findViewById<Button>(R.id.registerButton)
        registerEmail = findViewById<EditText>(R.id.registerEmailTV)
        registerPassword = findViewById<EditText>(R.id.registerPassword)
        registerType = findViewById<Spinner>(R.id.registerType)
        backToLoginTV = findViewById<TextView>(R.id.backToLoginTV)
    }
}