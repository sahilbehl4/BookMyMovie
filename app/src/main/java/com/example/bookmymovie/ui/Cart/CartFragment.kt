package com.example.bookmymovie.ui.Cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.bookmymovie.R
import com.example.bookmymovie.databinding.FragmentCartBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var userId: String

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var cartValues:  ArrayList<CartModel> = ArrayList()
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCartBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        database = Firebase.database
        reference = database.reference
        userId = Firebase.auth.currentUser!!.uid

        binding.emptyCart.isVisible = true
        binding.reset.isVisible = false
        binding.checkout.isVisible = false

        val root: View = binding.root
        cartAdapter = CartAdapter(requireActivity().baseContext, cartValues)
        binding.cartList.adapter = cartAdapter

        loadCart()

        binding.reset.setOnClickListener {
            reference.child(userId).child("cart").removeValue()
            cartValues.clear()
            cartAdapter.notifyDataSetChanged()
            binding.emptyCart.isVisible = true
            binding.reset.isVisible = false
            binding.checkout.isVisible = false
        }

        binding.checkout.setOnClickListener {
            reference.child(userId).child("creditcard").get().addOnSuccessListener { snapshot ->
                val ccNumber = snapshot.child("ccNumber").getValue()
                val date = snapshot.child("date").getValue()
                val cvv = snapshot.child("cvv").getValue()

                if (!(ccNumber == null || date == null || cvv == null)) {
                    reference.child(userId).child("cart").removeValue()
                    cartValues.clear()
                    cartAdapter.notifyDataSetChanged()
                    binding.emptyCart.isVisible = true
                    binding.reset.isVisible = false
                    binding.checkout.isVisible = false

                    Toast.makeText(requireActivity(), "Payment complete", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireActivity(), "No credit card information available", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener{
                Toast.makeText(requireActivity(), "No credit card information available", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    private fun loadCart() {
        reference.child(userId).child("cart").get().addOnSuccessListener { snapshot ->
            snapshot.children.forEach {  childSnapshot ->
                val movieName = childSnapshot.child("movieName").getValue()
                val totalPrice = childSnapshot.child("totalPrice").getValue()
                val numberOfTickets = childSnapshot.child("numberOfTickets").getValue()

                val model = CartModel(movieName.toString(), totalPrice.toString(), numberOfTickets.toString().toInt())

                cartValues.add(model)
            }

            if (!cartValues.isEmpty()) {
                binding.emptyCart.isVisible = false
                binding.reset.isVisible = true
                binding.checkout.isVisible = true
                cartAdapter.notifyDataSetChanged()
            }

        }.addOnFailureListener{

        }
    }
}