package com.example.proyectodam.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.proyectodam.databinding.FragmentRegisterBinding
import com.example.proyectodam.R
import com.example.proyectodam.data.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.btnRegister.setOnClickListener {
            val nombre = binding.inputNombre.text.toString().trim()
            val apellido = binding.inputApellido.text.toString().trim()
            val correo = binding.inputCorreo.text.toString().trim()
            val edad = binding.inputEdad.text.toString().toIntOrNull() ?: 0
            val password = binding.inputPassword.text.toString().trim()

            // Validación de campos vacíos
            if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validación de longitud mínima de contraseña
            if (password.length < 8) {
                Toast.makeText(requireContext(), "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(nombre, apellido, correo, edad, password)

            RetrofitClient.instance.registerUser(user).enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        // Registro exitoso: navega o muestra mensaje
                        findNavController().navigate(R.id.nav_apriv)
                    } else {
                        // Error del backend
                        val errorMsg = response.body()?.message ?: "Error al registrar"
                        // Mostrar un Toast con errorMsg
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    // Error de red
                    // Mostrar un Toast con t.message
                }
            })
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
