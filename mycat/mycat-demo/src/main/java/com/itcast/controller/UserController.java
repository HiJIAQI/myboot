package com.itcast.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itcast.entity.UserEntity;
import com.itcast.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/findUser")
	public List<UserEntity> findUser() {
		return userService.findUser();
	}

	@GetMapping("/insertUser")
	public Integer insertUser(@RequestParam String userName) {
		return userService.insertUser(userName);
	}

}
