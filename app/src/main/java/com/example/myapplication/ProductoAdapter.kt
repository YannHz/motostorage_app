package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductoAdapter(
    private var productos: List<ProductoEntity>,
    private val onItemClick: (ProductoEntity) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvItemNombre)
        val tvModelo: TextView = view.findViewById(R.id.tvItemModelo)
        val tvPrecio: TextView = view.findViewById(R.id.tvItemPrecio)
        val tvStock: TextView = view.findViewById(R.id.tvItemStock)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.tvNombre.text = producto.nombreProducto
        holder.tvModelo.text = "${producto.modelo} - ${producto.categoria}"
        holder.tvPrecio.text = "S/.${String.format("%,.0f", producto.precio)}"
        holder.tvStock.text = "  Stock: ${producto.stock}"

        holder.itemView.setOnClickListener {
            onItemClick(producto)
        }
        // Si el stock está bajo el mínimo, mostrar en rojo
        if (producto.stock <= producto.stockMinimo) {
            holder.tvStock.setTextColor(
                holder.itemView.context.getColor(R.color.red)
            )
        } else {
            holder.tvStock.setTextColor(
                holder.itemView.context.getColor(R.color.green)
            )
        }
    }

    override fun getItemCount() = productos.size

    fun actualizarLista(nuevaLista: List<ProductoEntity>) {
        productos = nuevaLista
        notifyDataSetChanged()
    }
}