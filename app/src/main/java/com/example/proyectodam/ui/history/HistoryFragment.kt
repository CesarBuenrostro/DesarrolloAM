package com.example.proyectodam.ui.history


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.proyectodam.R
import java.text.SimpleDateFormat
import java.util.*

class HistoryFragment : Fragment() {

    private val historial = mutableListOf<Pedido>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        val layoutPedidos = view.findViewById<LinearLayout>(R.id.linearLayoutPedidos)

        // Ejemplo de historial inicial (puedes modificarlo o recibirlo de otro lugar)
        historial.add(Pedido("Pedido 1", "01/08/2025", listOf("Chamara afelpada"), 4.0f))
        historial.add(Pedido("Pedido 2", "31/07/2025", listOf("Sudadera café"), 3.0f))

        cargarHistorial(layoutPedidos)
        return view
    }

    private fun cargarHistorial(layoutPedidos: LinearLayout) {
        for (pedido in historial) {
            val cardView = layoutInflater.inflate(R.layout.pedido_card, null) as CardView
            val imagen = cardView.findViewById<ImageView>(R.id.pedidoImagen)
            val numero = cardView.findViewById<TextView>(R.id.pedidoNumero)
            val fecha = cardView.findViewById<TextView>(R.id.pedidoFecha)
            val productosText = cardView.findViewById<TextView>(R.id.pedidoProductos)
            val rating = cardView.findViewById<RatingBar>(R.id.pedidoRating)

            imagen.setImageResource(R.drawable.img1) // Asegúrate de tener esta imagen
            numero.text = pedido.numero
            fecha.text = pedido.fecha
            productosText.text = pedido.productos.joinToString(", ")
            rating.rating = pedido.calificacion

            layoutPedidos.addView(cardView)
        }
    }
}

// Clase de datos para el historial
data class Pedido(val numero: String, val fecha: String, val productos: List<String>, val calificacion: Float)