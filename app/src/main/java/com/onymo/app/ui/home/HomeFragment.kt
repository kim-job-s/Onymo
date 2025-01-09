package com.onymo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.onymo.app.R
import com.onymo.app.databinding.FragmentHomeBinding
import com.onymo.app.ui.custom.NoticeTextView
import com.onymo.app.ui.home.CalendarDecorators
import com.onymo.app.ui.setting.SettingFragment
import com.onymo.app.viewmodel.HomeViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import java.text.SimpleDateFormat
import java.util.*

/**
 * HomeFragment - 메인 홈 화면 Fragment
 * 출근하기 버튼과 달력 관리 기능 포함
 */
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    // 출근 여부를 관리하는 변수
    private var isWorkStarted = false

    /**
     * onCreateView - View 객체를 생성하는 생명주기 메서드
     * @return Fragment의 root View
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Fragment의 레이아웃을 inflate하여 binding 객체를 초기화
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * onViewCreated - View가 생성된 후 호출되는 생명주기 메서드
     * UI 구성과 이벤트 리스너를 설정
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel 초기화
        viewModel = HomeViewModel()

        // UI 초기화
        setupUI()

    }

    /**
     * setupUI - 화면 초기 구성
     * 캘린더와 공지사항 초기화 및 버튼 이벤트 설정
     */
    private fun setupUI() {
        val calendarView: MaterialCalendarView = binding.materialCalendarView
        val noticeTextView: NoticeTextView = binding.selectedDateEvents
        val today = CalendarDay.today()

        // 오늘 날짜 텍스트 설정
        binding.todayDateText.text = SimpleDateFormat(
            "yyyy년 MM월 dd일 EEEE", Locale.getDefault()
        ).format(Date())

        // 오늘 날짜 데코레이터 추가
        calendarView.addDecorator(CalendarDecorators.todayDecorator(requireContext()))

        // 공휴일 데코레이터 추가
        calendarView.addDecorator(CalendarDecorators.holidayDecorator(requireContext(), viewModel.getHolidayDates()))

        // 주말 데코레이터 추가
        calendarView.addDecorator(CalendarDecorators.weekendDecorator(requireContext(), java.util.Calendar.SATURDAY))
        calendarView.addDecorator(CalendarDecorators.weekendDecorator(requireContext(), java.util.Calendar.SUNDAY))

        // 기본적으로 오늘 날짜를 선택
        calendarView.setDateSelected(today, true)
        noticeTextView.setEvents(
            viewModel.getEventsForDate(today.toString()),
            isHoliday = false,
            isToday = true
        )

        // 날짜 선택 이벤트 처리
        calendarView.setOnDateChangedListener(OnDateSelectedListener { _, date, _ ->
            // 날짜를 "yyyy-MM-dd" 형식으로 변환
            val selectedDate = String.format("%04d-%02d-%02d", date.year, date.month + 1, date.day)
            val isToday = date == today
            val events = viewModel.getEventsForDate(selectedDate) // 특정 날짜의 모든 이벤트를 가져옴
            noticeTextView.setEvents(
                events,
                isHoliday = viewModel.getHolidayDates().contains(date),
                isToday = isToday
            )
        })
        // 출근 버튼 및 설정 버튼 초기화
        setupStartWorkButton()

        // 설정 버튼 이벤트 처리
        setupSettingsButton()
    }

    /**
     * setupStartWorkButton - 출근하기 버튼 클릭 이벤트 설정
     * 출근하기 상태에 따라 버튼 동작 및 UI 업데이트
     */
    private fun setupStartWorkButton() {
        binding.startWorkButton.setOnClickListener {
            val today = CalendarDay.today()
            val todayKey = String.format("%04d-%02d-%02d", today.year, today.month + 1, today.day)

            if (!isWorkStarted) {
                // 출근 상태로 전환
                isWorkStarted = true

                // 버튼 상태 업데이트
                binding.startWorkButton.apply {
                    text = getString(R.string.home_work_started) // 텍스트를 "출근 완료"로 변경
                    isEnabled = false // 버튼 비활성화
                    setBackgroundResource(R.drawable.rounded_start_work_button_background_disabled) // 비활성화 배경 적용
                }

                // 캘린더에 출근 점 표시
                binding.materialCalendarView.addDecorator(
                    CalendarDecorators.workDecorator(requireContext(), today)
                )

                // 공지사항에 "출근 완료" 추가
                binding.selectedDateEvents.setEvents(
                    events = viewModel.getEventsForDate(todayKey), // 기존이벤트
                    isHoliday = viewModel.getHolidayDates().contains(today),
                    isToday = true,
                    isWorkComplete = true // 출근 완료 상태를 true로 설정
                )
            } else {
                // 이미 출근 완료 상태 -> 다이얼로그 표시
                showWorkAlreadyStartedDialog()
            }
        }
    }

    /**
     * showWorkAlreadyStartedDialog - 이미 출근 완료 다이얼로그 표시
     */
    private fun showWorkAlreadyStartedDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_work_already_started, null)

        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        // 다이얼로그 테두리를 둥글게 설정
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // 확인 버튼 클릭 이벤트 설정
        dialogView.findViewById<Button>(R.id.dialog_button_ok).setOnClickListener {
            dialog.dismiss()
        }

        // 다이얼로그 표시
        dialog.show()
    }

    /**
     * setupSettingsButton - 설정 버튼 동작 설정
     */
    private fun setupSettingsButton() {
        binding.topbarSetting.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SettingFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
