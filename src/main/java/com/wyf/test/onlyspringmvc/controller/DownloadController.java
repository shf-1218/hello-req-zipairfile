package com.wyf.test.onlyspringmvc.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DownloadController {

	@ResponseBody
	@RequestMapping(value = "/download/download1")
	public void download1(//
			HttpServletRequest req, //
			HttpServletResponse resp//
	) throws IOException {
		ServletOutputStream out = null;
		FileInputStream fis = null;
		try {
			// 模拟得到文件流
			fis = new FileInputStream("D:/测试.txt");
			int available = fis.available();// 文件多少字节
			byte[] fileBinaryBytes = new byte[available];
			fis.read(fileBinaryBytes);
			
			
			// 不能用PrintWriter，文件流非字符流
			out = resp.getOutputStream();
			out.write(fileBinaryBytes);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (fis !=null) {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

	}
	
	
	@ResponseBody
	@RequestMapping(value = "/download/download2")
	public void download2(//
			HttpServletRequest req, //
			HttpServletResponse resp//
	) {

	}
}