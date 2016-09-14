package com.reizen.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter("*.do")
public class AjaxFilter implements Filter{

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doFilter(ServletRequest request, 
      ServletResponse response, 
      FilterChain chain)
      throws IOException, ServletException {
    HttpServletResponse httpResponse = (HttpServletResponse)response;
    httpResponse.addHeader("Access-Control-Allow-Origin", "*");
    httpResponse.addHeader("Access-Control-Allow-Headers", "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
    
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    // TODO Auto-generated method stub
    
  }

}
