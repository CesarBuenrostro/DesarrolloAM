package com.example.proyectodam.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.proyectodam.MainActivity
import com.example.proyectodam.R
import com.example.proyectodam.data.api.RetrofitClient
import com.example.proyectodam.data.api.SessionManager
import com.example.proyectodam.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
private lateinit var sessionManager: SessionManager


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())


        (activity as AppCompatActivity).supportActionBar?.hide()



        binding.btnLogin.setOnClickListener {
            val correo = binding.inputEmail.text.toString().trim()
            val password = binding.inputPassword.text.toString().trim()

            if (correo.isEmpty() || password.isEmpty()) {
                Snackbar.make(binding.root, "Completa todos los campos", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val loginRequest = LoginRequest(correo, password)

            RetrofitClient.instance.loginUser(loginRequest)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                        val body = response.body()
                        if (response.isSuccessful && body != null && body.success) {
                            val token = body.token
                            val nombre = body.user?.nombre ?: "Sin Nombre"
                            val correo = body.user?.correo ?: "Sin correo"

                            Log.d("SessionDataLog", "Nombre: ${nombre}, Email: ${correo}")

                            sessionManager.saveAuthToken(token)
                            sessionManager.saveUserInfo(nombre, correo)

                            Log.d("SessionData", "Nombre: ${sessionManager.fetchUserName()}, Email: ${sessionManager.fetchUserEmail()}")

                            Snackbar.make(binding.root, response.body()?.message ?:"Operación realizada", Snackbar.LENGTH_LONG).show()

                            val intent = Intent(requireContext(), MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("startDestination", "nav_shop")
                            startActivity(intent)
                        } else {
                            val errorMsg = body?.message ?: "Error en las credenciales"
                            Snackbar.make(binding.root, errorMsg, Snackbar.LENGTH_LONG).show()
                        }

                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        // Mostrar error de red
                        Snackbar.make(binding.root, "Error de Conexión", Snackbar.LENGTH_LONG).show()

                    }
                })
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}
