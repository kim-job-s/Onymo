package com.onymo.app.review

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.onymo.app.R

class ReviewFragment : Fragment(R.layout.fragment_review), ReviewContract.View{
    private lateinit var presenter: ReviewPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Toolbar 제목 변경
        activity?.findViewById<TextView>(R.id.toolbar_title)?.text = "평가"

        // Presenter 초기화
        presenter = ReviewPresenter(this)

        // TbaLayout 이벤트 처리
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let { presenter.onTabSelected(it.position) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

    }

    override fun showTabContent(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.tab_content, fragment)
            .commit()
    }

}