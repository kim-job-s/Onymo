package com.onymo.app.ui.setting.hrinfo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.onymo.app.R
import com.onymo.app.databinding.FragmentHrInfoBinding

class HrInfoFragment : Fragment() {

    // View Binding 객체 선언
    private var _binding: FragmentHrInfoBinding? = null
    private val binding get() = _binding!!

    // 필드의 편집 가능 상태 플래그
    private var isEditingEnabled = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Fragment의 View Binding 초기화
        _binding = FragmentHrInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 초기 버튼 상태 설정
        setupButtonStates()

        // UI 요소와 상호작용 리스너 설정
        setupListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Binding 객체 정리 (메모리 누수 방지)
        _binding = null
    }

    /**
     * 초기 버튼 상태를 설정합니다.
     */
    private fun setupButtonStates() {
        // 초기 상태는 "저장하기"와 "수정하기" 버튼 비활성화
        setButtonEnabled(binding.hrInfoSaveBtn, false)
    }

    /**
     * UI 요소와의 상호작용 리스너를 설정합니다.
     */
    private fun setupListeners() {
        // 텍스트 필드 목록
        val textFields = listOf(
            binding.hrInfoCompanyNameInput,
            binding.hrInfoCompanyLocationInput,
            binding.hrInfoCompanyRegistrationInput,
            binding.hrInfoOrganizationInput,
            binding.hrInfoJobInput,
            binding.hrInfoPositionInput
        )

        // 텍스트 변경 리스너 추가
        for (field in textFields) {
            field.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // 사용되지 않음
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // 텍스트 변경 시 "저장하기" 버튼 활성화 조건 체크
                    val isFilled = isAnyFieldFilled(textFields)
                    setButtonEnabled(binding.hrInfoSaveBtn, isFilled)
                }

                override fun afterTextChanged(s: Editable?) {
                    // 사용되지 않음
                }
            })
        }

        // 저장하기 버튼 클릭 이벤트 처리
        binding.hrInfoSaveBtn.setOnClickListener {
            saveData()
        }
    }

    /**
     * 입력된 텍스트 필드 중 하나라도 비어 있지 않으면 true 반환.
     */
    private fun isAnyFieldFilled(fields: List<com.google.android.material.textfield.TextInputEditText>): Boolean {
        return fields.any { it.text?.isNotEmpty() == true }
    }

    /**
     * 저장하기 버튼 클릭 시 데이터 저장 및 상태 갱신.
     */
    private fun saveData() {
        // 데이터 저장 로직 추가 (예: 네트워크 요청 또는 로컬 저장소)
        disableEditing()

        // "저장하기" 버튼 비활성화
        setButtonEnabled(binding.hrInfoSaveBtn, false)
    }

    /**
     * 필드 편집 활성화/비활성화 설정.
     */
    private fun enableEditing(enabled: Boolean) {
        val textFields = listOf(
            binding.hrInfoCompanyNameInput,
            binding.hrInfoCompanyLocationInput,
            binding.hrInfoCompanyRegistrationInput,
            binding.hrInfoOrganizationInput,
            binding.hrInfoJobInput,
            binding.hrInfoPositionInput
        )
        isEditingEnabled = enabled
        textFields.forEach { it.isEnabled = enabled }
    }

    /**
     * 필드 편집 비활성화 설정.
     */
    private fun disableEditing() {
        enableEditing(false)
    }

    /**
     * 버튼 활성화/비활성화 상태를 설정.
     * @param button View - 상태를 변경할 버튼
     * @param isEnabled Boolean - 버튼 활성화 여부
     */
    private fun setButtonEnabled(button: View, isEnabled: Boolean) {
        button.isEnabled = isEnabled

        // AppCompatButton 또는 TextView에 텍스트 색상을 설정
        if (button is androidx.appcompat.widget.AppCompatButton) {
            val textColorRes = if (isEnabled) {
                R.color.color_on_primary // 활성화 상태의 텍스트 색상
            } else {
                R.color.color_button_disabled_text // 비활성화 상태의 텍스트 색상
            }
            button.setTextColor(ContextCompat.getColor(requireContext(), textColorRes))
        }

        // 버튼 배경 설정
        val backgroundRes = if (isEnabled) {
            R.drawable.btn_bg_save_selector // 활성화 상태 배경
        } else {
            R.drawable.btn_bg_save_selector // 비활성화 상태도 동일한 Selector로 관리
        }
        button.setBackgroundResource(backgroundRes)
    }

}
