package com.onymo.app.ui.manager.viewmodel

import androidx.lifecycle.ViewModel
import com.onymo.app.data.model.MeasureItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ManagerMeasureViewModel - 척도 관리 ViewModel
 */
class ManagerMeasureViewModel : ViewModel() {

    // 아웃라인 리스트 관리
    private val _outlineItems = MutableStateFlow<List<MeasureItem>>(emptyList())
    val outlineItems: StateFlow<List<MeasureItem>> get() = _outlineItems.asStateFlow()

    // 텍스트 아이템 리스트 관리
    private val _innerMeasureItemsMap = MutableStateFlow<Map<Int, List<MeasureItem>>>(emptyMap())
    val innerMeasureItemsMap: StateFlow<Map<Int, List<MeasureItem>>> get() = _innerMeasureItemsMap.asStateFlow()

    /**
     * addOutline - 새로운 아웃라인 생성 및 텍스트 아이템 추가
     * @param count 입력된 숫자 (생성할 텍스트 아이템 개수)
     */
    fun addOutline(count: Int) {
        //현재 아웃라인 리스트 가져오기
        val currentOutlines = _outlineItems.value.toMutableList()

        // 새 아웃라인 생성
        val newOutlineId = currentOutlines.size + 1
        val newOutline = MeasureItem(id = newOutlineId, title = "${newOutlineId}점 척도")
        currentOutlines.add(newOutline)

        // 업데이트된 아웃라인 리스트 저장
        _outlineItems.value = currentOutlines

        // 새 아웃라인에 대한 텍스트 아이템 리스트 생성
        val newInnerItems = List(count) { index ->
            MeasureItem(id = index + 1, title = "${index + 1}번째 항목")
        }

        // 기존 텍스트 아이템 맵에 새 아웃라인과 텍스트 리스트 추가
        val updatedInnerMap = _innerMeasureItemsMap.value.toMutableMap()
        updatedInnerMap[newOutlineId] = newInnerItems
        _innerMeasureItemsMap.value = updatedInnerMap
    }

    /**
     * clearOutlines - 모든 아웃라인 및 텍스트 아이템 초기화
     */
    fun clearOutlines() {
        _outlineItems.value = emptyList() // 아웃라인 리스트 초기화
        _innerMeasureItemsMap.value = emptyMap() // 텍스트 아이템 초기화
    }

    /**
     * moveOutlineUp - 리스트 항목을 위로 이동
     * @param position 이동할 항목의 위치
     */
    fun moveOutlineUp(position: Int) {
        val outlines = _outlineItems.value.toMutableList()
        if (position > 0) {
            outlines[position] = outlines[position - 1].also {
                outlines[position - 1] = outlines[position]
            }
            _outlineItems.value = outlines
        }
    }

    /**
     * moveOutlineDown - 리스트 항목을 아래로 이동
     * @param position 이동할 항목의 위치
     */
    fun moveOutlineDown(position: Int) {
        val outlines = _outlineItems.value.toMutableList()
        if (position < outlines.size - 1) {
            outlines[position] = outlines[position + 1].also {
                outlines[position + 1] = outlines[position]
            }
            _outlineItems.value = outlines
        }
    }

    /**
     * deleteOutline - 리스트 항목 삭제
     * @param position 삭제할 항목의 위치
     */
    fun deleteOutline(position: Int) {
        val outlines = _outlineItems.value.toMutableList()
        if (position in outlines.indices) {
            outlines.removeAt(position)
            _outlineItems.value = outlines
        }
    }
}