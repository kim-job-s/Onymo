package com.onymo.app.ui.manager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.onymo.app.data.model.MeasureItem
import com.onymo.app.databinding.ItemManagerMeasureOutlineBinding
import com.onymo.app.ui.adapter.ManagerMeasureTextRecyclerAdapter

/**
 * ManagerMeasureAdapter - RecyclerView를 위한 아웃라인 어댑터
 * @param outlineItems 아웃라인 리스트
 * @param onMoveUp 아이템 이동(위로) 콜백 함수
 * @param onMoveDown 아이템 이동(아래로) 콜백 함수
 * @param onDelete 아이템 삭제 콜백 함수
 * @param innerItemsProvider 내부 텍스트 아이템 리스트 제공 함수
 */
class ManagerMeasureRecyclerAdapter(
    private val outlineItems: List<MeasureItem>,
    private val onMoveUp: (Int) -> Unit,
    private val onMoveDown: (Int) -> Unit,
    private val onDelete: (Int) -> Unit,
    private val innerItemsProvider: (Int) -> List<MeasureItem> // 내부 텍스트 아이템 제공 함수
) : RecyclerView.Adapter<ManagerMeasureRecyclerAdapter.OutlineViewHolder>() {

    /**
     * ViewHolder 클래스 - 아웃라인 항목과 데이터를 바인딩
     */
    inner class OutlineViewHolder(private val binding: ItemManagerMeasureOutlineBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * bind - 아웃라인 데이터를 바인딩
         * @param outline MeasureItem 데이터
         */
        fun bind(outline: MeasureItem) {
            binding.titleManagerMeasure.text = outline.title

            // 내부 RecyclerView 설정
            val innerAdapter = ManagerMeasureTextRecyclerAdapter(innerItemsProvider(outline.id))
            binding.recyclerMeasureTextItems.adapter = innerAdapter
            binding.recyclerMeasureTextItems.layoutManager = LinearLayoutManager(binding.root.context)

            // 위로 버튼 클릭 리스너
            binding.btnMeasureArrowUp.setOnClickListener {
                if (position > 0) onMoveUp(position)
            }

            // 아래로 버튼 클릭 리스너
            binding.btnMeasureArrowDown.setOnClickListener {
                if (position < outlineItems.size - 1) onMoveDown(position)
            }

            // 삭제 버튼 클릭 리스너
            binding.btnMeasureCancel.setOnClickListener {
                onDelete(position)
            }
        }
    }

    /**
     * onCreateViewHolder - 새로운 ViewHolder 생성
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutlineViewHolder {
        val binding = ItemManagerMeasureOutlineBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return OutlineViewHolder(binding)
    }

    /**
     * onBindViewHolder - 데이터를 ViewHolder에 바인딩
     */
    override fun onBindViewHolder(holder: OutlineViewHolder, position: Int) {
        holder.bind(outlineItems[position])
    }

    /**
     * getItemCount - 아웃라인 개수 반환
     */
    override fun getItemCount(): Int = outlineItems.size
}