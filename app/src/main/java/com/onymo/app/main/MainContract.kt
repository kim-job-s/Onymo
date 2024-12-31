package com.onymo.app.main

import androidx.fragment.app.Fragment

interface MainContract {
    interface View {
        fun showFragment(fragment: Fragment)
        fun updateToolbarTitle(title: String)
    }

    interface Presenter {
        fun onStart()
        fun onBottomNavigationItemSelected(itemId: Int)
    }
}