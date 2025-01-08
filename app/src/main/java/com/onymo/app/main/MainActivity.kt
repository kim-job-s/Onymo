package com.onymo.app.main;

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.onymo.app.R
import com.onymo.app.ui.review.ReviewFragment
import com.onymo.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // 초기 화면 설정
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        // 바텀 네비게이션 아이템 클릭 리스너
        bottomNavigationView.setOnItemSelectedListener { menuItem ->

            // 모든 아이콘을 기본 아이콘으로 리셋
            resetBottomNavigationTcons(bottomNavigationView)

            //선택된 아이템의 아이콘 변경 프래그먼트 전환
            when (menuItem.itemId) {
                R.id.bottom_navigation_home -> {
                    menuItem.setIcon(R.drawable.ic_bottom_navigation_fill_home_24px)
                    switchFragment(HomeFragment())
                }
                R.id.bottom_navigation_review -> {
                    menuItem.setIcon(R.drawable.ic_bottom_navigation_fill_thumb_up_24px)
                    switchFragment(ReviewFragment())
                }
            }
            true
        }
    }
    /**
     * 모든 바텀 네비게이션 아이콘을 기본 상태로 리셋
     */
    private fun resetBottomNavigationTcons(bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.menu.findItem(R.id.bottom_navigation_home)
            .setIcon(R.drawable.ic_bottom_navigation_home_24px)
        bottomNavigationView.menu.findItem(R.id.bottom_navigation_review)
            .setIcon(R.drawable.ic_bottom_navigation_thumb_up_24px)
        bottomNavigationView.menu.findItem(R.id.bottom_navigation_edit)
            .setIcon(R.drawable.ic_bottom_navigation_edit_square_24px)
        bottomNavigationView.menu.findItem(R.id.bottom_navigation_group)
            .setIcon(R.drawable.ic_bottom_navigation_group_24px)
        bottomNavigationView.menu.findItem(R.id.bottom_navigation_view)
            .setIcon(R.drawable.ic_bottom_navigation_description_24px)
    }

    /**
     * Fragment를 전환하는 함수
     */
    private fun switchFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
