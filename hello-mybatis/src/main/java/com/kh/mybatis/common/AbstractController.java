package com.kh.mybatis.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractController {
	/**
	 * 리턴할 String은 jsp 또는 redirect에 관한 경로이다.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		throw new MethodNotAllowedException("GET");
	}
	
	public String doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
		{
			throw new MethodNotAllowedException("Post");
		}
}
