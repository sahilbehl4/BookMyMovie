package com.example.bookmymovie.ui.EditProfile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.bookmymovie.R
import com.example.bookmymovie.databinding.FragmentUpdatePasswordBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UpdatePasswordFragment : Fragment() {

    private var _binding: FragmentUpdatePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdatePasswordBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_updatePasswordFragment_to_navigation_notifications)
        }

        binding.updatePassword.setOnClickListener {
            val currentPassword = binding.password.text.toString()
            val newPassword = binding.newPassword.text.toString()
            val confirmNewPassword = binding.confirmNewPassword.text.toString()


            Firebase.auth.signInWithEmailAndPassword(Firebase.auth.currentUser!!.email!!, currentPassword).addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    if (newPassword != confirmNewPassword) {
                        Toast.makeText(requireActivity(), "New password does not match" , Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }

                    Firebase.auth.currentUser!!.updatePassword(newPassword).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            findNavController().navigate(R.id.action_updatePasswordFragment_to_navigation_notifications)
                            Toast.makeText(
                                requireActivity(),
                                "Password has been updated",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                requireActivity(),
                                "Failed to update password",
                                Toast.LENGTH_SHORT
                            )
                        }
                    }
                }
                else {
                    Toast.makeText(requireActivity(), "Invalid current password" , Toast.LENGTH_SHORT).show()
                    return@addOnCompleteListener
                }
            }
        }

        return root
    }
}