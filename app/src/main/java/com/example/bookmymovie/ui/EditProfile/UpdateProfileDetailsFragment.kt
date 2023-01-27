package com.example.bookmymovie.ui.EditProfile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.bookmymovie.R
import com.example.bookmymovie.databinding.FragmentUpdateProfileDetailsBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase


class UpdateProfileDetailsFragment : Fragment() {

    private var _binding: FragmentUpdateProfileDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUpdateProfileDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.email.setText(Firebase.auth.currentUser?.email)
        binding.name.setText(Firebase.auth.currentUser?.displayName)

        binding.updateProfile.setOnClickListener {
            val update = userProfileChangeRequest {
                displayName = binding.name.text.toString()
            }

            Firebase.auth.currentUser?.updateProfile(update)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireActivity(), "Updated Profile" , Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_updateProfileDetailsFragment_to_navigation_notifications)
                } else {
                    Toast.makeText(requireActivity(), "Update Profile Failed" , Toast.LENGTH_SHORT).show()
                }
            }

            return@setOnClickListener
        }

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_updateProfileDetailsFragment_to_navigation_notifications)
        }

        return root
    }
}









