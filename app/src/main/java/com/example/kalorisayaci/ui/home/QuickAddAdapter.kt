package com.example.kalorisayaci.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.kalorisayaci.R
import com.example.kalorisayaci.databinding.ItemQuickAddBinding

class QuickAddAdapter(
    private val items: List<QuickAddItem>,
    private val onClick: (QuickAddItem) -> Unit
) : RecyclerView.Adapter<QuickAddAdapter.QuickAddViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuickAddViewHolder {
        val binding = ItemQuickAddBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuickAddViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuickAddViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            tvFoodName.text = item.name
            tvCalories.text = "${item.calories} kcal"
            
            // Load image from URL using Glide
            Glide.with(ivFood.context)
                .load(item.imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivFood)
            
            // Add meal to nutrition when clicked
            root.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun getItemCount() = items.size

    inner class QuickAddViewHolder(val binding: ItemQuickAddBinding) :
        RecyclerView.ViewHolder(binding.root)
} 