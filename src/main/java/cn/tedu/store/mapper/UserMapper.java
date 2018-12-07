   package cn.tedu.store.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.User;

public interface UserMapper {
	/**
	 * 增加用户数据
	 * @param user 用户数据对象
	 * @return 受影响的行数，如果增加成功，则返回1，否则，返回0
	 */
	Integer insert(User user);

	/**
	 * 根据用户名查询用户数据
	 * @param username 用户名
	 * @return 与参数用户名匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	User findUserByUsername(String username);
	
	/**
	 * 根据用户id修改密码，两个参数，需要加注解@Param
	 * @param 用户id 和用户新密码
	 * @return 返回受影响的行数
	 */
	//UserMapper.xml中参数类型是user对象，而此方法的参数并不是user对象，所以需要加@Param()注解，将注解里的值作为Key，注解后的参数作为Value存进User对象中，
	//在UserMapper.xml中通过key值获取到value值，所以@Param()注解里的参数要和user对象参数、sql语句里 #{} 的参数 保持一致，而注解后的参数不做要求
	Integer changePassword(@Param("uid") Integer uid,@Param("password") String password,
			@Param("modifiedUser") String modifiedUser,@Param("modifiedTime") Date modifiedTime);
	
	/**
	 * 根据用户id查询用户密码
	 * @param id
	 * @return 返回用户对象user
	 */
	User findUserById(Integer id);
	
	/**
	 * 根据用户id修改用户个人信息
	 * @param user 封装了被修改的用户的id（必选），新用户名（可选），
	 * @return 返回受影响行数，返回1代表成功，返回0代表失败
	 */
	Integer changeInfo(User user);
}




