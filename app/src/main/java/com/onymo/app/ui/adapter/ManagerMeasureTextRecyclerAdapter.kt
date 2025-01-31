package com.onymo.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onymo.app.data.model.MeasureItem
import com.onymo.app.databinding.ItemManagerMeasureTextBinding

class ManagerMeasureTextRecyclerAdapter(private var items: List<MeasureItem>) :
    RecyclerView.Adapter<ManagerMeasureTextRecyclerAdapter.TextViewHolder>() {

    inner class TextViewHolder(private val binding: ItemManagerMeasureTextBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MeasureItem) {
            binding.measureNumber.text = "${item.id}."
            binding.measureInput.hint = item.title
        }
    }

    fun updateItems(newItems: List<MeasureItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val binding = ItemManagerMeasureTextBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TextViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
