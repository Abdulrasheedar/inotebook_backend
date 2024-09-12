package com.inotebook.inotebook;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testClass {
	
	@GetMapping("test")
	public String sayHello() {
		return " Hello this is working ";
	}

}
