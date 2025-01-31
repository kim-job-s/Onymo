package com.onymo.app.data.repository

import com.onymo.app.data.model.Event
import com.onymo.app.data.model.Holiday
import com.prolificinteractive.materialcalendarview.CalendarDay
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

// 공휴일 및 이벤트 데이터를 관리하는 Repository
class HomeRepository {
    // 2025년 공휴일 데이터 반환
    fun getHolidays2025(): List<Holiday> {
        return listOf(
            Holiday(
                "2025-01-01",
                "신정",
                events = listOf(
                    Event("새해 첫날 기념 행사", "10:00"),
                    Event("가족 모임", "18:00")
                )
            ),
            Holiday("2025-01-15", "개인휴일"),
            Holiday(
                "2025-01-28",
                "설날 전 연휴",
                events = listOf(Event("차례 준비", "14:00"))
            ),
            Holiday("2025-01-29", "설날", events = listOf(Event("차례 지내기", "09:00"))),
            Holiday("2025-01-30", "설날 후 연휴"),
            Holiday("2025-03-01", "삼일절", events = listOf(Event("독립 기념 행사", "11:00"))),
            Holiday("2025-05-01", "근로자의 날"),
            Holiday("2025-05-05", "어린이날", events = listOf(Event("어린이날 놀이공원 방문", "09:00"))),
            Holiday("2025-05-06", "대체공휴일"),
            Holiday("2025-06-06", "현충일", events = listOf(Event("추모 행사", "10:00"))),
            Holiday("2025-08-15", "광복절", events = listOf(Event("광복 기념 행사", "15:00"))),
            Holiday("2025-10-03", "개천절"),
            Holiday("2025-10-05", "추석 전 연휴"),
            Holiday("2025-10-06", "추석"),
            Holiday("2025-10-07", "추석 후 연휴"),
            Holiday("2025-10-08", "대체공휴일"),
            Holiday("2025-10-09", "한글날"),
            Holiday("2025-12-25", "성탄절", events = listOf(Event("크리스마스 미사", "00:00"), Event("가족 파티", "19:00")))
        )
    }

    // 2025년 공휴일을 CalendarDay로 변환
    fun getHolidays2025AsCalendarDays(): List<CalendarDay> {
        val formatter = DateTimeFormatter.ISO_DATE
        return getHolidays2025().map { holiday ->
            val localDate = LocalDate.parse(holiday.date, formatter)
            CalendarDay.from(localDate.year, localDate.monthValue - 1, localDate.dayOfMonth)
        }
    }

    // 특정 날짜의 이벤트를 반환
    fun getEventsForDate(date: String): List<Event> {
        return getHolidays2025().find { it.date == date }?.events ?: emptyList()
    }
}