package com.unifor.sistemadeembarque

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import org.w3c.dom.Text

class PassengerActivity : AppCompatActivity() {
    private lateinit var passengerEmail: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passenger)
        initView()

        val userId = intent.getStringExtra("user_id")
        val email = intent.getStringExtra("email_id")

        passengerEmail.text = "Email :: $email"
    }


    private fun initView() {
        passengerEmail = findViewById<TextView>(R.id.passengerEmailTV)
    }
}