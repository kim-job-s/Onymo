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
 * CalendarDecorators - MaterialCalendarView의 데코레이터 관리
 */
object CalendarDecorators {

    /**
     * 공휴일 데코레이터
     * @param context Context
     * @param dates 공휴일 날짜 리스트
     */
    fun holidayDecorator(context: Context, dates: List<CalendarDay>?): DayViewDecorator {
        val color = ContextCompat.getColor(context, R.color.colorHoliday)
        return object : DayViewDecorator {
            override fun shouldDecorate(day: CalendarDay): Boolean {
                return dates?.contains(day) == true
            }

            override fun decorate(view: DayViewFacade) {
                view.addSpan(ForegroundColorSpan(color))
            }
        }
    }

    /**
     * 오늘 날짜 데코레이터
     * @param context Context
     */
    fun todayDecorator(context: Context): DayViewDecorator {
        val drawable = ContextCompat.getDrawable(context, R.drawable.calendar_circle_today)
        return object : DayViewDecorator {
            private val today = CalendarDay.today()

            override fun shouldDecorate(day: CalendarDay): Boolean = day == today

            override fun decorate(view: DayViewFacade) {
                drawable?.let { view.setBackgroundDrawable(it) }
            }
        }
    }

    /**
     * 주말 데코레이터
     * @param context Context
     * @param dayOfWeek 요일 (토요일, 일요일)
     */
    fun weekendDecorator(context: Context, dayOfWeek: Int): DayViewDecorator {
        val color = when (dayOfWeek) {
            java.util.Calendar.SATURDAY -> ContextCompat.getColor(context, R.color.colorSaturday)
            java.util.Calendar.SUNDAY -> ContextCompat.getColor(context, R.color.colorSunday)
            else -> throw IllegalArgumentException("Invalid dayOfWeek: $dayOfWeek")
        }

        return object : DayViewDecorator {
            override fun shouldDecorate(day: CalendarDay): Boolean {
                val calendar = java.util.Calendar.getInstance()
                calendar.set(day.year, day.month, day.day) // CalendarDay의 month는 1부터 시작
                return calendar.get(java.util.Calendar.DAY_OF_WEEK) == dayOfWeek
            }

            override fun decorate(view: DayViewFacade) {
                view.addSpan(ForegroundColorSpan(color))
            }
        }
    }

    /**
     * 출근 완료 점 데코레이터
     * @param context Context
     */
    fun workDecorator(context: Context, workDay: CalendarDay): DayViewDecorator {
     return object : DayViewDecorator {
         override fun shouldDecorate(day: CalendarDay?): Boolean = day == workDay

         override fun decorate(view: DayViewFacade) {
             view.addSpan(DotSpan(10f, ContextCompat.getColor(context, R.color.colorPrimary)))
         }
     }
    }
}
