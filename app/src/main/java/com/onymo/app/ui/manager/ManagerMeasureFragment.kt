package com.onymo.app.ui.manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.onymo.app.R
import com.onymo.app.databinding.FragmentManagerMeasureBinding
import com.onymo.app.ui.adapter.ManagerMeasureAdapter
import com.onymo.app.ui.manager.viewmodel.ManagerMeasureViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * ManagerMeasureFragment - 척도 관련 내용을 관리하는 Fragment
 */
class ManagerMeasureFragment : Fragment() {

    private var _binding: FragmentManagerMeasureBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ManagerMeasureViewModel
    private lateinit var adapter: ManagerMeasureAdapter

    /**
     * onCreateView - 프래그먼트 레이아웃 초기화
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManagerMeasureBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ManagerMeasureViewModel::class.java] // ViewModel 초기화
        return binding.root
    }

    /**
     * onViewCreated - UI 초기화 및 이벤트 설정
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        setupListeners()
    }

    /**
     * onDestroyView - 메모리 누수를 방지하기 위해 Binding 해제
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * setupRecyclerView - RecyclerView 초기화
     */
    private fun setupRecyclerView() {
        adapter = ManagerMeasureAdapter(
            outlineItems = emptyList(),
            onMoveUp = { position -> viewModel.moveOutlineUp(position) }, // 위로 이동 콜백
            onMoveDown = { position -> viewModel.moveOutlineDown(position) }, // 아래로 이동 콜백
            onDelete = { position -> viewModel.deleteOutline(position) }, // 삭제 콜백
            innerItemsProvider = { outlineId -> // 내부 텍스트 아이템 제공
                viewModel.innerMeasureItemsMap.value[outlineId] ?: emptyList()
            }
        )
        binding.recyclerMeasureOutlineItems.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerMeasureOutlineItems.adapter = adapter
    }

    /**
     * observeViewModel - ViewModel의 데이터를 관찰하여 UI 업데이트
     */
    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.outlineItems.collectLatest { outlines ->
                adapter = ManagerMeasureAdapter(
                    outlineItems = outlines,
                    onMoveUp = { position -> viewModel.moveOutlineUp(position) }, // 위로 이동 콜백
                    onMoveDown = { position -> viewModel.moveOutlineDown(position) }, // 아래로 이동 콜백
                    onDelete = { position -> viewModel.deleteOutline(position) }, // 삭제 콜백
                    innerItemsProvider = { outlineId ->
                        viewModel.innerMeasureItemsMap.value[outlineId] ?: emptyList()
                    }
                )
                adapter.notifyDataSetChanged()
                binding.recyclerMeasureOutlineItems.adapter = adapter
            }
        }
    }

    /**
     * setupListeners - 버튼 클릭 리스너 설정
     */
    private fun setupListeners() {
        binding.btnAdd.setOnClickListener {
            val inputText = binding.scoreInput.text.toString()
            val count = inputText.toIntOrNull()

            if (count == null || count <= 0) {
                // 입력값이 숫자가 아니거나 0 이하일 경우
                binding.errorMessage.text = getString(R.string.manager_measure_error_invalid_number) // 에러 메시지 표시
                binding.errorMessage.visibility = View.VISIBLE
            } else if (count > 10) {
                // 숫자가 너무 큰 경우 제한 메세지 표시
                binding.errorMessage.text = getString(R.string.manager_measure_error_number_too_large) // 제한된 메시지 표시
                binding.errorMessage.visibility = View.VISIBLE
            } else {
                // 유효한 숫자인 경우
                binding.errorMessage.visibility = View.GONE // 에러 메시지 제거
                viewModel.addOutline(count) // ViewModel에 숫자 전달하여 항목 추가
                binding.scoreInput.text?.clear() // 입력창 초기화
            }
        }

        // 에러 메시지 제거 이벤트 (사용자가 입력을 수정할 때)
        binding.scoreInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.errorMessage.visibility = View.GONE
            }
        }
    }
}
