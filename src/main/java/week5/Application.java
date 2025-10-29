package week5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Spring Boot 애플리케이션 진입점
 * - @SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
 * : Security 설정이 없는 상태에서 테스트를 위해 Security AutoConfiguration을 제외합니다.
 * 초기화 로직은 DatabaseInitializer.java로 분리되었습니다.
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
