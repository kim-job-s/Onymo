package com.onymo.app.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.onymo.app.R

class NoticeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        // 초기화는 "등록된 일정 없음" 상태로 설정
        updateAppearance(isHoliday = false, hasEvent = false)
        // 기본 패딩 설정
        //setPadding(48, 19, 48, 19)
    }

    /**
     * 공휴일, 일반 일정, 등록된 일정 없음에 따라 스타일을 업데이트
     * @param isHoliday 공휴일 여부
     * @param hasEvent 등록된 일정 여부
     */
    fun updateAppearance(isHoliday: Boolean, hasEvent: Boolean) {
        when {
            isHoliday -> {
                // 공휴일 스타일
                setBackgroundResource(R.drawable.notice_background_holiday)
                setTextColor(ContextCompat.getColor(context, R.color.colorHoliday)) // 붉은 텍스트
            }
            hasEvent -> {
                // 일반 일정 스타일
                setBackgroundResource(R.drawable.notice_background_event)
                setTextColor(ContextCompat.getColor(context, R.color.colorOnSurface)) // 검은 텍스트
            }
            else -> {
                // 등록된 일정 없음 스타일
                setBackgroundResource(R.drawable.notice_background)
                setTextColor(ContextCompat.getColor(context, R.color.colorGray)) // 회색 텍스트
            }
        }
    }

    /**
     * 여러 이벤트를 텍스트로 설정
     * @param events 이벤트 목록
     */
    fun setEvents(events: List<String>, isHoliday: Boolean) {
        if (events.isEmpty()) {
            text = context.getString(R.string.home_no_events) // 등록된 일정 없음
            updateAppearance(isHoliday = false, hasEvent = false)
        } else {
            text = events.joinToString (separator = "\n") // 여러 이벤트를 줄바꿈으로 표시
            updateAppearance(isHoliday = isHoliday, hasEvent = true)
        }
    }
}
