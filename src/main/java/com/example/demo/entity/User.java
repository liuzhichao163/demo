package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 模块名称
 * 模块简单说明
 *
 * @ClassName User
 * @Author: ytc
 * @Create: 2022/9/19 16:22
 * @Version: v1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private Long id;

	private String userName;

	private Integer age;

	private String phoneNum;

	private Integer sex;

	@Builder.Default
	private LocalDateTime createDate = LocalDateTime.now();

	private LocalDateTime updateDate;

	private Integer delFage;

	public User(String userName, Integer age) {
		this.userName = userName;
		this.age = age;
	}
}