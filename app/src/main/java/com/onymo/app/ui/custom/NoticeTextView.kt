package com.onymo.app.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.onymo.app.R

/**
 * NoticeTextView - 날짜 상태를 나타내는 텍스트 뷰
 */
class NoticeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        // 초기 상태 설정
        updateAppearance(isHoliday = false, hasEvent = false, isToday = false)
    }

    /**
     * 날짜 상태에 따라 스타일 업데이트
     * @param isHoliday 공휴일 여부
     * @param hasEvent 이벤트 여부
     * @param isToday 오늘 날짜 여부
     */
    fun updateAppearance(isHoliday: Boolean, hasEvent: Boolean, isToday: Boolean) {
        when {
            isToday -> {
                setBackgroundResource(R.drawable.rounded_notice_background_today)
                setTextColor(ContextCompat.getColor(context, R.color.colorOnSurface))
            }
            isHoliday -> {
                setBackgroundResource(R.drawable.rounded_notice_background_holiday)
                setTextColor(ContextCompat.getColor(context, R.color.colorHoliday))
            }
            hasEvent -> {
                setBackgroundResource(R.drawable.rounded_notice_background_event)
                setTextColor(ContextCompat.getColor(context, R.color.colorOnSurface))
            }
            else -> {
                setBackgroundResource(R.drawable.rounded_notice_background)
                setTextColor(ContextCompat.getColor(context, R.color.colorGray))
            }
        }
    }

    /**
     * 이벤트 데이터를 설정하고 상태에 맞는 스타일 적용
     * @param events 이벤트 리스트
     * @param isHoliday 공휴일 여부
     * @param isToday 오늘 날짜 여부
     * @param isWorkComplete 출근 완료 여부
     */
    fun setEvents(
        events: List<String>,
        isHoliday: Boolean,
        isToday: Boolean,
        isWorkComplete: Boolean = false
    ) {
        // 이벤트 정렬: 출근 완료 > 나머지 이벤트
        val sortedEvents = mutableListOf<String>()
        if (isWorkComplete) {
            sortedEvents.add(context.getString(R.string.home_work_completed_event))
        }
        sortedEvents.addAll(events)

        // 텍스트 설정
        text = if (sortedEvents.isEmpty()) {
            context.getString(R.string.home_no_events) // 등록된 일정 없음
        } else {
            sortedEvents.joinToString(separator = "\n") // 이벤트 리스트를 줄바꿈으로 연결
        }

        // 스타일 업데이트
        updateAppearance(isHoliday, events.isNotEmpty(), isToday)
    }
}
