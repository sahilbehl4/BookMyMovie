package com.example.bookmymovie.ui.ForgotPassword

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.bookmymovie.R
import com.example.bookmymovie.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            binding.EmailAddress.setText("")
            finish()
        }

        binding.SendEmailButton.setOnClickListener {
            val email = binding.EmailAddress.text.toString()

            if (email.isNullOrBlank()) {
                Toast.makeText(this, "Enter valid email", Toast.LENGTH_SHORT).show()
            } else {
                Firebase.auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        Toast.makeText(this, "Reset email sent", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Failed to send reset email", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}