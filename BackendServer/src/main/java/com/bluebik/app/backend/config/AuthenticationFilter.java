package com.bluebik.app.backend.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMethod;

import com.bluebik.app.backend.service.LoginLogicService;

@WebFilter(urlPatterns = "/*")
public class AuthenticationFilter implements Filter {
	
	@Resource
	private LoginLogicService loginLogicService; 
	
	private static HashMap<String, RequestMethod> ignoreUri;
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			String url[] = ((HttpServletRequest)request).getRequestURI().split("/");
			String method = ((HttpServletRequest)request).getMethod().toString();
			RequestMethod found = ignoreUri.get("/"+url[1]);
			if(found == null || (!"ALL".equals(method) && !method.equals(found.toString()))) {
				String username = ((HttpServletRequest)request).getHeader("username");
				String secretKey = ((HttpServletRequest)request).getHeader("secretKey");
				if((loginLogicService.checkAccessByUsernameAndSecreKey(username, secretKey))) {
					chain.doFilter(request, response);
				}else {
					this.setAccessDenied(response);
				}
			}else {
				chain.doFilter(request, response);
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setAccessDenied(response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		ignoreUri = new HashMap<String, RequestMethod>();
		ignoreUri.put("/login", RequestMethod.POST);
		ignoreUri.put("/user", RequestMethod.POST);
		ignoreUri.put("/short-uri", RequestMethod.valueOf("ALL"));
	}
	
	private void setAccessDenied(ServletResponse response) throws IOException {
		((HttpServletResponse) response).setContentType("text/html");
		((HttpServletResponse) response).setStatus(HttpServletResponse.SC_FORBIDDEN);
		PrintWriter out = response.getWriter();
		out.println("access denied");
	}

}
