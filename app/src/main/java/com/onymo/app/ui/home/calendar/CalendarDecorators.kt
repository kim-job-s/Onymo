package com.onymo.app.ui.home

import android.content.Context
import android.icu.util.Calendar
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.onymo.app.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

object CalendarDecorators {

    // 공휴일 데코레이터
    fun holidayDecorator(context: Context, dates: List<CalendarDay>?): DayViewDecorator {
        val color = ContextCompat.getColor(context, R.color.colorHoliday) // 색상 정의
        return object : DayViewDecorator {
            override fun shouldDecorate(day: CalendarDay): Boolean {
                return dates?.contains(day) == true
            }

            override fun decorate(view: DayViewFacade) {
                view.addSpan(ForegroundColorSpan(color)) // 텍스트 색상 변경
                view.addSpan(android.text.style.StyleSpan(android.graphics.Typeface.BOLD)) // 굵은 글씨체 추가
            }
        }
    }

    // 오늘 날짜 데코레이터
    fun todayDecorator(context: Context): DayViewDecorator {
        return object : DayViewDecorator {
            private val today = CalendarDay.today()
            private val backgroundDrawable =
                ContextCompat.getDrawable(context, R.drawable.calendar_circle_today)

            override fun shouldDecorate(day: CalendarDay): Boolean = day == today

            override fun decorate(view: DayViewFacade) {
                backgroundDrawable?.let {
                    view.setBackgroundDrawable(it)
                }
            }
        }
    }


    // 주말 데코레이터
    fun weekendDecorator(context: Context, dayOfWeek: Int): DayViewDecorator {
        val color = when (dayOfWeek) {
            Calendar.SATURDAY -> ContextCompat.getColor(context, R.color.colorSaturday)
            Calendar.SUNDAY -> ContextCompat.getColor(context, R.color.colorSunday)
            else -> throw IllegalArgumentException("잘못된 요일을 입력하였습니다: $dayOfWeek")
        }

        return object : DayViewDecorator {
            override fun shouldDecorate(day: CalendarDay): Boolean {
                val calendar = java.util.Calendar.getInstance()

                // CalendarDay의 month는 1부터 시작하므로 그대로 사용
                calendar.set(day.year, day.month, day.day)

                // 요일 비교
                return calendar.get(java.util.Calendar.DAY_OF_WEEK) == dayOfWeek
            }

            override fun decorate(view: DayViewFacade) {
                view.addSpan(ForegroundColorSpan(color))
            }
        }
    }
}
