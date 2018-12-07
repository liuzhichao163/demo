package cn.tedu.store.service;

import cn.tedu.store.entity.User;
import cn.tedu.store.service.ex.PasswordConflictException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameConflictException;

public interface IUserService {
	
	/**
	 * 注册功能
	 * @param user
	 * @return 返回注册成功的用户对象，包含用户id
	 * @throws 注册失败抛出UsernameConflictException异常
	 *
	 */
	User reg(User user)throws UsernameConflictException;

	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	User login(String username,String password)throws PasswordConflictException,UsernameConflictException;
	
	/**
	 * 根据用户名修改密码
	 * @param uid 用户id，来自用户登录后存入的session里的id
	 * @param oldPassword 用户原密码
	 * @param newPassword 用户新密码
	 * @return 返回生效行数
	 * @throws UsernameConflictException
	 * @throws PasswordConflictException
	 */
	Integer changePassword(Integer uid,String oldPassword,String newPassword)throws UserNotFoundException, UserNotFoundException;
	
	/**
	 * 修改用户个人信息
	 * @param uid 用户id
	 * @param uid 用户新头像路径，如果不修改，注意null值的使用
	 * @param username 新用户名，如果不修改，注意null值的使用
	 * @param gender 新性别，如果不修改，注意null值的使用
	 * @param phone	新手机号，如果不修改，注意null值的使用
	 * @param eamil	新用邮箱，如果不修改，注意null值的使用
	 * @return 返回受影响的行数
	 */
	Integer changeInfo(Integer uid,String avatar,String username,Integer gender,String phone,String email)throws UsernameConflictException,UserNotFoundException;
	
	/**
	 * 判断是否有重名用户
	 * @param username
	 * @return 没有重名用户返回null，有重名对象就返回user对象
	 */
	User findUserByUsername(String username);
	
	/**
	 * 根据用户id查询用户密码
	 * @param id
	 * @return
	 */
	User findUserById(Integer id);
	
	/**
	 * 获取加密后的密码
	 * @param password 原密码 
	 * @param uuid 盐
	 * @return 加密后的密码
	 */
	String getMd5Password(String password,String salt);
	
	
	
	
	
	
}
