package com.bluebik.app.backend.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bluebik.app.backend.service.UriResourceService;

@Controller
public class RedirectController {
	
	@Resource
	private UriResourceService uriResourceService;
	
	@Value("${app.server.hostname}")
	private String hostName;
	
	@RequestMapping(value="/{shortUri}")
	public void redirect(@PathVariable String shortUri, HttpServletResponse response) {
		response.setContentType(MediaType.TEXT_HTML_VALUE);
		try {
			String result = uriResourceService.getUriByShortUriAndcounting(shortUri);
			if(result != null) {
				response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
				if(!(result.toLowerCase().startsWith("http://") || result.toLowerCase().startsWith("https://"))){
					result = "http://"+result;
				}
				response.setHeader("Location", result);
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
			out.println(String.format("uri : %s can't found", hostName+"/"+shortUri));
			response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
