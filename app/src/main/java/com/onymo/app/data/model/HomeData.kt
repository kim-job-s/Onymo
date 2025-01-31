package com.onymo.app.data.model

/**
 * Holiday - 공휴일 및 이벤트 데이터를 나타내는 클래스
 * @param date String - 공휴일 또는 이벤트 날짜 (예: "2025-01-01")
 * @param name String - 공휴일 또는 이벤트 이름 (예: "신정")
 * @param events List<Event> - 해당 날짜의 이벤트 리스트
 */
data class Holiday(
    val date: String, // 공휴일 날짜 (예: "2025-01-01")
    val name: String, // 공휴일 이름 (예: "신정")
    val events: List<Event> = emptyList() // 해당 날짜의 이벤트 리스트
)
/**
 * Event - 특정 날짜에 등록된 이벤트를 나타내는 클래스
 * @param title String - 이벤트 제목 (예: "미팅")
 * @param time String - 이벤트 시간 (예: "14:00")
 */
data class Event(
    val title: String, // 이벤트 제목
    val time: String   // 이벤트 시간
)