package com.onymo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.onymo.app.R
import com.onymo.app.data.model.Event
import com.onymo.app.databinding.FragmentHomeBinding
import com.onymo.app.ui.home.HomeCalendarDecorators
import com.onymo.app.ui.home.HomeListAdapter
import com.onymo.app.ui.setting.SettingFragment
import com.onymo.app.viewmodel.HomeViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.text.SimpleDateFormat
import java.util.*

/**
 * HomeFragment - 메인 홈 화면 Fragment
 * 캘린더, 출근 버튼, 설정 화면 이동 기능을 포함합니다.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!! // Null Safety를 위한 바인딩
    private lateinit var viewModel: HomeViewModel

    private var isWorkStarted = false // 출근 완료 상태를 저장하는 변수

    /**
     * onCreateView - 프래그먼트의 레이아웃을 인플레이트하고 View 객체를 반환
     * @return Fragment의 root View
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Fragment의 XML 레이아웃과 바인딩 객체 연결
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * onViewCreated - View가 생성된 후 호출됩니다.
     * 주로 UI 초기화 및 이벤트 리스너를 설정합니다.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel 초기화
        viewModel = HomeViewModel()

        // UI 설정
        setupUI()
    }

    /**
     * setupUI - 화면 초기 구성 및 각종 UI 요소 설정
     * 캘린더 데코레이터와 날짜 선택 이벤트 설정을 포함합니다.
     */
    private fun setupUI() {
        val calendarView: MaterialCalendarView = binding.homeCalendarView
        val today = CalendarDay.today()

        // 오늘 날짜 텍스트 설정
        binding.homeTodayDate.text = SimpleDateFormat(
            "yyyy년 MM월 dd일 EEEE", Locale.getDefault()
        ).format(Date())

        // 캘린더 데코레이터 설정
        setupCalendarDecorators(calendarView)

        // 오늘 날짜 선택
        calendarView.setDateSelected(today, true)

        // 오늘 날짜의 이벤트 리스트 초기화
        updateEventList(today)

        // 날짜 선택 이벤트 설정
        calendarView.setOnDateChangedListener { _, date, _ ->
            updateEventList(date)
            // 출근 버튼 상태 업데이트
            binding.homeStartWorkBtn.isEnabled = date == today && !isWorkStarted
        }

        // 출근 버튼 설정
        setupStartWorkButton()

        // 설정 버튼 설정
        setupSettingsButton()
    }

    /**
     * setupCalendarDecorators - 캘린더에 데코레이터 추가
     * @param calendarView MaterialCalendarView
     */
    private fun setupCalendarDecorators(calendarView: MaterialCalendarView) {
        // 오늘 날짜 데코레이터
        calendarView.addDecorator(HomeCalendarDecorators.todayDecorator(requireContext()))

        // LiveData를 관찰하여 공휴일 데코레이터 추가
        viewModel.holidayDates.observe(viewLifecycleOwner) { holidayDates ->
            calendarView.addDecorator(
                HomeCalendarDecorators.holidayDecorator(requireContext(), holidayDates)
            )
        }

        // 주말 데코레이터
        calendarView.addDecorator(
            HomeCalendarDecorators.weekendDecorator(requireContext(), java.util.Calendar.SATURDAY)
        )
        calendarView.addDecorator(
            HomeCalendarDecorators.weekendDecorator(requireContext(), java.util.Calendar.SUNDAY)
        )
    }

    /**
     * setupStartWorkButton - 출근 버튼 클릭 이벤트 설정
     */
    private fun setupStartWorkButton() {
        binding.homeStartWorkBtn.setOnClickListener {
            val today = CalendarDay.today()

            if (!isWorkStarted) {
                // 출근 상태 업데이트
                isWorkStarted = true

                // UI 업데이트
                binding.homeStartWorkBtn.apply {
                    text = "출근완료" // 직접 문자열 삽입
                    isEnabled = false
                    setButtonEnabled(this, isEnabled)
                }

                // 캘린더에 출근 완료 점 추가
                binding.homeCalendarView.addDecorator(
                    HomeCalendarDecorators.workDecorator(requireContext(), today)
                )

                // 리스트뷰에 출근 완료 메시지 추가
                updateEventList(today, isWorkComplete = true)
            } else {
                showWorkAlreadyStartedDialog()
            }
        }
    }

    /**
     * 이미 출근한 경우 다이얼로그 표시
     */
    private fun showWorkAlreadyStartedDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_work_started, null)

        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogView.findViewById<Button>(R.id.dialog_button_ok).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    /**
     * 설정 버튼 클릭 이벤트 설정
     */
    private fun setupSettingsButton() {
        binding.homeSetting.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SettingFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    /**
     * 특정 날짜의 이벤트 리스트 업데이트
     * @param date 선택된 날짜
     * @param isWorkComplete 출근 완료 이벤트 추가 여부
     */
    private fun updateEventList(date: CalendarDay, isWorkComplete: Boolean = false) {
        // 선택된 날짜를 yyyy-MM-dd 형식으로 변환
        val selectedDate = String.format("%04d-%02d-%02d", date.year, date.month + 1, date.day)

        // ViewModel에서 LiveData로 제공되는 이벤트 데이터를 관찰
        viewModel.eventsForDate.observe(viewLifecycleOwner) { events ->
            val eventList = events.toMutableList()

            // 출근 완료 이벤트를 추가
            if (isWorkComplete) {
                eventList.add(0, Event("출근 완료", ""))
            }

            // 리스트뷰 어댑터 갱신
            val adapter = HomeListAdapter(requireContext(), R.layout.item_event, eventList)
            binding.homeSelectedDateEvents.adapter = adapter
        }

        // ViewModel에 선택된 날짜로 이벤트 로드 요청
        viewModel.loadEventsForDate(selectedDate)
    }

    /**
     * setButtonEnabled - 버튼 활성화/비활성화 상태를 설정
     * @param button View - 상태를 변경할 버튼
     * @param isEnabled Boolean - 버튼 활성화 여부
     */
    private fun setButtonEnabled(button: View, isEnabled: Boolean) {
        button.isEnabled = isEnabled

        // AppCompatButton을 처리하는 경우
        if (button is androidx.appcompat.widget.AppCompatButton) {
            // 버튼 배경 변경
            val backgroundRes = if (isEnabled) {
                R.drawable.btn_bg_work_started_selector // 활성화 상태 배경
            } else {
                R.drawable.btn_bg_work_started_selector // 비활성화 상태도 동일한 Selector로 관리
            }
            button.setBackgroundResource(backgroundRes)

            // 텍스트 색상 변경
            val textColorRes = if (isEnabled) {
                R.color.color_on_primary // 활성화 상태 텍스트
            } else {
                R.color.color_button_disabled_text // 비활성화 상태 텍스트
            }
            button.setTextColor(ContextCompat.getColor(requireContext(), textColorRes))
        }
    }
}
