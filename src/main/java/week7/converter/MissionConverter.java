package week7.converter;

import org.springframework.data.domain.Page;
import week7.domain.Mission;
import week7.web.dto.MissionResponse;
import java.util.List;
import java.util.stream.Collectors;

public class MissionConverter {

    /**
     * Mission Entity -> MissionPreviewDTO 변환
     */
    public static MissionResponse.MissionPreviewDTO toMissionPreviewDTO(Mission mission) {
        return MissionResponse.MissionPreviewDTO.builder()
                .missionId(mission.getId())
                .storeId(mission.getStore().getId())
                .storeName(mission.getStore().getName())
                .title(mission.getTitle())
                .content(mission.getContent())
                .point(mission.getPoint())
                .deadline(mission.getDeadline())
                .build();
    }

    /**
     * Page<Mission> -> MissionPreviewListDTO 변환 (페이징 정보 포함)
     */
    public static MissionResponse.MissionPreviewListDTO toMissionPreviewListDTO(Page<Mission> missionPage) {

        List<MissionResponse.MissionPreviewDTO> missionPreviewDTOList = missionPage.getContent().stream()
                .map(MissionConverter::toMissionPreviewDTO)
                .collect(Collectors.toList());

        return MissionResponse.MissionPreviewListDTO.builder()
                .missionList(missionPreviewDTOList)
                .isFirst(missionPage.isFirst())
                .isLast(missionPage.isLast())
                .totalPage(missionPage.getTotalPages())
                .totalElements(missionPage.getTotalElements())
                .listSize(missionPreviewDTOList.size())
                .build();
    }
}