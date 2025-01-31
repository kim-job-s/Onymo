package com.onymo.app.ui.managerjob

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.onymo.app.databinding.FragmentManagerJobBinding
import com.onymo.app.ui.adapter.ManagerJobAdapter
import com.onymo.app.viewmodel.ManagerJobViewModel

/**
 * ✅ 직업 선택 화면 (ManagerJobFragment)
 * - RecyclerView를 통해 직업 목록을 표시
 * - 카드 클릭 시 바텀시트에서 중분류부터 선택 가능
 */
class ManagerJobFragment : Fragment() {

    // ✅ ViewBinding 및 ViewModel 선언
    private lateinit var binding: FragmentManagerJobBinding
    private lateinit var viewModel: ManagerJobViewModel
    private lateinit var adapter: ManagerJobAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // ✅ 데이터 바인딩 초기화
        binding = FragmentManagerJobBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ✅ ViewModel 초기화
        viewModel = ViewModelProvider(this).get(ManagerJobViewModel::class.java)
        binding.viewModel = viewModel // 데이터 바인딩으로 ViewModel 연결
        binding.lifecycleOwner = viewLifecycleOwner // LiveData 관찰 가능하도록 설정

        // ✅ RecyclerView 초기화
        setupRecyclerView()

        // ✅ LiveData 관찰
        observeLiveData()
    }

    /**
     * ✅ RecyclerView를 설정하는 함수
     */
    private fun setupRecyclerView() {
        // ✅ 화면 크기 계산 (그리드 설정)
        val spanCount = 4 // 열 개수
        val spacing = 8 // 아이템 간의 간격(dp 단위)
        val displayMetrics: DisplayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels

        // ✅ 아이템의 크기를 화면 너비에서 계산
        val itemWidth = ((screenWidth - (spacing * (spanCount + 1))) / spanCount)
        val itemHeight = itemWidth // 높이를 너비와 동일하게 설정

        // ✅ RecyclerView 초기화
        adapter = ManagerJobAdapter(
            emptyList(),
            itemWidth,
            itemHeight
        ) { selectedJob ->
            viewModel.selectJob(selectedJob) // ✅ 선택된 직업 업데이트 (제목 + 중분류)
            showBottomSheet() // ✅ 바텀시트 실행
        }

        binding.recyclerManagerJob.layoutManager = GridLayoutManager(context, spanCount)
        binding.recyclerManagerJob.adapter = adapter
    }

    /**
     * ✅ LiveData를 관찰하여 UI 업데이트
     */
    private fun observeLiveData() {
        // ✅ 직업 리스트 관찰하여 RecyclerView 업데이트
        viewModel.jobs.observe(viewLifecycleOwner) { jobList ->
            adapter.updateJobs(jobList)
        }
    }

    /**
     * ✅ 바텀 시트 띄우기
     */
    private fun showBottomSheet() {
        val bottomSheet = ManagerJobBottomSheetFragment(viewModel)
        bottomSheet.show(parentFragmentManager, "BottomSheetFragmentTag")
    }
}