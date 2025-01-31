package com.onymo.app.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.onymo.app.R
import com.onymo.app.data.model.Event

/**
 * HomeListAdapter - 선택된 날짜의 이벤트를 ListView에 표시하는 어댑터
 * @param context Context - 액티비티나 프래그먼트의 컨텍스트
 * @param layoutResId Int - 각 아이템의 레이아웃 리소스 ID
 * @param events List<Event> - 이벤트 리스트
 */
class HomeListAdapter(
    private val context: Context,
    @LayoutRes private val layoutResId: Int,
    private var events: List<Event>
) : android.widget.BaseAdapter() {

    /**
     * 이벤트 데이터의 개수 반환
     * @return Int - 이벤트의 총 개수
     */
    override fun getCount(): Int {
        return events.size
    }

    /**
     * 특정 위치의 이벤트 데이터 반환
     * @param position Int - 위치
     * @return Event - 이벤트 데이터
     */
    override fun getItem(position: Int): Event {
        return events[position]
    }

    /**
     * 특정 위치의 아이템 ID 반환
     * @param position Int - 위치
     * @return Long - 아이템 ID
     */
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * 각 아이템의 뷰 생성 및 반환
     * @param position Int - 위치
     * @param convertView View? - 재사용할 뷰
     * @param parent ViewGroup - 부모 뷰그룹
     * @return View - 생성된 아이템 뷰
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(layoutResId, parent, false)

        // 이벤트 데이터 가져오기
        val event = getItem(position)

        // View 설정
        val eventTitleTextView: TextView = view.findViewById(R.id.event_title)
        val eventDateTextView: TextView = view.findViewById(R.id.event_date)
        val eventTimeTextView: TextView = view.findViewById(R.id.event_time)

        // 날짜와 시간을 분리
        val dateAndTime = event.time.split(" ")
        val date = dateAndTime.getOrNull(0) ?: "" // 날짜 부분
        val time = dateAndTime.getOrNull(1)?.plus(" ${dateAndTime.getOrNull(2) ?: ""}")?.trim() ?: "" // 시간 부분

        // 데이터 바인딩
        eventTitleTextView.text = event.title
        eventDateTextView.text = "날짜: ${event.time.split(" ")[0]}" // 시간에서 날짜 부분만 사용
        eventTimeTextView.text = "시간: ${event.time}" // 시간 표시

        return view
    }

    /**
     * 이벤트 데이터 갱신
     * @param newEvents List<Event> - 새 이벤트 리스트
     */
    fun updateEvents(newEvents: List<Event>) {
        this.events = newEvents
        notifyDataSetChanged() // 데이터 변경 알림
    }
}
