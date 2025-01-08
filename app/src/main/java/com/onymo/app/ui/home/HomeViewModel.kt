package com.onymo.app.ui.home

import androidx.lifecycle.ViewModel
import com.onymo.app.repository.HolidayRepository
import com.prolificinteractive.materialcalendarview.CalendarDay

// Home 화면에 필요한 데이터를 관리하는 ViewModel
class HomeViewModel : ViewModel() {

    private val repository = HolidayRepository()

    // 2025년 공휴일을 CalendarDay로 변환하여 반환
    fun getHolidayDates(): List<CalendarDay> {
        return repository.getHolidays2025AsCalendarDays()
    }

    fun getEventsForDate(date: String): List<String> {
        return repository.getHolidays2025()
            .filter { it.date == date } // 특정 날짜와 일치하는 모든 이벤트 가져오기
            .map { it.name } // 이벤트 이름 리스트로 변환
    }
}
