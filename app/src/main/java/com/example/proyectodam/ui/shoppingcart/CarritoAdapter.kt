package com.example.proyectodam.ui.shoppingcart

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectodam.databinding.CarritoitemCardBinding
import android.text.TextWatcher
import com.example.proyectodam.R

class CarritoAdapter(
    private val items: MutableList<ItemCarrito>,
    private  val onItemDelete: (ItemCarrito, Int) -> Unit) :
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
        holder.binding.etCantidad.setText(item.cantidad.toString())
        holder.binding.tvTalla.text = "Talla ${item.talla}"

        val baseUrl = "http://192.168.0.9:3000"
        val fullImageUrl = baseUrl + item.imagen

        Glide.with(holder.itemView.context)
            .load(fullImageUrl)
            .placeholder(R.drawable.ic_menu_camera) // opcional: imagen mientras carga
            .error(R.drawable.ic_minus)             // opcional: imagen si falla
            .into(holder.binding.IVImage)         // 👈 Asegúrate que este ID exista en tu layout

        // Función para actualizar subtotal
        fun actualizarSubtotal(nuevaCantidad: Int) {
            item.cantidad = nuevaCantidad
            item.subtotal = item.precio * nuevaCantidad
            holder.binding.tvSubtotal.text = "SubTotal: ${item.subtotal}"
            // Aquí puedes notificar al fragmento o actividad si quieres actualizar el total general
        }

        // Inicializar subtotal
        actualizarSubtotal(item.cantidad)

        holder.binding.btnEliminarItem.setOnClickListener {
            onItemDelete(item, position)
        }

        holder.binding.btnAumentar.setOnClickListener {
            val cantidadActual = holder.binding.etCantidad.text.toString().toIntOrNull() ?: 1
            val nuevaCantidad = cantidadActual + 1
            holder.binding.etCantidad.setText(nuevaCantidad.toString())
            actualizarSubtotal(nuevaCantidad)
        }

        holder.binding.btnDisminuir.setOnClickListener {
            val cantidadActual = holder.binding.etCantidad.text.toString().toIntOrNull() ?: 1
            if (cantidadActual > 1) {
                val nuevaCantidad = cantidadActual - 1
                holder.binding.etCantidad.setText(nuevaCantidad.toString())
                actualizarSubtotal(nuevaCantidad)
            }
        }

        // Actualizar subtotal al escribir manualmente
        holder.binding.etCantidad.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val nuevaCantidad = s.toString().toIntOrNull() ?: 1
                actualizarSubtotal(nuevaCantidad)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }


    override fun getItemCount(): Int = items.size

    fun removeItemAt(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateItems(newItems: List<ItemCarrito>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}

