package com.ddd.study.demo.test.domain;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assumptions.assumeThat;

@Tag("init-test")
@ExtendWith(SpringExtension.class)
@Rollback
@SpringBootTest
@ActiveProfiles("test")
public class DBTest {

    @Autowired
    private TestUserRepository testUserRepository;
    @Autowired
    private TestAddressRepository testAddressRepository;

    @ParameterizedTest
    @ValueSource(strings = {"hyounglin", "Chanhyeong Cho", "y2o2u2n", "youngvly"})
    void insert(String name) {
        assumeThat(name).isNotEmpty();

        TestAddress address = new TestAddress("경기도", "성남시", 12345);
        testAddressRepository.save(address);

        TestUser user = new TestUser(name, address);
        testUserRepository.save(user);
    }


}
