package com.onymo.app.data.model

/**
 * MeasureItem - 척도 데이터 모델
 * @param id 아이템 고유 번호
 * @param title 제목 (ex: "2점 척도")
 */
data class MeasureItem(
    val id: Int, // 고유 ID
    var title: String // 척도 제목
)
