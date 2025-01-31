package com.onymo.app.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.onymo.app.R
import com.onymo.app.R.*

class ReviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout.fragment_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    val tabLayout : TabLayout = view.findViewById(R.id.tab_layout)

        //초기 화면 설정 (첫 번째 탭)
        loadFragment(ReviewProgressFragment())

        //탭 선택 리스너
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: Tab?) {
                when (tab?.position) {
                    0 -> loadFragment(ReviewProgressFragment()) // 첫 번째 탭: 진행 중
                    1 -> loadFragment(ReviewStatusFragment()) // 두 번째 탭: 현황
                    2 -> loadFragment(ReviewConfirmFragment()) // 세 번째 탭: 확정
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // 탭이 선택 해제될 때의 동작 (필요 시 구현)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // 이미 선택된 탭이 다시 선택될 때의 동작 (필요 시 구현)
            }

        })
    }

    private fun loadFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.tab_content, fragment)
            .commit()
    }
}