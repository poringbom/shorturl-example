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
	
	private static HashMap<String, String> authenUri;
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			String url = ((HttpServletRequest)request).getRequestURI();
			String method = ((HttpServletRequest)request).getMethod().toString();
			String found = authenUri.get(url);
			if(found != null && method.equals(found)) {
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
		authenUri = new HashMap<String, String>();
		authenUri.put("/uri", RequestMethod.GET.toString());
	}
	
	private void setAccessDenied(ServletResponse response) throws IOException {
		((HttpServletResponse) response).setContentType("text/html");
		((HttpServletResponse) response).setStatus(HttpServletResponse.SC_FORBIDDEN);
		PrintWriter out = response.getWriter();
		out.println("access denied");
	}

}
