package com.onymo.app.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.onymo.app.R
import com.onymo.app.databinding.FragmentLoginBinding
import com.onymo.app.ui.setting.SettingFragment

/**
 * LoginFragment - 로그인 화면을 담당하는 프래그먼트
 * 사용자가 이메일과 비밀번호를 입력하고 로그인할 수 있도록 기능을 제공합니다.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 이메일 및 비밀번호 입력 필드 변경 감지
        binding.loginEditEmail.addTextChangedListener(textWatcher)
        binding.loginEditPw.addTextChangedListener(textWatcher)

        // 로그인 버튼 클릭 이벤트 처리
        binding.loginBtn.setOnClickListener {
            handleLogin()
        }

        // 닫기 버튼 클릭 이벤트 처리 (이전 화면으로 이동)
        binding.closeBtn.setOnClickListener {
            navigateToMypage()
        }

        // 자동 로그인 체크박스 이벤트 처리
        binding.loginCheckboxAutoLogin.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) {
                "자동 로그인이 설정되었습니다."
            } else {
                "자동 로그인이 해제되었습니다."
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        // 회원가입 버튼 클릭 이벤트 처리
        binding.loginBtnJoin.setOnClickListener {
            Toast.makeText(requireContext(), "아직 회원가입이 서비스 점검 중입니다.", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 이메일 및 비밀번호 입력 감지하여 로그인 버튼 활성화 여부 결정
     */
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val email = binding.loginEditEmail.text.toString().trim()
            val password = binding.loginEditPw.text.toString().trim()
            toggleLoginButton(email.isNotEmpty() && password.isNotEmpty())
        }
        override fun afterTextChanged(s: Editable?) {}
    }

    /**
     * 로그인 버튼 활성화/비활성화 설정
     * @param isEnabled 버튼 활성화 여부
     */
    private fun toggleLoginButton(isEnabled: Boolean) {
        binding.loginBtn.isEnabled = isEnabled
        binding.loginBtn.background = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.btn_bg_login_selector // 활성화 상태 배경
        )
    }

    /**
     * 로그인 버튼 클릭 시 로그인 수행
     * 유효한 이메일과 비밀번호가 입력되었는지 확인하고, 메시지를 표시합니다.
     */
    private fun handleLogin() {
        val email = binding.loginEditEmail.text.toString()
        val password = binding.loginEditPw.text.toString()

        if (isValidEmail(email) && password.isNotEmpty()) {
            // 로그인 성공 메시지 표시
            Toast.makeText(requireContext(), "로그인이 되었습니다.", Toast.LENGTH_SHORT).show()
        } else {
            // 로그인 실패 메시지 표시
            Toast.makeText(requireContext(), "이메일 또는 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 이메일 형식이 올바른지 검증하는 함수
     * @param email 입력된 이메일 주소
     * @return 이메일 형식이 올바르면 true, 그렇지 않으면 false 반환
     */
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * 마이페이지(설정 페이지)로 이동하는 함수
     */
    private fun navigateToMypage() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SettingFragment()) // 마이페이지 프래그먼트로 교체
            .addToBackStack(null) // 뒤로가기 지원
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 누수 방지
    }
}
