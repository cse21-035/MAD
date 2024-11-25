package com.example.quickutility

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class Help : AppCompatActivity() {

    private lateinit var btnClose : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        btnClose = findViewById(R.id.btnClose)

        btnClose.setOnClickListener {
            Toast.makeText(this, "Help closed...", Toast.LENGTH_SHORT).show();
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}