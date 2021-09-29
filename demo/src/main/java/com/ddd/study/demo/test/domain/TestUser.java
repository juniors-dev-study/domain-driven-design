package com.ddd.study.demo.test.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Entity(name = "test_user")
public class TestUser {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	@Column(name = "name", nullable = false, length = 45)
	private String name;
	@ManyToOne
	private TestAddress address;

	TestUser(String name, TestAddress address) {
		this.name = name;
		this.address = address;
	}
}
