package com.onymo.app.viewmodel

import androidx.lifecycle.ViewModel
import com.onymo.app.data.repository.HolidayRepository
import com.prolificinteractive.materialcalendarview.CalendarDay

/**
 * HomeViewModel - 홈 화면에서 사용되는 데이터 및 비즈니스 로직을 관리
 */
class HomeViewModel : ViewModel() {

    private val repository = HolidayRepository()

    /**
     * 공휴일 데이터를 가져와 CalendarDay 리스트로 반환
     * @return 공휴일 날짜 리스트
     */
    fun getHolidayDates(): List<CalendarDay> {
        return repository.getHolidays2025AsCalendarDays()
    }

    /**
     * 특정 날짜의 이벤트 목록을 가져옴
     * @param date 요청한 날짜 (yyyy-MM-dd 형식)
     * @return 해당 날짜의 이벤트 리스트
     */
    fun getEventsForDate(date: String): List<String> {
        return repository.getHolidays2025()
            .filter { it.date == date }
            .map { it.name }
    }
}
