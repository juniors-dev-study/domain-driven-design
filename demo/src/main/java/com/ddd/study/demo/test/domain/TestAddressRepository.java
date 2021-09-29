package com.ddd.study.demo.test.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestAddressRepository extends JpaRepository<TestAddress,Integer> {
}
