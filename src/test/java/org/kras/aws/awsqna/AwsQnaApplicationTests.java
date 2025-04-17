package org.kras.aws.awsqna;

import org.junit.jupiter.api.Test;
import org.kras.aws.awsqna.service.CsvService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.MySQLContainer;


@SpringBootTest
public class AwsQnaApplicationTests {
    static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.30")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpassword");

    static {
        mySQLContainer.start();
    }

    @MockitoBean
    CsvService csvService;

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @Test
    void contextLoads() {
        Mockito.doNothing().when(csvService).loadCsvFromResources();
    }

}
