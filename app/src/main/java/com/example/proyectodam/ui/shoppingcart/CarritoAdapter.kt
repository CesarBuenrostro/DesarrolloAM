package com.example.proyectodam.ui.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectodam.databinding.CarritoitemCardBinding

class CarritoAdapter(private val items: List<ItemCarrito>) :
    RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>() {

    inner class CarritoViewHolder(val binding: CarritoitemCardBinding) :
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        val binding = CarritoitemCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CarritoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {
        val item = items[position]

        holder.binding.tvNombre.text = item.nombre
        holder.binding.tvPrecio.text = "${item.precio}"
        holder.binding.tvCantidad.text = "Cantidad: ${item.cantidad}"
        holder.binding.tvTalla.text = "Talla${item.talla}"
        holder.binding.tvSubtotal.text = "SubTotal: ${ item.subtotal }"
    }

    override fun getItemCount(): Int = items.size
}

