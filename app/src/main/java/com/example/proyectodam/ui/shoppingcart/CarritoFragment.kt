package com.example.proyectodam.ui.shoppingcart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectodam.R
import com.example.proyectodam.data.api.ApiResponseGeneric
import com.example.proyectodam.data.api.RetrofitClient
import com.example.proyectodam.data.api.SessionManager
import com.example.proyectodam.databinding.FragmentCarritoBinding
import com.example.proyectodam.ui.shop.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
private lateinit var sessionManager: SessionManager
private lateinit var carritoAdapter: CarritoAdapter


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



        RetrofitClient.instance.obtenerCarrito(tokenConBearer).enqueue(object : Callback<ApiResponseCarrito> {
            override fun onResponse(call: Call<ApiResponseCarrito>, response: Response<ApiResponseCarrito>) {

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
                            carritoAdapter = CarritoAdapter(items.toMutableList()){
                                item,position ->
                                val builder = AlertDialog.Builder(requireContext())
                                builder.setTitle("Confirmar eliminación")
                                builder.setMessage("¿Deseas eliminar este producto del carrito?")
                                builder.setPositiveButton("Sí") { _, _ ->
                                    eliminarItemDelCarrito(tokenConBearer, item.productoId, position)
                                }
                                builder.setNegativeButton("Cancelar", null)
                                builder.show()

                            }
                            adapter = carritoAdapter
                        }
                    } else {
                        Toast.makeText(requireContext(), "Agrega Productos al carrito", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Error al obtener el carrito", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponseCarrito>, t: Throwable) {
                Toast.makeText(requireContext(), "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        binding.IBClearCarrito.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Confirmar")
            builder.setMessage("¿Estás seguro de que deseas vaciar el carrito?")

            builder.setPositiveButton("Sí") { _, _ ->
                // Llamar a la API para vaciar el carrito
                RetrofitClient.instance.clearCarrito("Bearer $token").enqueue(object : Callback<ApiResponseCarrito> {
                    override fun onResponse(call: Call<ApiResponseCarrito>, response: Response<ApiResponseCarrito>) {
                        if (response.isSuccessful) {
                            carritoAdapter.updateItems(mutableListOf())
                            binding.tvTotal.text = "0"
                            Toast.makeText(requireContext(), "Carrito vaciado correctamente", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "Error al vaciar el carrito", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ApiResponseCarrito>, t: Throwable) {
                        Toast.makeText(requireContext(), "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }

            builder.setNegativeButton("Cancelar", null)
            builder.show()
        }



        binding.btnPagar.setOnClickListener {
                val token = sessionManager.fetchAuthToken()
                val tokenConBearer = "Bearer $token"

                RetrofitClient.instance.crearPedido(tokenConBearer)
                    .enqueue(object : Callback<ApiResponseGeneric> {
                        override fun onResponse(call: Call<ApiResponseGeneric>, response: Response<ApiResponseGeneric>) {
                            if (response.isSuccessful && response.body()?.success == true) {
                                Toast.makeText(requireContext(), "¡Pedido realizado con éxito!", Toast.LENGTH_SHORT).show()

                                findNavController().navigate(R.id.nav_history)


                            } else {
                                Log.e("PEDIDO_ERROR", "Mensaje del servidor: ${response.body()?.message}")
                                Toast.makeText(requireContext(), "No se pudo realizar el pedido", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<ApiResponseGeneric>, t: Throwable) {
                            Toast.makeText(requireContext(), "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
        } //



    }

    private fun clearCarrito(token: String) {
        RetrofitClient.instance.clearCarrito(token).enqueue(object : Callback<ApiResponseCarrito> {
            override fun onResponse(call: Call<ApiResponseCarrito>, response: Response<ApiResponseCarrito>) {
                if (response.isSuccessful) {
                    carritoAdapter.updateItems(mutableListOf())
                    binding.tvTotal.text = "0"
                    Toast.makeText(requireContext(), "Carrito vaciado", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("API", "Error al vaciar carrito: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ApiResponseCarrito>, t: Throwable) {
                Log.e("API_ERROR", "Fallo al conectar con la API", t)
            }
        })
    }

    private fun eliminarItemDelCarrito(token: String, itemId: String, position: Int) {
        RetrofitClient.instance.deleteProductCarrito(token, itemId).enqueue(object : Callback<ApiResponseCarrito> {
            override fun onResponse(call: Call<ApiResponseCarrito>, response: Response<ApiResponseCarrito>) {
                if (response.isSuccessful) {
                    carritoAdapter.removeItemAt(position)
                    // Actualizar total si tu respuesta lo contiene
                    val nuevoTotal = response.body()?.data?.total ?: 0
                    binding.tvTotal.text = "$nuevoTotal"
                    Toast.makeText(requireContext(), "Producto eliminado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Error al eliminar producto", Toast.LENGTH_SHORT).show()
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