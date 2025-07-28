package com.example.proyectodam.ui.shop

import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectodam.data.api.RetrofitClient
import com.example.proyectodam.databinding.FragmentShopBinding

class ShopFragment : Fragment() {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

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

        // Llamada a la API usando Retrofit
        RetrofitClient.instance.getProductos().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val productos = response.body()?.data ?: emptyList()

                    // Asignar productos a los RecyclerView
                    binding.recyclerViewTemporada.apply {
                        adapter = ProductoAdapter(productos)
                        layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                    }

                    binding.recyclerViewMujer.apply {
                        adapter = ProductoAdapter(productos)
                        layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                    }

                    binding.recyclerViewHombre.apply {
                        adapter = ProductoAdapter(productos)
                        layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("API_ERROR", "Error al obtener productos", t)
            }
        })
}


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
