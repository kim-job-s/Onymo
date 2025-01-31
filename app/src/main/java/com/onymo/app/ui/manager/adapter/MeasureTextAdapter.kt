package com.onymo.app.ui.manager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onymo.app.data.model.MeasureItem
import com.onymo.app.databinding.ItemManagerMeasureTextBinding

/**
 * MeasureTextAdapter - 내부 RecyclerView 어댑터
 * @param items 내부 항목 리스트
 */
class MeasureTextAdapter(private val items: List<MeasureItem>) :
    RecyclerView.Adapter<MeasureTextAdapter.TextViewHolder>() {

    /**
     * ViewHolder 클래스 - 개별 항목과 데이터를 바인딩
     */
    inner class TextViewHolder(private val binding: ItemManagerMeasureTextBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * bind - 데이터를 바인딩
         * @param item MeasureItem 데이터
         */
        fun bind(item: MeasureItem) {
            binding.measureNumber.text = "${item.id}."
            binding.measureInput.hint = item.title
        }
    }

    /**
     * onCreateViewHolder - 새로운 ViewHolder 생성
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val binding = ItemManagerMeasureTextBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TextViewHolder(binding)
    }

    /**
     * onBindViewHolder - 데이터를 개별 ViewHolder에 바인딩
     */
    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        holder.bind(items[position])
    }

    /**
     * getItemCount - 항목 개수 반환
     */
    override fun getItemCount(): Int = items.size
}
