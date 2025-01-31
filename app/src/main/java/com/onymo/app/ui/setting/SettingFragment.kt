package com.onymo.app.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.onymo.app.R
import com.onymo.app.ui.login.LoginFragment
import com.onymo.app.ui.setting.hrinfo.HrInfoFragment

class SettingFragment : Fragment() {

    private lateinit var companyNameTextView: TextView
    private lateinit var logoutButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 닫기 버튼 클릭 이벤트 설정
        val closeButton: ImageButton = view.findViewById(R.id.close_btn)
        
        closeButton.setOnClickListener {
            parentFragmentManager.popBackStack() // 프래그먼트 스택에서 제거하여 이전 화면으로 돌아감
        }

        // 로그인 및 회원가입 버튼 클릭 이벤트
        val loginRegisterButton: View = view.findViewById(R.id.login_register)
        loginRegisterButton.setOnClickListener {
            topbarToLoginFragment()
        }

        val hrInfoButton: View = view.findViewById(R.id.hr_info)
        hrInfoButton.setOnClickListener {
            topBarToHrInfoFragment()
        }
    }

    private fun topbarToLoginFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LoginFragment())
            .addToBackStack(null) //  뒤로가기 지원
            .commit()
    }

    private fun topBarToHrInfoFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HrInfoFragment())
            .addToBackStack(null) //  뒤로가기 지원
            .commit()
    }
}