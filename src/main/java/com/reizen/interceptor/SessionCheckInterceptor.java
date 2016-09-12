package com.reizen.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class SessionCheckInterceptor extends HandlerInterceptorAdapter {

	public SessionCheckInterceptor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	  System.out.println("user : "+request.getSession().getAttribute("user"));
		if(request.getSession().getAttribute("user")==null){
			response.sendError(800);
			return false;
		}
		return true;
	}

}