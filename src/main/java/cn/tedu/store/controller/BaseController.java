package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;

/**
 * 项目中所有控制器类的基类
 * 
 * @author soft01
 *
 */
public abstract class BaseController {
	/**
	 * 从Session中获取用户id
	 * 
	 * @param session HttpSession
	 * @return 用户的id
	 */
	protected final Integer getUidFromSession(HttpSession session) {
		Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
		System.out.println(uid);
		return uid;
		
	}
}
