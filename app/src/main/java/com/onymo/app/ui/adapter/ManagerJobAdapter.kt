package com.onymo.app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onymo.app.data.model.JobItem
import com.onymo.app.databinding.ItemManagerJobCardBinding
import kotlinx.coroutines.Job

/**
 * RecyclerView Adapter
 * - 직업 목록을 RecyclerView에서 표시하는 어댑터
 * - 데이터 바인딩을 활용하여 UI를 업데이트
 */
class ManagerJobAdapter(
    private var jobList: List<JobItem>, // 데이터 리스트
    private val itemWidth: Int, // 아이템의 너비
    private val itemHeight: Int, // 아이템의 높이
    private val onItemClick: (JobItem) -> Unit // 카드 클릭 시 실행할 콜백 함수
) : RecyclerView.Adapter<ManagerJobAdapter.JobViewHolder>() {

    /**
     * ViewHolder 클래스
     * - 데이터 바인딩을 사용하여 데이터를 바인딩
     */
    inner class JobViewHolder(private val binding: ItemManagerJobCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * JobItem 데이터를 UI에 바인딩
         */
        fun bind(job: JobItem) {
            binding.job = job // 데이터 바인딩을 활용한 UI 갱신
            binding.executePendingBindings() // 즉시 바인딩 실행

            // ✅ 클릭 이벤트를 하나로 통합
            // ✅ 아이템 클릭 이벤트 (카드뷰 선택 시 바텀시트에 데이터 전달)
            binding.root.setOnClickListener {
                onItemClick(job) // 선택된 카드의 JobItem 데이터를 콜백으로 전달
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        // 데이터 바인딩을 활용하여 ViewHolder 생성
        val binding = ItemManagerJobCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return JobViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        // ✅ 아이템 크기 동적으로 설정
        val layoutParams = holder.itemView.layoutParams
        layoutParams.width = itemWidth // 너비 고정
        layoutParams.height = (itemWidth * 1.2).toInt() // 높이는 비율로 설정
        layoutParams.height = itemHeight // 높이 설정
        holder.itemView.layoutParams = layoutParams

        // ViewHolder와 데이터를 연결
        holder.bind(jobList[position])
    }

    override fun getItemCount(): Int = jobList.size

    /**
     * 데이터를 업데이트하는 함수
     */
    fun updateJobs(jobs: List<JobItem>) {
        jobList = jobs
        notifyDataSetChanged() // RecyclerView 데이터 갱신
    }
}