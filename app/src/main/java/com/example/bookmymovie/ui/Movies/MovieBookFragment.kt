package com.example.bookmymovie.ui.Movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.bookmymovie.R
import com.example.bookmymovie.databinding.FragmentMovieBookBinding
import com.example.bookmymovie.ui.Cart.CartModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MovieBookFragment : Fragment() {

    private var _binding: FragmentMovieBookBinding? = null

    private val binding get() = _binding!!

    private val maxBookingCount = 9
    private var generalCount = 1
    private var seniorCount = 0
    private var childCount = 0
    private var totalCount : Int = generalCount + seniorCount + childCount

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBookBinding.inflate(inflater, container, false)
        val root: View = binding.root

        parentFragmentManager.setFragmentResultListener("movieInfo",
            this,
            FragmentResultListener { key, result ->
                val movieName = result.getString("movieName")
                println("movieName" + movieName)
                val textView = root.findViewById<TextView>(R.id.movie_name)
                textView.text = movieName
        })

        setCountLabels()
        updateTotals()

        binding.subGeneral.setOnClickListener {
            if (totalCount > 1) {
                generalCount--
            }
            setCountLabels()
            updateTotals()
        }

        binding.addGeneral.setOnClickListener {
            if (totalCount < maxBookingCount) {
                generalCount++
            }
            setCountLabels()
            updateTotals()
        }

        binding.subSenior.setOnClickListener {
            if (totalCount > 1) {
                seniorCount--
            }
            setCountLabels()
            updateTotals()
        }

        binding.addSenior.setOnClickListener {
            if (totalCount < maxBookingCount) {
                seniorCount++
            }
            setCountLabels()
            updateTotals()
        }

        binding.subChild.setOnClickListener {
            if (totalCount > 1) {
                childCount--
            }
            setCountLabels()
            updateTotals()
        }

        binding.addChild.setOnClickListener {
            if (totalCount < maxBookingCount) {
                childCount++
            }
            setCountLabels()
            updateTotals()
        }

        binding.purchase.setOnClickListener {
            addToCart()
        }

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_movieBookFragment_to_navigation_home)
        }

        return root
    }

    private fun addToCart() {
        val movieName = binding.movieName.text.toString()
        val totalCost = binding.viewersTotal.text.toString()
        val model = CartModel(movieName, totalCost, totalCount)

        val database = Firebase.database
        val ref = database.reference

        val cartRef = ref.child(Firebase.auth.currentUser!!.uid).child("cart")
        cartRef.push().setValue(model).addOnCompleteListener {  task ->
            if (task.isSuccessful) {
                Toast.makeText(requireActivity(), "Added to cart", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_movieBookFragment_to_navigation_home)
            } else {
                Toast.makeText(requireActivity(), "Failed to add to cart", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setCountLabels() {
        val generalCountTextView = binding.generalCount
        val childCountTextView = binding.childCount
        val seniorCountTextView = binding.seniorCount

        generalCountTextView.text = generalCount.toString()
        childCountTextView.text = childCount.toString()
        seniorCountTextView.text = seniorCount.toString()
        totalCount = generalCount + seniorCount + childCount
    }

    private fun updateTotals() {
        val viewersTextView = binding.viewers
        val viewersTotalTextView = binding.viewersTotal

        viewersTextView.text = ""
        if(generalCount == 1) {
            viewersTextView.text = "1 General\n"
        } else if(generalCount > 1){
            viewersTextView.text = generalCount.toString() + " Generals\n"
        }

        if(seniorCount == 1) {
            viewersTextView.text = viewersTextView.text.toString() + "1 Senior\n"
        } else if(seniorCount > 1){
            viewersTextView.text = viewersTextView.text.toString() + seniorCount.toString() + " Seniors\n"
        }

        if(childCount == 1) {
            viewersTextView.text = viewersTextView.text.toString() + "1 Child\n"
        } else if(childCount > 1){
            viewersTextView.text = viewersTextView.text.toString() + childCount.toString() + " Children\n"
        }

        val totalMoney = generalCount * 8 + childCount * 5.5 + seniorCount * 5.5
        viewersTotalTextView.text = "£" + totalMoney.toString()
    }

}