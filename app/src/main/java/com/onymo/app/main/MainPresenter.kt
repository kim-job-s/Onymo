package com.onymo.app.main

import com.onymo.app.R
import com.onymo.app.review.ReviewFragment

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {
    override fun onStart() {
        // 초기 화면: HomeFragment
        //view.showFragment(HomeFragment())
        view.updateToolbarTitle("Home")
    }

    override fun onBottomNavigationItemSelected(itemId: Int) {
        when (itemId) {
            R.id.bottom_navigation_home -> {
                //view.showFragment(HomeFragment())
                view.updateToolbarTitle("Home")
            }
            R.id.bottom_navigation_review -> {
                view.showFragment(ReviewFragment())
                view.updateToolbarTitle("Review")
            }
            // 추가 메뉴 처리 가능
        }
    }
}