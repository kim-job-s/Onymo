package com.onymo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.onymo.app.R
import com.onymo.app.databinding.FragmentHomeBinding
import com.onymo.app.ui.home.HomeViewModel
import com.onymo.app.ui.custom.NoticeTextView
import com.onymo.app.ui.home.CalendarDecorators
import com.onymo.app.ui.mypage.MypageFragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    // MaterialCalendarView와 ViewModel을 연결
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = HomeViewModel() // ViewModel 직접 초기화

        val calendarView: MaterialCalendarView = binding.materialCalendarView
        val todayDateText: TextView = binding.todayDateText
        val noticeTextView: NoticeTextView = binding.selectedDateEvents

        // 오늘 날짜 표시
        val today = CalendarDay.today()
        val dateFormatter = SimpleDateFormat("yyyy년 MM월 dd일 EEEE", Locale.getDefault())
        todayDateText.text = dateFormatter.format(Date())

        // 기본적으로 오늘 날짜를 선택
        calendarView.setDateSelected(today, true)
        noticeTextView.text = "${today.year}-${today.month}-${today.day}의 이벤트를 표시합니다."

        // 공휴일 가져오기
        val holidays = viewModel.getHolidayDates()
        calendarView.addDecorator(CalendarDecorators.holidayDecorator(requireContext(), holidays))

        // 오늘 날짜 데코레이터
        calendarView.addDecorator(CalendarDecorators.todayDecorator(requireContext()))

        // 주말 데코레이터
        calendarView.addDecorator(CalendarDecorators.weekendDecorator(requireContext(), java.util.Calendar.SATURDAY))
        calendarView.addDecorator(CalendarDecorators.weekendDecorator(requireContext(), java.util.Calendar.SUNDAY))

        // 날짜 선택 이벤트 처리
        calendarView.setOnDateChangedListener(OnDateSelectedListener { _, date, _ ->
            // 날짜를 "yyyy-MM-dd" 형식으로 변환
            val selectedDate = String.format("%04d-%02d-%02d", date.year, date.month + 1, date.day)
            val events = viewModel.getEventsForDate(selectedDate) // 특정 날짜의 모든 이벤트를 가져옴

            if (events.isNotEmpty()) {
                    noticeTextView.text = "[$selectedDate]: $events"
                noticeTextView.setEvents(events, isHoliday = holidays.contains(CalendarDay.from(date.year, date.month, date.day)))
            } else {
                    noticeTextView.text = "[$selectedDate]: 등록된 일정이 없습니다."
                    noticeTextView.setEvents(emptyList(), isHoliday = false)
            }
        })

        //마이페이지 버튼 클릭 이벤트 설정
        val myPageButton: ImageButton = view.findViewById(R.id.topbar_my_page)
        myPageButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MypageFragment())
                .addToBackStack(null) // 뒤로가기 지원
                .commit()
        }

    }
}
