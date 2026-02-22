package com.example.expensetracker.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.example.expensetracker.R
import com.example.expensetracker.api.RetrofitClient
import com.example.expensetracker.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvBackLogin = findViewById<TextView>(R.id.backLogin)

        btnRegister.setOnClickListener {

            val etFirstName = findViewById<TextInputEditText>(R.id.etFirstName)
            val etLastName = findViewById<TextInputEditText>(R.id.etLastName)
            val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
            val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
            val etConfirm = findViewById<TextInputEditText>(R.id.etConfirmPassword)

            val password = etPassword.text.toString()
            val confirm = etConfirm.text.toString()

            // Validation
            if (password != confirm) {
                etConfirm.error = "Passwords do not match!"
                return@setOnClickListener
            }

            if ((etEmail.text?.toString() ?: "").isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(
                userID = null,
                userFirstName = etFirstName.text.toString(),
                userLastName = etLastName.text.toString(),
                userEmail = etEmail.text.toString(),
                userPassword = password
            )

            registerUser(user)
        }

        tvBackLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun registerUser(user: User) {

        RetrofitClient.instance.register(user)
            .enqueue(object : Callback<User> {

                override fun onResponse(call: Call<User>, response: Response<User>) {

                    if (response.isSuccessful) {

                        Toast.makeText(
                            this@RegisterActivity,
                            "Account Created!",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {

                        Toast.makeText(
                            this@RegisterActivity,
                            "Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {

                    Toast.makeText(
                        this@RegisterActivity,
                        "Network Failure",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}