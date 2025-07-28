package com.example.proyectodam.ui.shoppingcart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.proyectodam.R
import com.example.proyectodam.databinding.FragmentCarritoBinding
import com.example.proyectodam.databinding.FragmentRegisterBinding

class CarritoFragment : Fragment() {

    private var _binding: FragmentCarritoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCarritoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}