package com.onymo.app.ui.mypage.hrinfo

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

    private lateinit var binding: FragmentHrInfoBinding
    private var isEditngEnabled = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHrInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtonStates()
        setupListeners()
    }

    private fun setupButtonStates() {
        // 초기 상태
        setButtonEnabled(binding.btnInput, true)
        setButtonEnabled(binding.btnSave, false)
        setButtonEnabled(binding.btnModify, false)
    }

    private fun setupListeners() {
        //입력하기 버튼 클릭
        binding.btnInput.setOnClickListener {
            enableEditing(true)
            setButtonEnabled(binding.btnInput, false)
            setButtonEnabled(binding.btnSave, false)
        }
        //저장하기 버튼 클릭
        binding.btnSave.setOnClickListener{
            saveDate()
            setButtonEnabled(binding.btnInput, false)
            setButtonEnabled(binding.btnModify, true)
        }
        //수정하기 버튼 클릭
        binding.btnModify.setOnClickListener{
            enableEditing(true)
            setButtonEnabled(binding.btnSave, true)
            setButtonEnabled(binding.btnModify, false)
        }

        // 텍스트 변경 리스너 추가
        val textFields = listOf(
            binding.companyNameInput,
            binding.companyLocationInput,
            binding.companyRegistrationInput,
            binding.organizationInput,
            binding.jobInput,
            binding.positionInput
        )

        for (field in textFields) {
            field.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setButtonEnabled(binding.btnSave, isAnyFieldFilled(textFields))
                }

                override fun afterTextChanged(s: Editable?) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    private fun isAnyFieldFilled(fields: List<com.google.android.material.textfield.TextInputEditText>): Boolean {
        return fields.any { it.text?.isNotEmpty() == true }
    }

    private fun enableEditing(enabled: Boolean) {
        val textFields = listOf(
            binding.companyNameInput,
            binding.companyLocationInput,
            binding.companyRegistrationInput,
            binding.organizationInput,
            binding.jobInput,
            binding.positionInput
        )
        isEditngEnabled = enabled
        textFields.forEach {it.isEnabled = enabled}
    }

    private fun saveDate() {
        //데이터 저장 로직 추가
        enableEditing(false)
        setButtonEnabled(binding.btnSave, false)
    }


    private fun setButtonEnabled(button: View, isEnabled: Boolean) {
        button.isEnabled = isEnabled
        if (isEnabled) {
            button.setBackgroundResource(R.drawable.rounded_button_background)
            button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.text_white))
        } else {
            button.setBackgroundResource(R.drawable.rounded_button_background_disabled)
            button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.text_dark_gray))
        }
    }
}