package com.example.proyectodam.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectodam.ui.shoppingcart.CarritoData
import com.example.proyectodam.databinding.PedidoCardBinding
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class HistoryAdapter(private val itemHistory: List<Pedido>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int ): HistoryViewHolder {
        val binding = PedidoCardBinding.inflate(LayoutInflater.from(parent.context), parent, false  )
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = itemHistory[position]
        holder.binding.pedidoNumero.text = "Pedido: ${position + 1}"

        val fechaFormateada = formatearFechaLegacy(item.fecha)
        holder.binding.pedidoFecha.text = "Fecha: ${fechaFormateada}"

        val textoProductos = item.productos.joinToString(", "){ it.nombre }
        holder.binding.pedidoProductos.text = textoProductos

    }

    override fun getItemCount(): Int = itemHistory.size
    inner class HistoryViewHolder(val binding: PedidoCardBinding) :
            RecyclerView.ViewHolder(binding.root)


    fun formatearFechaLegacy(fechaISO: String): String {

        val formatoEntrada = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        formatoEntrada.timeZone = TimeZone.getTimeZone("UTC")

        val fecha: Date = formatoEntrada.parse(fechaISO) ?: return "Fecha inválida"

        // Formatear a formato legible
        val formatoSalida = SimpleDateFormat("dd MMM yyyy", Locale("es", "MX"))
        return formatoSalida.format(fecha)
    }
}