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

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailField = findViewById<TextInputEditText>(R.id.emailInput)
        val passwordField = findViewById<TextInputEditText>(R.id.passwordInput)
        val loginBtn = findViewById<Button>(R.id.loginButton)
        val btnRegister = findViewById<TextView>(R.id.btnRegister)

        loginBtn.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val loginRequest = User(
                userID = null,
                userEmail = email,
                userPassword = password,
                userFirstName = null,
                userLastName = null
            )

            performLogin(loginRequest)
        }

        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun performLogin(userRequest: User) {

        RetrofitClient.instance.login(userRequest)
            .enqueue(object : Callback<User> {

                override fun onResponse(call: Call<User>, response: Response<User>) {

                    if (response.isSuccessful) {

                        val loggedInUser = response.body()

                        if (loggedInUser != null) {

                            saveUserSession(loggedInUser.userID)

                            Toast.makeText(
                                this@LoginActivity,
                                "Welcome, ${loggedInUser.userFirstName}!",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent = Intent(this@LoginActivity, DashboardActivity::class.java)

                            intent.putExtra("USER_FIRSTNAME", loggedInUser.userFirstName)
                            intent.putExtra("USER_LASTNAME", loggedInUser.userLastName)
                            intent.putExtra("USER_EMAIL", loggedInUser.userEmail)

                            startActivity(intent)
                            finish()
                        }

                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Invalid Credentials",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Connection Error: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun saveUserSession(userId: Long?) {

        val sharedPref = getSharedPreferences("ExpenseTrackerPrefs", MODE_PRIVATE)

        with(sharedPref.edit()) {
            putLong("KEY_USER_ID", userId ?: -1L)
            apply()
        }
    }
}