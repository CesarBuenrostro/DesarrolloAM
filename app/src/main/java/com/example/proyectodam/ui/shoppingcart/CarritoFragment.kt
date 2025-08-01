package com.example.proyectodam.ui.shoppingcart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectodam.data.api.RetrofitClient
import com.example.proyectodam.data.api.SessionManager
import com.example.proyectodam.databinding.FragmentCarritoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
private lateinit var sessionManager: SessionManager

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
        val sessionManager = SessionManager(requireContext())

        val token = sessionManager.fetchAuthToken()
        val tokenConBearer = "Bearer $token"


        // Llamada a la API usando Retrofit
        RetrofitClient.instance.obtenerCarrito(tokenConBearer).enqueue(object : Callback<ApiResponseCarrito> {
            override fun onResponse(call: Call<ApiResponseCarrito>, response: Response<ApiResponseCarrito>) {
                Log.d("CarritoFragment", "Respuesta carrito: ${response.body()}")

                if (response.isSuccessful && response.body()?.success == true) {

                    val carrito = response.body()?.data
                     val items = carrito?.items ?: emptyList()

                    val Total = carrito?.total ?: 0
                    binding.tvTotal.text = "${Total}"

                    if (items.isNotEmpty()) {
                        binding.recyclerCarrito.apply {
                            layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            adapter = CarritoAdapter(items)
                        }
                    } else {
                        Toast.makeText(requireContext(), "dpdsdmfvsevnepasnviñniew", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Error al obtener el carrito", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponseCarrito>, t: Throwable) {
                Toast.makeText(requireContext(), "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}