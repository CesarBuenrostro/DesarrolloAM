package com.example.proyectodam.ui.PrivacyNotice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.proyectodam.R
import com.example.proyectodam.databinding.FragmentAprivBinding
import com.example.proyectodam.databinding.FragmentLoginBinding


class AprivFragment : Fragment() {

    private var _binding: FragmentAprivBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAprivBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.show()

        binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.nav_login)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

}