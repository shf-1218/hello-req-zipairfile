package com.wyf.test.onlyspringmvc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DebugController {
	
	@ResponseBody
	@RequestMapping(value = "/debug/refresh")
	public synchronized Object refreshConfig() {
		// properties文件刷新,新增properties配置文件并且有免重启tomcat刷新需要的都要放在这里 BEGIN
		//AccountConfig.initConstVars();
		
		
		
		// properties文件刷新,新增properties配置文件并且有免重启tomcat刷新需要的都要放在这里 END
		Map<String, Object> map = new HashMap<>();
		map.put("flag", true);
		map.put("retCode", "1");
		map.put("msg", "刷新成功");
		map.put("data", null);
		map.put("time", "最后刷新时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/debug/forward/{page}")
	public Object forward(HttpServletRequest req, HttpServletResponse resp, @PathVariable String page) {
		
		return new ModelAndView("forward:/WEB-INF/jsp/test/jsp/" + page + ".jsp").addObject(page);
	}
}