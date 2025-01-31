package com.onymo.app.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.onymo.app.R
import com.onymo.app.data.model.Event
import com.onymo.app.databinding.FragmentHomeBinding
import com.onymo.app.ui.setting.SettingFragment
import com.onymo.app.viewmodel.HomeViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.text.SimpleDateFormat
import java.util.*

/**
 * HomeFragment - 메인 홈 화면을 담당하는 Fragment
 * 출근 버튼, 캘린더 기능, 설정 화면 이동 기능을 포함합니다.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!! // Null Safety를 위한 바인딩
    private lateinit var viewModel: HomeViewModel
    private var isWorkStarted = false // 출근 완료 상태를 저장하는 변수

    private val handler = Handler(Looper.getMainLooper())
    private val timeUpdateRunnable = object : Runnable {
        override fun run() {
            updateCurrentTime()
            handler.postDelayed(this, 1000) // 1초마다 반복
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel 초기화
        viewModel = HomeViewModel()

        // UI 설정
        setupUI()

        // 실시간 시간 업데이트 시작
        handler.post(timeUpdateRunnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(timeUpdateRunnable)
        _binding = null
    }

    /**
     * UI 초기화 및 구성
     */
    private fun setupUI() {
        val calendarView: MaterialCalendarView = binding.homeCalendarView
        val today = CalendarDay.today()

        // 오늘 날짜 실시간 업데이트
        updateCurrentTime()

        // 캘린더 데코레이터 설정
        setupCalendarDecorators(calendarView)

        // 오늘 날짜 선택
        calendarView.setDateSelected(today, true)

        // 이벤트 리스트 초기화
        updateEventList(today)

        // 날짜 선택 이벤트 설정
        calendarView.setOnDateChangedListener { _, date, _ ->
            updateEventList(date)
            binding.homeStartWorkBtn.isEnabled = date == today && !isWorkStarted
        }

        // 출근 버튼 설정
        setupStartWorkButton()

        // 설정 버튼 설정
        setupSettingsButton()
    }

    /**
     * 현재 시간 업데이트하여 화면에 표시
     */
    private fun updateCurrentTime() {
        val currentTime = SimpleDateFormat(
            "yyyy년 MM월 dd일 EEEE HH:mm:ss", Locale.getDefault()
        ).format(Date())
        binding.homeTodayDate.text = currentTime
    }

    /**
     * 캘린더 데코레이터 설정
     */
    private fun setupCalendarDecorators(calendarView: MaterialCalendarView) {
        calendarView.addDecorator(HomeCalendarDecorators.todayDecorator(requireContext()))

        viewModel.holidayDates.observe(viewLifecycleOwner) { holidayDates ->
            calendarView.addDecorator(
                HomeCalendarDecorators.holidayDecorator(requireContext(), holidayDates)
            )
        }

        calendarView.addDecorator(
            HomeCalendarDecorators.weekendDecorator(requireContext(), Calendar.SATURDAY)
        )
        calendarView.addDecorator(
            HomeCalendarDecorators.weekendDecorator(requireContext(), Calendar.SUNDAY)
        )
    }

    /**
     * 출근 버튼 클릭 이벤트 설정
     */
    private fun setupStartWorkButton() {
        binding.homeStartWorkBtn.setOnClickListener {
            val today = CalendarDay.today()

            if (!isWorkStarted) {
                isWorkStarted = true
                binding.homeStartWorkBtn.apply {
                    text = "출근완료"
                    isEnabled = false
                    setButtonEnabled(this, isEnabled)
                }
                binding.homeCalendarView.addDecorator(
                    HomeCalendarDecorators.workDecorator(requireContext(), today)
                )
                updateEventList(today, isWorkComplete = true)
            } else {
                showWorkAlreadyStartedDialog()
            }
        }
    }

    /**
     * 출근 완료 상태 업데이트
     */
    private fun setButtonEnabled(button: View, isEnabled: Boolean) {
        button.isEnabled = isEnabled
        if (button is androidx.appcompat.widget.AppCompatButton) {
            val backgroundRes = if (isEnabled) {
                R.drawable.btn_bg_work_started_selector
            } else {
                R.drawable.btn_bg_work_started_selector
            }
            button.setBackgroundResource(backgroundRes)
            val textColorRes = if (isEnabled) {
                R.color.color_on_primary
            } else {
                R.color.color_button_disabled_text
            }
            button.setTextColor(ContextCompat.getColor(requireContext(), textColorRes))
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
     * 이벤트 리스트 업데이트
     */
    @SuppressLint("DefaultLocale")
    private fun updateEventList(date: CalendarDay, isWorkComplete: Boolean = false) {
        val selectedDate = String.format("%04d-%02d-%02d", date.year, date.month + 1, date.day)
        viewModel.eventsForDate.observe(viewLifecycleOwner) { events ->
            val eventList = events.toMutableList()
            if (isWorkComplete) {
                eventList.add(0, Event("출근 완료", selectedDate))
            }
            binding.homeSelectedDateEvents.adapter = HomeListAdapter(requireContext(), R.layout.item_event, eventList)
        }
        viewModel.loadEventsForDate(selectedDate)
    }
}
