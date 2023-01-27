package com.example.bookmymovie.ui.CreditCard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.bookmymovie.R
import com.example.bookmymovie.databinding.FragmentUpdateCreditCardBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class UpdateCreditCardFragment : Fragment() {
    private var _binding: FragmentUpdateCreditCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateCreditCardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val database = Firebase.database
        val ref = database.reference

        val userId = Firebase.auth.currentUser!!.uid

        ref.child(userId).child("creditcard").get().addOnSuccessListener { snapshot ->
            val ccNumber = snapshot.child("ccNumber").getValue()
            val date = snapshot.child("date").getValue()
            val cvv = snapshot.child("cvv").getValue()

            if (!(ccNumber == null || date == null || cvv == null)) {
                binding.CardNumber.setText(ccNumber.toString())
                binding.date.setText(date.toString())
                binding.CVV.setText(cvv.toString())
            }
        }.addOnFailureListener{

        }

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_updateCreditCardFragment_to_navigation_notifications)
        }

        binding.updatePassword.setOnClickListener {
            val cardNumber = binding.CardNumber.text.toString()
            val date = binding.date.text.toString()
            val cvv = binding.CVV.text.toString()

            if (cardNumber.isNullOrEmpty() || date.isNullOrEmpty() || cvv.isNullOrEmpty()) {
                Toast.makeText(requireActivity(), "Enter valid card details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val model = CreditCardModel(cardNumber, date, cvv)

            ref.child(userId).child("creditcard").setValue(model).addOnCompleteListener {  task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireActivity(), "Updated credit card details", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_updateCreditCardFragment_to_navigation_notifications)
                } else {
                    Toast.makeText(requireActivity(), "Failed to update credit card details", Toast.LENGTH_LONG).show()
                }
            }
        }

        return root
    }
}