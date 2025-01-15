package com.onymo.app.data.repository

import com.onymo.app.data.model.Holiday
import com.prolificinteractive.materialcalendarview.CalendarDay
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

// 공휴일 데이터를 관리하는 Repository
class HolidayRepository {
    // 2025년 공휴일 데이터 반환
    fun getHolidays2025(): List<Holiday> {
        return listOf(
            Holiday("2025-01-01", "신정"),
            Holiday("2025-01-15", "개인휴일"),
            Holiday("2025-01-28", "설날 전 연휴"),
            Holiday("2025-01-29", "설날"),
            Holiday("2025-01-30", "설날 후 연휴"),
            Holiday("2025-03-01", "삼일절(3.1절)"),
            Holiday("2025-05-01", "근로자의 날"),
            Holiday("2025-05-05", "어린이날"),
            Holiday("2025-05-06", "대체공휴일"),
            Holiday("2025-06-06", "현충일"),
            Holiday("2025-08-15", "광복절"),
            Holiday("2025-10-03", "개천절"),
            Holiday("2025-10-05", "추석 전 연휴"),
            Holiday("2025-10-06", "추석"),
            Holiday("2025-10-07", "추석 후 연휴"),
            Holiday("2025-10-08", "대체공휴일"),
            Holiday("2025-10-09", "한글날"),
            Holiday("2025-12-25", "성탄절")
        )
    }

    // 2025년 공휴일을 CalendarDay로 변환
    fun getHolidays2025AsCalendarDays(): List<CalendarDay> {
        val formatter = DateTimeFormatter.ISO_DATE
        return getHolidays2025().map { holiday ->
            val localDate = LocalDate.parse(holiday.date, formatter)
            CalendarDay.from(localDate.year, localDate.monthValue-1, localDate.dayOfMonth)
        }
    }

}
