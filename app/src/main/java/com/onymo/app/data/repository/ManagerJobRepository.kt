package com.onymo.app.data.repository

import android.util.Log
import com.onymo.app.R
import com.onymo.app.data.model.JobCategory
import com.onymo.app.data.model.JobItem

/**
 * Repository 클래스
 *
 * ViewModel에 데이터를 제공하며, 실제 앱에서는 데이터베이스 또는 네트워크를 통해 데이터를 가져올 수도 있음.
 */
class ManagerJobRepository {

    /**
     * 전체 직업 목록 반환 (Mock 데이터)
     */
    fun getJobs(): List<JobItem> {
        return listOf(
            JobItem(1, "01.사업관리", R.drawable.icon_keyword01),
            JobItem(2, "02.경영·회계·사무", R.drawable.icon_keyword02),
            JobItem(3, "03.금융·보험", R.drawable.icon_keyword03),
            JobItem(4, "04.교육·자연·사회과학", R.drawable.icon_keyword04),
            JobItem(5, "05.법률·경찰·소방·교도·국방", R.drawable.icon_keyword05),
            JobItem(6, "06.보건·의료", R.drawable.icon_keyword06),
            JobItem(7, "07.사회복지·종교", R.drawable.icon_keyword07),
            JobItem(8, "08.문화·예술·디자인·방송", R.drawable.icon_keyword08),
            JobItem(9, "09.운전·운송", R.drawable.icon_keyword09),
            JobItem(10, "10.영업판매", R.drawable.icon_keyword10),
            JobItem(11, "11.경비·청소", R.drawable.icon_keyword11),
            JobItem(12, "12.이용·숙박·여행·오락·스포츠", R.drawable.icon_keyword12),
            JobItem(13, "13.음식서비스", R.drawable.icon_keyword13),
            JobItem(14, "14.건설", R.drawable.icon_keyword14),
            JobItem(15, "15.기계", R.drawable.icon_keyword15),
            JobItem(16, "16.재료", R.drawable.icon_keyword16),
            JobItem(17, "17.화학·바이오", R.drawable.icon_keyword17),
            JobItem(18, "18.섬유·의복", R.drawable.icon_keyword18),
            JobItem(19, "19.전기·전자", R.drawable.icon_keyword19),
            JobItem(20, "20.정보통신", R.drawable.icon_keyword20),
            JobItem(21, "21.식품가공", R.drawable.icon_keyword21),
            JobItem(22, "22.인쇄·목재·가구·공예", R.drawable.icon_keyword22),
            JobItem(23, "23.환경·에너지·안전", R.drawable.icon_keyword23),
            JobItem(24, "24.농림어업", R.drawable.icon_keyword24),
        )
    }

    /**
     * 사업관리 (01번 카테고리)의 하위 데이터를 반환
     */
    fun getBusinessManagementCategories(): List<JobCategory> {
        val categories = listOf(
            JobCategory(
                id = 1, title = "01. 사업관리", subCategories = listOf(
                    JobCategory(
                        id = 11, title = "01. 프로젝트관리", subCategories = listOf(
                            JobCategory(111, title = "01. 공적개발원조사업관리", subCategories = listOf(
                                    JobCategory(1111, "01. 공적개발원조사업 개발전략수립"),
                                    JobCategory(1112, "02. 공적개발원조사업 사업기획"),
                                    JobCategory(1113, "03. 공적개발원조사업 PDM수립"),
                                    JobCategory(1114, "04. 공적개발원조사업 총괄운영관리"),
                                    JobCategory(1115, "05. 공적개발원조사업 프로젝트 집행"),
                                    JobCategory(1116, "06. 공적개발원조사업 성과관리"),
                                    JobCategory(1117, "07. 공적개발원조사업 평가관리")
                                )
                            ),
                            JobCategory(112, title = "02. 프로젝트관리", subCategories = listOf(
                                    JobCategory(1121, "01. 프로젝트 전략기획"),
                                    JobCategory(1122, "02. 프로젝트 통합관리"),
                                    JobCategory(1123, "03. 프로젝트 이해관계자관리"),
                                    JobCategory(1124, "04. 프로젝트 범위관리"),
                                    JobCategory(1125, "05. 프로젝트 인적자원관리"),
                                    JobCategory(1126, "06. 프로젝트 일정관리"),
                                    JobCategory(1127, "07. 프로젝트 원가관리"),
                                    JobCategory(1128, "08. 프로젝트 리스크관리"),
                                    JobCategory(1129, "09. 프로젝트 품질관리"),
                                    JobCategory(1130, "10. 프로젝트 조달관리"),
                                    JobCategory(1131, "11. 프로젝트 의사소통관리")
                                )
                            ),
                            JobCategory(114, title = "03. 산학협력관리", subCategories = listOf(
                                JobCategory(1141, "01. 산학협력 사업기획"),
                                JobCategory(1142, "02. 대학기술이전 기획"),
                                JobCategory(1143, "03. 대학기술이전 성과 관리"),
                                JobCategory(1144, "04. 대학기술사업화"),
                                JobCategory(1145, "05. 산학협력 연구비관리"),
                                JobCategory(1146, "06. 산학협력 과제관리"),
                                JobCategory(1147, "07. 대학 창업교육 기획"),
                                JobCategory(1148, "08. 대학 창업교육 운영"),
                                JobCategory(1149, "09. 입주기업 지원관리"),
                                JobCategory(1150, "10. 산학협력 지식재산권 관리전략"),
                                JobCategory(1151, "11. 산학협력 지식재산권 운영관리"),
                                JobCategory(1152, "12. 산학협력 학술연구진흥"),
                                JobCategory(1153, "13. 산학협력 회계관리")
                                )
                            )
                        )
                    ),
                    JobCategory(
                        id = 12, title = "02. 해외관리", subCategories = listOf(
                            JobCategory(121, title = "01. 해외법인설립관리", subCategories = listOf(
                                    JobCategory(1211, "01. 해외법인 설립목적 파악"),
                                    JobCategory(1212, "02. 해외법인 설립내부역량 분석"),
                                    JobCategory(1213, "03. 해외법인 설립대상국 분석"),
                                    JobCategory(1214, "04. 해외법인 설립타당성 검토"),
                                    JobCategory(1215, "05. 해외법인 설립계획 수립"),
                                    JobCategory(1216, "06. 해외법인 운영계획 수립"),
                                    JobCategory(1217, "07. 국내 해외법인 설립절차 진행"),
                                    JobCategory(1218, "08. 현지 해외법인설립 절차 진행"),
                                    JobCategory(1219, "09. 해외법인설립 위험 관리"),
                                    JobCategory(1220, "10. 해외법인 설립 이해 관계자 관리")
                                )
                            ),
                            JobCategory(122, title = "02. 해외취업관리", subCategories = listOf(
                                    JobCategory(1221, "01. 해외취업 정보수집"),
                                    JobCategory(1222, "02. 해외취업 사업기획"),
                                    JobCategory(1223, "03. 해외구인처 발굴"),
                                    JobCategory(1224, "04. 해외취업 구직자 발굴"),
                                    JobCategory(1225, "05. 해외취업 구직자 컨설팅"),
                                    JobCategory(1226, "06. 해외취업 구직자 교육 훈련"),
                                    JobCategory(1227, "07. 해외취업 알선"),
                                    JobCategory(1228, "08. 해외취업 정착 지원"),
                                    JobCategory(1229, "09. 해외취업자 모니터링"),
                                    JobCategory(1230, "10. 해외취업 일반관리")
                                )
                            )
                        )
                    )
                )
            )
        )
        return categories
    }
}
