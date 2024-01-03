package com.example.applemarket

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applemarket.ProductList.list
import com.example.applemarket.databinding.ItemProductBinding

class ProductAdapter() : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.ivItemImage.setImageResource(product.imageFile)
            binding.tvItemName.text = product.title
            binding.tvLocation.text = product.location
            binding.tvPrice.text = product.price.toString()
            binding.tvLikeCount.text = product.like.toString()
            binding.tvSpeechBubbleCount.text = product.conversation.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ProductList.list.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = list[position]
        holder.bind(product)
    }
}