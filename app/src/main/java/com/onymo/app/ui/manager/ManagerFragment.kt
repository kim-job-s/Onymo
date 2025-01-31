package com.onymo.app.ui.manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.onymo.app.R
import com.onymo.app.databinding.FragmentManagerBinding
import com.onymo.app.ui.manager.fragments.ManagerQuestionnaireFragment
import com.onymo.app.ui.managerjob.ManagerJobFragment

/**
 * ManagerFragment - TabLayout을 사용하여 문항지, 직무, 척도 탭을 관리하는 프래그먼트
 */
class ManagerFragment : Fragment() {

    // View Binding 객체 초기화
    private lateinit var binding: FragmentManagerBinding


    /**
     * onCreateView - 프래그먼트의 레이아웃을 초기화하고 뷰를 반환
     *
     * @param inflater 레이아웃 XML을 inflate하는 데 사용
     * @param container 프래그먼트의 부모 뷰
     * @param savedInstanceState 이전 상태 복원을 위한 Bundle
     * @return 프래그먼트의 루트 뷰
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManagerBinding.inflate(inflater, container, false) // View Binding 초기화
        return binding.root
    }

    /**
     * onViewCreated - 뷰가 생성된 이후 호출되며, UI 초기화와 이벤트 설정 담당
     *
     * @param view 생성된 프래그먼트의 루트 뷰
     * @param savedInstanceState 이전 상태 복원을 위한 Bundle
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabLayout() // TabLayout 초기화 및 설정
    }

    /**
     * setupTabLayout - TabLayout을 설정하고 탭 선택 이벤트 처리
     */
    private fun setupTabLayout() {
        // 초기 탭을 문항지 프래그먼트로 설정
        replaceFragment(ManagerQuestionnaireFragment())

        // TabLayout에 리스너 추가
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            /**
             * onTabSelected - 탭이 선택되었을 때 호출
             *
             * @param tab 선택된 Tab 객체
             */
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> replaceFragment(ManagerQuestionnaireFragment()) // 문항지 탭 선택 시
                    1 -> replaceFragment(ManagerJobFragment()) // 직무 탭 선택 시
                    2 -> replaceFragment(ManagerMeasureFragment()) // 척도 탭 선택 시
                }
            }

            /**
             * onTabUnselected - 탭 선택이 해제되었을 때 호출
             *
             * @param tab 선택 해제된 Tab 객체
             */
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // 선택 해제 시 별도 처리 필요 시 구현
            }

            /**
             * onTabReselected - 이미 선택된 탭을 다시 선택했을 때 호출
             *
             * @param tab 다시 선택된 Tab 객체
             */
            override fun onTabReselected(tab: TabLayout.Tab?) {
                // 재선택 시 별도 처리 필요 시 구현
            }
        })
    }

    /**
     * replaceFragment - 선택된 탭에 따라 프래그먼트를 교체
     *
     * @param fragment 표시할 프래그먼트 객체
     */
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = parentFragmentManager // 부모 FragmentManager 가져오기
        val transaction: FragmentTransaction = fragmentManager.beginTransaction() // 트랜잭션 시작
        transaction.replace(R.id.tab_content, fragment) // 프래그먼트를 container에 교체
        transaction.commit() // 트랜잭션 커밋
    }
}