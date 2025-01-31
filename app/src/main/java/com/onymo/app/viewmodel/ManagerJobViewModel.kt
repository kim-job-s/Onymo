package com.onymo.app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onymo.app.data.model.JobCategory
import com.onymo.app.data.model.JobItem
import com.onymo.app.data.repository.ManagerJobRepository

/**
 * ViewModel 클래스
 * - View와 Model 사이에서 데이터 및 상태를 관리
 * - 생명주기와 무관하게 데이터를 유지하며, UI 상태를 LiveData로 제공
 */
class ManagerJobViewModel : ViewModel() {

    private val repository = ManagerJobRepository() // 데이터를 제공하는 Repository

    // ✅ 전체 직업 목록 (대분류)
    private val _jobs = MutableLiveData<List<JobItem>>()
    val jobs: LiveData<List<JobItem>> get() = _jobs

    // ✅ 선택된 카드의 제목 (바텀시트에 표시)
    private val _selectedCardTitle = MutableLiveData<String>()
    val selectedCardTitle: LiveData<String> get() = _selectedCardTitle

    // ✅ 선택된 중분류 리스트
    private val _midCategories = MutableLiveData<List<JobCategory>>()
    val midCategories: LiveData<List<JobCategory>> get() = _midCategories

    // ✅ 선택된 소분류 리스트
    private val _smallCategories = MutableLiveData<List<JobCategory>>()
    val smallCategories: LiveData<List<JobCategory>> get() = _smallCategories

    // ✅ 선택된 세분류 리스트
    private val _detailCategories = MutableLiveData<List<JobCategory>>()
    val detailCategories: LiveData<List<JobCategory>> get() = _detailCategories

    // ✅ 선택된 능력단위 리스트
    private val _unitCategories = MutableLiveData<List<JobCategory>>()
    val unitCategories: LiveData<List<JobCategory>> get() = _unitCategories

    // ✅ 바텀시트 표시 여부
    private val _isBottomSheetVisible = MutableLiveData(false)
    val isBottomSheetVisible: LiveData<Boolean> get() = _isBottomSheetVisible

    init {
        loadJobs()
    }

    private fun loadJobs() {
        val jobList = repository.getJobs()
        _jobs.value = jobList
    }

    /**
     * ✅ 카드 선택 시 -> 바텀시트에 제목 & 중분류 표시
     */
    fun selectJob(job: JobItem) {
        _selectedCardTitle.value = job.title
        _midCategories.value = repository.getBusinessManagementCategories()
        _smallCategories.value = emptyList() // ✅ 초기화
        _detailCategories.value = emptyList() // ✅ 초기화
        _isBottomSheetVisible.value = true
    }

    /**
     * ✅ 중분류 선택 시 -> 소분류 데이터 로드
     */
    fun selectMidCategory(category: JobCategory) {
        val smallCategoryList = category.subCategories ?: emptyList()
        _smallCategories.value = smallCategoryList
    }

    /**
     * ✅ 소분류 선택 시 -> 세분류 데이터 로드
     */
    fun selectSmallCategory(category: JobCategory) {
        val detailCategoryList = category.subCategories ?: emptyList()
        _detailCategories.value = detailCategoryList
    }

    /**
     * ✅ 세분류 선택 시 -> 능력단위 데이터 로드
     */
    fun selectDetailCategory(category: JobCategory) {
        val unitCategoryList =category.subCategories ?: emptyList()
        _unitCategories.value = unitCategoryList
    }

    /**
     * ✅ 세분류 선택 시 -> 능력단위 데이터 로드
     */
    fun selectUnitCategory(category: JobCategory) {
        val unitCategoryList =category.subCategories ?: emptyList()
        _unitCategories.value = unitCategoryList
    }

    /**
     * ✅ 바텀시트 닫기 기능 추가
     */
    fun hideBottomSheet() {
        _isBottomSheetVisible.value = false
    }
}
