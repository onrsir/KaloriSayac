package com.example.kalorisayaci.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class QuickAddViewHolder(private val binding: ItemQuickAddBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onClick(items[position])
                }
            }
        }

        fun bind(item: QuickAddItem) {
            binding.tvFoodName.text = item.name
            binding.tvCalories.text = "${item.calories} kcal"
            binding.ivFood.setImageResource(item.imageResId)
        }
    }
} 