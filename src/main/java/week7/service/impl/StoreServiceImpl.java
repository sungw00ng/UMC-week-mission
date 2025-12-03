// week7.service.impl.StoreServiceImpl.java

package week7.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import week7.domain.Store;
import week7.domain.repository.StoreRepository;
import week7.service.StoreService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    @Override
    public Page<Store> getStoreList(String locationName, Integer page) {

        // Pageable 객체 생성: 페이지는 0부터 시작, 페이지당 10개로 설정 (필요에 따라 변경 가능)
        // Spring Data JPA는 Pageable 객체를 받아 페이징 처리를 수행합니다.
        PageRequest pageRequest = PageRequest.of(page, 10);

        // Repository를 사용하여 조건에 맞는 가게 목록 조회
        return storeRepository.findByLocationName(locationName, pageRequest);
    }
}