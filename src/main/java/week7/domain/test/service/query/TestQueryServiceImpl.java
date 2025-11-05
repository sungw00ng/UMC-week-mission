package week7.domain.test.service.query;

import week7.domain.test.exception.TestException;
import week7.domain.test.exception.code.TestErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestQueryServiceImpl implements TestQueryService {

    @Override
    public void checkFlag(Long flag){
        if (flag != null && flag == 1){
            throw new TestException(TestErrorCode.TEST_EXCEPTION);
        }
    }
}