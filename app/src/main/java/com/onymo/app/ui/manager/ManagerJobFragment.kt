package com.onymo.app.ui.manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.onymo.app.databinding.FragmentManagerJobBinding

/**
 * ManagerJobFragment - 직무 관련 내용을 관리하는 프래그먼트
 */
class ManagerJobFragment : Fragment() {

    private var _binding: FragmentManagerJobBinding? = null
    private val binding get() = _binding!!

    /**
     * onCreateView - 프래그먼트의 레이아웃을 초기화하고 뷰를 반환
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentManagerJobBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * onViewCreated - 뷰가 생성된 후 호출되며 초기 UI 설정 작업 수행
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 직무 데이터를 초기화하거나 UI를 업데이트
        setupUI()
    }

    /**
     * onDestroyView - 뷰가 파괴되기 전에 호출되어 리소스 해제
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 누수 방지
    }

    /**
     * setupUI - UI 초기화 및 이벤트 설정
     */
    private fun setupUI() {
        // 직무 관련 데이터나 리스너 설정
        //binding.jobText.text = "직무 섹션입니다."
    }
}