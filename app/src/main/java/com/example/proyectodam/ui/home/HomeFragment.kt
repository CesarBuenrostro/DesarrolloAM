package com.example.proyectodam.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.proyectodam.R
import com.example.proyectodam.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.hide()


        // Navegación de botones
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.nav_login)
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.nav_register)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
