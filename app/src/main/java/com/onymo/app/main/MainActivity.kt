package com.onymo.app.main

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.onymo.app.R

class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Presenter 초기화
        presenter = MainPresenter(this)

        // 초기 화면 설정 (HomeFragment)
        presenter.onStart()

        // Bottom Navigation 이벤트 처리
        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_navigation_home -> {
                    presenter.onBottomNavigationItemSelected(R.id.bottom_navigation_home)
                    true
                }
                R.id.bottom_navigation_review -> {
                    presenter.onBottomNavigationItemSelected(R.id.bottom_navigation_review)
                    true
                }
                else -> false
            }
        }

    }

    // View 역할: Fragment 전환
    override fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    // View 역할: Toolbar 제목 업데이트
    override fun updateToolbarTitle(title: String) {
        findViewById<TextView>(R.id.toolbar_title)?.text = title
    }
}
