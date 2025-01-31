package com.onymo.app.data.model
import androidx.annotation.DrawableRes
/**
 * 데이터 클래스: JobItem
 *
 * 이 클래스는 각 아이템의 데이터를 정의합니다.
 * - id: 아이템 고유 식별자
 * - title: 아이템 제목
 * - iconResId: 아이템에 표시할 이미지 리소스 ID (R.drawable 참조)
 */
data class JobItem(
    val id: Int, // 아이템 ID
    val title: String, // 제목
    @DrawableRes val iconResId: Int // R.drawable 리소스 ID
)
/**
 * 데이터 클래스: JobCategory
 *
 * 중분류, 소분류, 세분류, 능력단위 데이터를 계층적으로 표현하기 위한 모델입니다.
 * - id: 고유 식별자
 * - title: 카테고리 이름
 * - subCategories: 하위 카테고리 리스트
 */
data class JobCategory(
    val id: Int,
    val title: String,
    val subCategories: List<JobCategory>? = null // 하위 카테고리
)