// week7.service.StoreService.java

package week7.service;

import org.springframework.data.domain.Page;
import week7.domain.Store;

public interface StoreService {

    // 특정 지역의 가게 목록 조회
    Page<Store> getStoreList(String locationName, Integer page);
}