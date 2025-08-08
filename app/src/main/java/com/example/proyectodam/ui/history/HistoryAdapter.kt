package com.example.proyectodam.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectodam.ui.shoppingcart.CarritoData
import com.example.proyectodam.databinding.PedidoCardBinding

class HistoryAdapter(private val itemHistory: List<Pedido>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int ): HistoryViewHolder {
        val binding = PedidoCardBinding.inflate(LayoutInflater.from(parent.context), parent, false  )
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = itemHistory[position]
        holder.binding.pedidoNumero.text = "Pedido: ${position}"
        holder.binding.pedidoFecha.text = item.fecha

    }

    override fun getItemCount(): Int = itemHistory.size
    inner class HistoryViewHolder(val binding: PedidoCardBinding) :
            RecyclerView.ViewHolder(binding.root)
}