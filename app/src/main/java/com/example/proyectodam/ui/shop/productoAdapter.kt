package com.example.proyectodam.ui.shop


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectodam.R
import com.example.proyectodam.databinding.BoxitemProductBinding

class ProductoAdapter(private val productos: List<Producto>,
    private val onAddCart : (Producto) -> Unit) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = BoxitemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.binding.productName.text = producto.nombre
        holder.binding.productPrice.text = "Precio: ${ producto.precio }"
        holder.binding.productSize.text = "Talla: ${ producto.talla }"

        holder.binding.addCart.setOnClickListener {
            onAddCart(producto)
        }

        val baseUrl = "http://192.168.0.9:3000" // localhost desde el emulador Android
        val fullImageUrl = baseUrl + producto.imagen // ej: /images/img1.jpg

        Glide.with(holder.itemView.context)
            .load(fullImageUrl)
            .placeholder(R.drawable.ic_menu_camera) // opcional: imagen mientras carga
            .error(R.drawable.ic_minus)             // opcional: imagen si falla
            .into(holder.binding.productImage)         // 👈 Asegúrate que este ID exista en tu layout

    }


    override fun getItemCount(): Int = productos.size

    inner class ProductoViewHolder(val binding: BoxitemProductBinding) :
        RecyclerView.ViewHolder(binding.root)
}

