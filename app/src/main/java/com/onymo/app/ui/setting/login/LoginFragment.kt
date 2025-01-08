package com.onymo.app.ui.setting.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.onymo.app.R
import com.onymo.app.databinding.FragmentLoginBinding
import com.onymo.app.repository.LoginRepository
import com.onymo.app.ui.setting.SettingFragment
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel

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

        viewModel = LoginViewModel(LoginRepository())

        // 로그인 버튼 클릭 이벤트
        binding.loginBtn.setOnClickListener {
            val email = binding.loginEditEmail.text.toString()
            val password = binding.loginEditPassword.text.toString()
            viewModel.login(email, password)
        }

        // 로그인 결과 관찰
        observeLoginResult()

        // 닫기 버튼 클릭 이벤트
        binding.topbarCloseButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun observeLoginResult() {
        lifecycleScope.launch {
            viewModel.loginResult.collect { result ->
                when (result) {
                    true -> {
                        Toast.makeText(context, "로그인 성공!", Toast.LENGTH_SHORT).show()
                        navigateToMypage() // 마이페이지로 이동
                    }
                    false -> {
                        Toast.makeText(context, "로그인 실패! 이메일 또는 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        // 초기 상태 처리 (로그인 결과 아직 없음)
                    }
                }
            }
        }
    }

    private fun navigateToMypage() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SettingFragment())
            .addToBackStack(null) // 뒤로가기 지원
            .commit()
    }
}