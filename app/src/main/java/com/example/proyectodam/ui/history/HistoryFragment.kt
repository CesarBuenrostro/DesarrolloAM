package com.example.proyectodam.ui.history


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectodam.data.api.ApiResponseGeneric
import com.example.proyectodam.data.api.RetrofitClient
import com.example.proyectodam.data.api.SessionManager
import com.example.proyectodam.databinding.FragmentHistoryBinding
import com.example.proyectodam.ui.history.Pedido
import com.example.proyectodam.ui.shoppingcart.ApiResponseCarrito
import com.example.proyectodam.ui.shoppingcart.CarritoAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

private lateinit var historyAdapter: HistoryAdapter

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.show()

        val  sessionManager = SessionManager(requireContext())

        val token = sessionManager.fetchAuthToken()
        val tokenConBearer = "Bearer $token"

        RetrofitClient.instance.obtenerHistorial(tokenConBearer).enqueue(object : Callback<ApiResponseHistory> {
            override fun onResponse(call: Call<ApiResponseHistory>, response: Response<ApiResponseHistory>) {


                if (response.isSuccessful && response.body()?.success == true) {

                    val pedidos = response.body()

                    val items = pedidos?.data ?: emptyList()

                    if (items.isNotEmpty()) {
                        binding.recyclerHistory.apply {
                            layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )

                        }

                    } else {
                        Toast.makeText(requireContext(), "Haz pedidos", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Error al obtener el historial", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponseHistory>, t: Throwable) {
                Log.d("HistoryFragment", "Respuesta carrito: ${t}")
                Toast.makeText(requireContext(), "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }
}
