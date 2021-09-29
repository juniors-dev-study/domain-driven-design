package com.ddd.study.demo.test.domain;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ToString
@NoArgsConstructor
@Entity(name = "test_address")
public class TestAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(name = "state")
    private String state;
    @Column(name = "city")
    private String city;
    @Column(name = "zip_code")
    private int zipCode;

    TestAddress(String state, String city, int zipCode) {
        this.state = state;
        this.city = city;
        this.zipCode = zipCode;
    }
}
