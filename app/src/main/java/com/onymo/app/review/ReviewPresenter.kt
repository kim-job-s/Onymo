package com.onymo.app.review

class ReviewPresenter(private val view: ReviewContract.View) : ReviewContract.Presenter {
    override fun onTabSelected(position: Int) {
        when (position) {
            0 -> view.showTabContent(ReviewProgressFragment())
            1 -> view.showTabContent(ReviewStatusFragment())
            2 -> view.showTabContent(ReviewConfirmFragment())
        }
    }
}