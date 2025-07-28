package com.example.proyectodam.ui.shop

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectodam.databinding.BoxitemProductBinding

class ProductoAdapter(private val productos: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = BoxitemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.binding.productName.text = producto.nombre
        holder.binding.productPrice.text = producto.precio
        holder.binding.productSize.text = producto.talla

        Glide.with(holder.itemView.context)
            .load(producto.imagen)
            .placeholder(R.drawable.arrow_down_float) // opcional: imagen mientras carga
            .error(R.drawable.arrow_down_float)             // opcional: imagen si falla
            .into(holder.binding.productImage)         // 👈 Asegúrate que este ID exista en tu layout
    }


    override fun getItemCount(): Int = productos.size

    inner class ProductoViewHolder(val binding: BoxitemProductBinding) :
        RecyclerView.ViewHolder(binding.root)
}
