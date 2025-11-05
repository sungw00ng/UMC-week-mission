package week7.domain.test.exception;

import week7.global.apiPayload.code.BaseErrorCode;
import week7.global.apiPayload.exception.GeneralException;

public class TestException extends GeneralException {
    public TestException(BaseErrorCode code) {
        super(code);
    }
}