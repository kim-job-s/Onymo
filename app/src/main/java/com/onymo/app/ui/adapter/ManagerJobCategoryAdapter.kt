package com.onymo.app.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.onymo.app.R
import com.onymo.app.data.model.JobCategory

/**
 * RecyclerView Adapter
 * - 중분류 및 세분류 리스트를 바텀시트에서 보여주기 위한 어댑터
 * - 선택한 아이템이 강조되도록 설정됨 (ripple_selected.xml 적용)
 */
class ManagerJobCategoryAdapter(
    private val onCategoryClick: (JobCategory) -> Unit // ✅ 클릭 시 실행할 콜백 함수
) : ListAdapter<JobCategory, ManagerJobCategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    private var selectedPosition = RecyclerView.NO_POSITION // ✅ 선택된 아이템 위치 저장

    /**
     * ViewHolder 클래스
     */
    class CategoryViewHolder(view: View, private val onCategoryClick: (JobCategory) -> Unit) :
        RecyclerView.ViewHolder(view) {

        private val categoryTitle: TextView = view.findViewById(R.id.mid_category_title) // ✅ 제목
        private val cardView: View = view.findViewById(R.id.category_card) // ✅ CardView 참조 수정

        /**
         * UI에 데이터 설정
         */
        fun bind(category: JobCategory, isSelected: Boolean) {
            categoryTitle.text = category.title // ✅ 중분류 제목 설정

            // ✅ 선택 상태에 따라 배경 변경 (ripple_selected.xml 적용)
            cardView.isSelected = isSelected
            cardView.setBackgroundResource(R.drawable.ripple_selected) // ✅ ripple 적용

            // ✅ 클릭 이벤트 처리
            cardView.setOnClickListener {
                onCategoryClick(category) // 선택된 카테고리를 콜백으로 전달
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_manager_job_mid_category, parent, false) // ✅ 레이아웃 파일 설정
        return CategoryViewHolder(view, onCategoryClick)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.bind(getItem(position), position == selectedPosition)

        // ✅ 클릭 시 선택된 아이템 업데이트
        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = position

            // ✅ 이전 선택 해제 (RecyclerView.NO_POSITION이 아닐 때만 실행)
            if (previousPosition != RecyclerView.NO_POSITION) {
                notifyItemChanged(previousPosition)
            }

            notifyItemChanged(selectedPosition) // ✅ 새로운 선택 적용
            onCategoryClick(getItem(position))
        }
    }
}

/**
 * DiffUtil Callback
 * - 리스트 변경 시 차이점을 계산하여 RecyclerView를 효율적으로 갱신
 */
class CategoryDiffCallback : DiffUtil.ItemCallback<JobCategory>() {
    override fun areItemsTheSame(oldItem: JobCategory, newItem: JobCategory): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: JobCategory, newItem: JobCategory): Boolean {
        return oldItem == newItem
    }
}
