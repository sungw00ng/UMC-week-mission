package week7.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import week7.domain.Mission;
import week7.domain.repository.MissionRepository;
import week7.service.MissionService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionServiceImpl implements MissionService {

    private final MissionRepository missionRepository;

    @Override
    public Page<Mission> getMissionList(String locationName, Integer page) {

        // 페이지 크기 10, 페이지 번호는 0부터 시작
        PageRequest pageRequest = PageRequest.of(page, 10);

        // MissionRepository의 @Query 메서드를 사용하여 특정 지역의 미션 조회
        return missionRepository.findAvailableMissionsByLocation(locationName, pageRequest);
    }
}