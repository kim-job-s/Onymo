package com.onymo.app.ui.managerjob

import android.os.Bundle
import android.view.*
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.onymo.app.R
import com.onymo.app.databinding.FragmentManagerJobBottomSheetBinding
import com.onymo.app.ui.adapter.ManagerJobCategoryAdapter
import com.onymo.app.viewmodel.ManagerJobViewModel

/**
 * ✅ 바텀시트 프래그먼트
 * - 중분류 선택 시, 바텀시트에서 소분류 및 추가 카테고리 표시
 * - 데이터 변경에 따라 높이가 동적으로 변경됨
 */
class ManagerJobBottomSheetFragment(
    private val viewModel: ManagerJobViewModel
) : BottomSheetDialogFragment() {

    private var _binding: FragmentManagerJobBottomSheetBinding? = null
    private val binding get() = _binding!!
    // ✅ 어댑터 선언
    private lateinit var midCategoryAdapter: ManagerJobCategoryAdapter
    private lateinit var smallCategoryAdapter: ManagerJobCategoryAdapter
    private lateinit var detailCategoryAdapter: ManagerJobCategoryAdapter
    private lateinit var unitCategoryAdapter: ManagerJobCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManagerJobBottomSheetBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        // ✅ BottomSheetDialog에서 BottomSheetBehavior 가져오기
        val dialog = dialog as BottomSheetDialog
        val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from(it)
            behavior.apply {
                state = BottomSheetBehavior.STATE_COLLAPSED  // ✅ 처음에는 하단에서 일부만 보이도록 설정
                peekHeight = ViewGroup.LayoutParams.MATCH_PARENT // ✅ 높이를 전체 화면과 유사하게 설정
                halfExpandedRatio = 0.7f  // ✅ 화면의 70%까지 올라오게 조정
                isFitToContents = true  // ✅ 컨텐츠 크기에 맞춰서 동작하도록 설정
                isDraggable = true  // ✅ 사용자가 위로 드래그하면 펼쳐지도록 설정
            }
        }

        // ✅ 배경 투명도 조정 (0.5 = 반투명)
        dialog.window?.setDimAmount(0.5f)

        // ✅ 중분류 리스트 RecyclerView 설정
        setupRecyclerView()

        /**
         * ✅ 바텀시트 닫기 LiveData 관찰
         * - ViewModel에서 `hideBottomSheet()`가 호출되면 `dismiss()` 실행
         */
        viewModel.isBottomSheetVisible.observe(viewLifecycleOwner, Observer { isVisible ->
            if (!isVisible) dismiss()
        })
    }

    /**
     * ✅ RecyclerView 설정
     * - 중분류 클릭 시 → 소분류 데이터 로드 및 바텀시트 크기 변경
     */
    private fun setupRecyclerView() {
        // ✅ 중분류 Adapter 설정 (클릭 시 소분류 로드)
        midCategoryAdapter = ManagerJobCategoryAdapter { selectedCategory ->
            viewModel.selectMidCategory(selectedCategory) // ✅ 중분류 선택 시 -> 소분류 데이터 로드
        }

        binding.recyclerManagerJobMidCategory.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = midCategoryAdapter
        }

        // ✅ 소분류 Adapter 설정
        smallCategoryAdapter = ManagerJobCategoryAdapter { selectedCategory ->
            viewModel.selectSmallCategory(selectedCategory) // ✅ 소분류 선택 시 → 세분류 데이터 로드
        }

        binding.recyclerManagerJobSmallCategory.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = smallCategoryAdapter
        }

        // ✅ 세분류 Adapter 설정
        detailCategoryAdapter = ManagerJobCategoryAdapter { selectedCategory ->
            viewModel.selectDetailCategory(selectedCategory) // ✅ 세분류 선택 시 → 능력단위 데이터 로드
        }

        binding.recyclerManagerJobDetailCategory.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = detailCategoryAdapter
        }

        // ✅ 능력단위 Adapter 설정
        unitCategoryAdapter = ManagerJobCategoryAdapter { selectedCategory ->
            viewModel.selectUnitCategory(selectedCategory) // ✅ 능력단위 선택 시 → 능력단위 데이터 로드
        }

        binding.recyclerManagerJobUnitCategory.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = unitCategoryAdapter
        }

        // ✅ 중분류 데이터 변경 시 UI 업데이트
        viewModel.midCategories.observe(viewLifecycleOwner, Observer { categories ->
            midCategoryAdapter.submitList(categories)
        })

        // ✅ 소분류 데이터 변경 시 UI 업데이트
        viewModel.smallCategories.observe(viewLifecycleOwner, Observer { categories ->
            smallCategoryAdapter.submitList(categories)
        })

        // ✅ 세분류 데이터 변경 시 UI 업데이트
        viewModel.detailCategories.observe(viewLifecycleOwner, Observer { categories ->
            detailCategoryAdapter.submitList(categories)
        })

        // ✅ 능력단위 데이터 변경 시 UI 업데이트
        viewModel.unitCategories.observe(viewLifecycleOwner, Observer { categories ->
            unitCategoryAdapter.submitList(categories)
        })

        // ✅ 닫기 버튼 클릭 이벤트 처리
        binding.closeBtn.setOnClickListener {
            viewModel.hideBottomSheet() // 바텀시트 닫기
            dismiss() // Dialog 닫기
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
