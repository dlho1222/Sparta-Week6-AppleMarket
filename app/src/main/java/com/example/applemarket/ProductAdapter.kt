package com.example.applemarket

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applemarket.ProductList.list
import com.example.applemarket.databinding.ItemProductBinding
import java.text.DecimalFormat

class ProductAdapter(val listener: OnClickListener) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private val decimalFormat = DecimalFormat("#,###")
    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.ivItemImage.setImageResource(product.imageFile)
            binding.tvItemName.text = product.title
            binding.tvLocation.text = product.location
            binding.tvPrice.text = "${decimalFormat.format(product.price)}원"
            binding.tvLikeCount.text = product.likeCount.toString()
            binding.tvSpeechBubbleCount.text = product.conversationCount.toString()
            if(product.isLiked){
                binding.ivLike.setImageResource(R.drawable.heart)
                binding.tvLikeCount.text = binding.tvLikeCount.text.toString().toInt().plus(1).toString()
            } else {
                binding.ivLike.setImageResource(R.drawable.emptyheart)
                binding.tvLikeCount.text = binding.tvLikeCount.text
            }
        }

    }

    //viewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size   //ProductList의 size 만큼 아이템 생성
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = list[position]
        holder.bind(product)

        holder.itemView.setOnLongClickListener {//아이템뷰 롱 클릭시 처리
            listener.longClick(product)
            true
        }
        holder.itemView.setOnClickListener {//아이템뷰 클릭시 처리
            listener.itemClick(product)
        }

    }

}

interface OnClickListener {  //클릭 이벤트 처리
    fun itemClick(product: Product)
    fun longClick(product: Product)

}