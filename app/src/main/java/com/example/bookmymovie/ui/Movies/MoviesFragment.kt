package com.example.bookmymovie.ui.Movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bookmymovie.databinding.FragmentHomeBinding
import com.example.bookmymovie.R

class MoviesFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val moviesName = arrayOf("Life of PI", "1917", "UnCharted", "Pirates of Caribbean", "Don't look up", "Jaws")
        val movieImages = arrayOf(R.drawable.life_of_pi, R.drawable.movie1917, R.drawable.uncharted, R.drawable.pirates_of_cariabian, R.drawable.dont_look_up, R.drawable.jaws)

        val moviesAdapter = MoviesAdapter(context = requireActivity().baseContext,
            moviesName = moviesName,
            moviesImageName = movieImages
        )
        binding.moviesGrid.adapter = moviesAdapter

        binding.moviesGrid.setOnItemClickListener { adapterView, view, i, l ->

            val bundle = Bundle()
            bundle.putString("movieName", moviesName[i])
            parentFragmentManager.setFragmentResult("movieInfo", bundle)

            findNavController().navigate(R.id.action_navigation_home_to_movieBookFragment)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}