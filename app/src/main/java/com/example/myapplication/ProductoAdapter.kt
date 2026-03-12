package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductoAdapter(private var listaProductos: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombreProducto)
        val tvModelo: TextView = view.findViewById(R.id.tvModeloProducto)
        val tvPrecio: TextView = view.findViewById(R.id.tvPrecioProducto)
        val tvStock: TextView = view.findViewById(R.id.tvStockProducto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = listaProductos[position]

        holder.tvNombre.text = producto.nombre
        holder.tvModelo.text = "Modelo: ${producto.modelo}"
        holder.tvPrecio.text = "S/. ${producto.precio}"
        holder.tvStock.text = "Stock: ${producto.stock}"
    }

    override fun getItemCount(): Int = listaProductos.size

    fun actualizarLista(nuevaLista: List<Producto>) {
        this.listaProductos = nuevaLista
        notifyDataSetChanged()
    }
}