package org.kras.aws.awsqna;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.kras.aws.awsqna.service.QuestionService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@SpringBootTest
@Testcontainers
public class AwsQnaApplicationTests {

    @Container
    static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.30")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpassword")
            .withEnv("MYSQL_CHARSET", "utf8mb4")
            .withEnv("MYSQL_COLLATION", "utf8mb4_unicode_ci")
            .withReuse(true);


    static {
        mySQLContainer.start();
    }
    @MockitoBean
    QuestionService questionService;

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @AfterAll
    public static void tearDown() {
        mySQLContainer.stop();
    }

    @Test
    void contextLoads() {
        Mockito.doNothing().when(questionService).persistsQuestion(Mockito.any());
    }

}
