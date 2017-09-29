package com.wyf.test.onlyspringmvc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TestController {

	public TestController() {
		System.out.println("TestController被初始化一次");
	}

	@RequestMapping(value = "/test/testforward")
	public Object testforward(//
	) {
		return new ModelAndView("test/jsp/test-forward");
	}

	@ResponseBody
	@RequestMapping(value = "/test/testjson")
	public Object testjson(//
	) {
		Map<String, Object> map = new HashMap<>();
		map.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return map;
	}
}