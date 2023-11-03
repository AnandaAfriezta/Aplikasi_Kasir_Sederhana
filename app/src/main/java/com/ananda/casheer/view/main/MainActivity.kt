package com.ananda.casheer.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.ananda.casheer.RegisterActivity
import com.ananda.casheer.database.CafeDatabase
import com.ananda.casheer.database.User
import com.ananda.casheer.view.list.ListTransaksiActivity
import com.example.casheer.R

class MainActivity : AppCompatActivity() {
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var db: CafeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        init()

        loginButton.setOnClickListener {
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val userList = db.cafeDao().login(email, password)
                if (userList.isNotEmpty()) {
                    val user = userList[0]
                    val name = user.nama
                    val role = user.role
                    val id_user = user.id_user

                    val moveIntent = if (role == "Manager") {
                        Intent(this@MainActivity, ListTransaksiActivity::class.java)
                    } else {
                        Intent(this@MainActivity, MainActivity2::class.java)
                    }

                    moveIntent.putExtra("name", name)
                    moveIntent.putExtra("role", role)
                    moveIntent.putExtra("id_user", id_user)
                    startActivity(moveIntent)
                    finish()
                } else {
                    showMessage("User not found")
                }
            } else {
                showMessage("Insert Email & Password")
            }
        }

        registerButton.setOnClickListener {
            val moveIntent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(moveIntent)
        }
    }

    private fun init() {
        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)
        loginButton = findViewById(R.id.buttonLogin)
        registerButton = findViewById(R.id.buttonRegister)

        db = CafeDatabase.getInstance(applicationContext)
    }

    private fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}
