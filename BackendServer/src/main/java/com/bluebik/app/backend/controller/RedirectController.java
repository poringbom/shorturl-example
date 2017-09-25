package com.bluebik.app.backend.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bluebik.app.backend.service.UriResourceService;

@Controller
public class RedirectController {
	
	@Resource
	private UriResourceService uriResourceService;
	
	@RequestMapping("/short-uri/{shortUri}")
	public void redirect(@PathVariable String shortUri, HttpServletResponse response) {
		try {
			String uri = uriResourceService.getUriByShortUri(shortUri);
			if(uri != null) {
				response.sendRedirect(uri);
			}else {
				this.setRedirectError(response, shortUri);
			}
		}catch (Exception e) {
			this.setRedirectError(response, shortUri);
			e.printStackTrace();
		}
	}
	
	private void setRedirectError(HttpServletResponse response, String shortUri) {
		try {
			PrintWriter out = response.getWriter();
			out.write(String.format("uri : %s can't found", shortUri));
			((HttpServletResponse) response).setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
			((HttpServletResponse) response).setContentType("text/html");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
