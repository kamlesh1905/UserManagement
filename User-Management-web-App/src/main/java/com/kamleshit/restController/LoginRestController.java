package com.kamleshit.restController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kamleshit.domain.LoginRequest;
import com.kamleshit.service.UserService;


@RestController
public class LoginRestController 
{
	@Autowired
	private UserService service;
	
	@PostMapping("/login")
	public String Login(@RequestBody LoginRequest request) 
	{
		String loginStatus = service.loginCheck(request.getEmail(), request.getPwd());
		return loginStatus;
	}

}
