package cn.tedu.store.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor{

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//登陆拦截器的目标：登陆了正常访问；如果没登陆就进行拦截
		HttpSession session = request.getSession();
		if(session.getAttribute("uid")==null) {
			//session中没有登陆的用户信息，先重定向(到登陆界面)后拦截
			response.sendRedirect("../user/login.do");
			return false; //fslse拦截；true放行
			
		}else {
			//session中存在登陆的用户信息，即已经登陆，放行
			return true;
		}
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
