package com.example.proyectodam.ui.shop

import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectodam.data.api.RetrofitClient
import com.example.proyectodam.data.api.SessionManager
import com.example.proyectodam.databinding.FragmentShopBinding
import com.example.proyectodam.ui.shoppingcart.ApiResponseCarrito
import com.example.proyectodam.ui.shoppingcart.addCartRequest

class ShopFragment : Fragment() {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.show()

        sessionManager = SessionManager(requireContext())

        val onAddToCart: (Producto) -> Unit = { producto ->
            val token = sessionManager.fetchAuthToken()
            val tokenConBearer = "Bearer $token"
            val request = addCartRequest(productoId = producto._id, cantidad = 1)

            RetrofitClient.instance.addProductCarrito(tokenConBearer, request)
                .enqueue(object : Callback<ApiResponseCarrito> {
                    override fun onResponse(call: Call<ApiResponseCarrito>, response: Response<ApiResponseCarrito>) {
                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "Error al agregar producto", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ApiResponseCarrito>, t: Throwable) {
                        Toast.makeText(requireContext(), "Fallo en la conexión", Toast.LENGTH_SHORT).show()
                    }
                })
        }

        // Cargar productos filtrados por género y temporada
        cargarProductosFiltrados("genero", "mujer", binding.recyclerViewMujer, onAddToCart)
        cargarProductosFiltrados("genero", "hombre", binding.recyclerViewHombre, onAddToCart)
        cargarProductosFiltrados("temporada", "verano", binding.recyclerViewTemporada, onAddToCart)
    }

    private fun cargarProductosFiltrados(
        key: String,
        value: String,
        recyclerView: androidx.recyclerview.widget.RecyclerView,
        onAddToCart: (Producto) -> Unit
    ) {
        RetrofitClient.instance.getProductosFiltrados(key, value)
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        val productos = response.body()?.data ?: emptyList()
                        val adapter = ProductoAdapter(productos, onAddToCart)

                        recyclerView.apply {
                            layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                            this.adapter = adapter
                        }
                    } else {
                        Toast.makeText(requireContext(), "Error al cargar productos [$key=$value]", Toast.LENGTH_SHORT).show()
                        Log.e("API_ERROR", "Error body: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Fallo en la conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                    Log.e("API_ERROR", "Fallo en la conexión: ${t.message}", t)
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
