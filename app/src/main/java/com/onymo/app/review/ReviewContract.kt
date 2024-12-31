package com.onymo.app.review

import androidx.fragment.app.Fragment

interface ReviewContract {
    interface View {
        fun showTabContent(fragment: Fragment)
    }

    interface Presenter {
        fun onTabSelected(position: Int)
    }
}