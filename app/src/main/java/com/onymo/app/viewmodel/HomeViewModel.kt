package com.onymo.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onymo.app.data.model.Event
import com.onymo.app.data.repository.HomeRepository
import com.prolificinteractive.materialcalendarview.CalendarDay

/**
 * HomeViewModel - 홈 화면에서 사용되는 데이터 및 비즈니스 로직을 관리
 */
class HomeViewModel : ViewModel() {

    // Repository 초기화
    private val repository = HomeRepository()

    // LiveData로 공휴일 리스트 제공
    private val _holidayDates = MutableLiveData<List<CalendarDay>>()
    val holidayDates: LiveData<List<CalendarDay>> get() = _holidayDates

    // LiveData로 특정 날짜의 이벤트 리스트 제공
    private val _eventsForDate = MutableLiveData<List<Event>>()
    val eventsForDate: LiveData<List<Event>> get() = _eventsForDate

    init {
        // 초기 데이터 로드
        loadHolidayDates()
    }

    /**
     * 공휴일 데이터를 가져와 LiveData에 설정
     */
    private fun loadHolidayDates() {
        _holidayDates.value = repository.getHolidays2025AsCalendarDays()
    }

    /**
     * 특정 날짜의 이벤트 목록을 LiveData에 설정
     * @param date 요청한 날짜 (yyyy-MM-dd 형식)
     */
    fun loadEventsForDate(date: String) {
        val events = repository.getEventsForDate(date)
        _eventsForDate.value = events
    }

    /**
     * 공휴일 이름 리스트를 반환
     * @param date 요청한 날짜 (yyyy-MM-dd 형식)
     * @return 해당 날짜의 공휴일 이름 리스트
     */
    fun getHolidayNamesForDate(date: String): List<String> {
        return repository.getHolidays2025()
            .filter { it.date == date }
            .map { it.name }
    }
}
