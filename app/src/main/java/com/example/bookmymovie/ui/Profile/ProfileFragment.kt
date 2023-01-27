package com.example.bookmymovie.ui.Profile

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bookmymovie.R
import com.example.bookmymovie.databinding.FragmentProfileBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val root: View = binding.root

        if (Firebase.auth.currentUser == null) {
            logOut()
            return root;
        }

        binding.nameTitleView.text = "Hi! " + Firebase.auth.currentUser!!.displayName

        binding.logout.setOnClickListener {
            logOut()
        }

        binding.updateLocation.setOnClickListener {
            updateLocation()
        }

        binding.updatePassword.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_notifications_to_updatePasswordFragment)
        }


        binding.editProfile.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_notifications_to_updateProfileDetailsFragment)
        }

        binding.updateCC.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_notifications_to_updateCreditCardFragment)
        }

        return root
    }

    private fun updateLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->
                        binding.currentLocation.text = "London, UK"
                    }

            } else {
                // settings open
            }
        } else {
            requestPermission()
        }

    }

    private fun checkPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true
        } else {
            return false
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_CODE = 100
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_REQUEST_ACCESS_CODE)
        updateLocation()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_ACCESS_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] ==PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireActivity(), "Granted", Toast.LENGTH_SHORT).show()
                updateLocation()
            } else {
                Toast.makeText(requireActivity(), "Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        return true
    }

    private fun logOut() {
        Firebase.auth.signOut()

        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}