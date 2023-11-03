package com.ananda.casheer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.ananda.casheer.view.main.MainActivity
import com.example.casheer.R

class home : AppCompatActivity() {
    private lateinit var homeBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()

        init()

        homeBtn.setOnClickListener {
            val intent = Intent(this@home,MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun init() {
        homeBtn=findViewById(R.id.btn_home)
    }
}