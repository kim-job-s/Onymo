package com.onymo.app.ui.home

import android.content.Context
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.onymo.app.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan

/**
 * HomeCalendarDecorators - MaterialCalendarView의 데코레이터 관리 클래스
 * 날짜에 특정 스타일을 적용하여 사용자 경험을 향상시킴
 */
object HomeCalendarDecorators {

    /**
     * 공휴일 데코레이터
     * 특정 날짜 리스트를 공휴일 색상으로 표시
     * @param context Context - 색상을 가져오는 데 사용
     * @param dates List<CalendarDay>? - 공휴일 날짜 리스트
     */
    fun holidayDecorator(context: Context, dates: List<CalendarDay>?): DayViewDecorator {
        val holidayColor = ContextCompat.getColor(context, R.color.color_calendar_holiday)
        return createSpanDecorator(dates, ForegroundColorSpan(holidayColor))
    }

    /**
     * 오늘 날짜 데코레이터
     * 오늘 날짜를 둥근 배경으로 강조
     * @param context Context - 배경 drawable을 가져오는 데 사용
     */
    fun todayDecorator(context: Context): DayViewDecorator {
        val todayDrawable = ContextCompat.getDrawable(context, R.drawable.bg_calendar_circle_today_date)
        val today = CalendarDay.today()
        return object : DayViewDecorator {
            override fun shouldDecorate(day: CalendarDay): Boolean = day == today

            override fun decorate(view: DayViewFacade) {
                todayDrawable?.let { view.setBackgroundDrawable(it) }
            }
        }
    }

    /**
     * 주말 데코레이터
     * 토요일과 일요일에 특정 색상을 적용
     * @param context Context - 색상을 가져오는 데 사용
     * @param dayOfWeek Int - 요일 값 (Calendar.SATURDAY 또는 Calendar.SUNDAY)
     */
    fun weekendDecorator(context: Context, dayOfWeek: Int): DayViewDecorator {
        val weekendColor = ContextCompat.getColor(context, R.color.color_calendar_weekend)
        return object : DayViewDecorator {
            override fun shouldDecorate(day: CalendarDay): Boolean {
                val calendar = java.util.Calendar.getInstance()
                calendar.set(day.year, day.month, day.day)
                return calendar.get(java.util.Calendar.DAY_OF_WEEK) == dayOfWeek
            }

            override fun decorate(view: DayViewFacade) {
                view.addSpan(ForegroundColorSpan(weekendColor))
            }
        }
    }

    /**
     * 출근 완료 점 데코레이터
     * 특정 날짜에 점을 추가하여 출근 완료 표시
     * @param context Context - 점 색상을 가져오는 데 사용
     * @param workDay CalendarDay - 출근 완료 날짜
     */
    fun workDecorator(context: Context, workDay: CalendarDay): DayViewDecorator {
        val workDotColor = ContextCompat.getColor(context, R.color.color_primary)
        return createDotDecorator(workDay, workDotColor)
    }

    /**
     * Span 기반 데코레이터 생성
     * @param dates List<CalendarDay>? - 스타일을 적용할 날짜 리스트
     * @param span Any - 적용할 span (ForegroundColorSpan 등)
     * @return DayViewDecorator
     */
    private fun createSpanDecorator(dates: List<CalendarDay>?, span: Any): DayViewDecorator {
        return object : DayViewDecorator {
            override fun shouldDecorate(day: CalendarDay): Boolean {
                return dates?.contains(day) == true
            }

            override fun decorate(view: DayViewFacade) {
                view.addSpan(span)
            }
        }
    }

    /**
     * Dot 기반 데코레이터 생성
     * @param day CalendarDay - 점을 표시할 날짜
     * @param color Int - 점 색상
     * @return DayViewDecorator
     */
    private fun createDotDecorator(day: CalendarDay, color: Int): DayViewDecorator {
        return object : DayViewDecorator {
            override fun shouldDecorate(dayToDecorate: CalendarDay?): Boolean = dayToDecorate == day

            override fun decorate(view: DayViewFacade) {
                view.addSpan(DotSpan(10f, color)) // 반지름 10f의 점 추가
            }
        }
    }
}
