package week7.service;

import org.springframework.data.domain.Page;
import week7.domain.Mission;

public interface MissionService {

    // 특정 지역의 미션 목록 조회 (페이징 포함)
    Page<Mission> getMissionList(String locationName, Integer page);
}