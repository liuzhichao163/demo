package cn.tedu.store.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tedu.store.entity.User;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.ex.PasswordConflictException;
import cn.tedu.store.service.ex.PasswordNotFounfException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameConflictException;
import cn.tedu.store.util.Validator;

@Service("userService")
public class UserServiceImpl implements IUserService{
	@Autowired
	private UserMapper userMapper;
	//注册
	public User reg(User user) throws UsernameConflictException{
		//注册成功
		User data = findUserByUsername(user.getUsername());
		if(data == null) {
			//生成uuid,调用加密方法对密码加密，将加密后方法存入user对象
			String uuid = UUID.randomUUID().toString();
			String md5Psaaword = getMd5Password(user.getPassword(),uuid);
			user.setPassword(md5Psaaword);
			user.setUuid(uuid);
			//调用insert方法查入数据
			userMapper.insert(user);
			//封装日志相关数据
			Date now = new Date();
			user.setCreatedUser("[System]");
			user.setCreatedTime(now);
			user.setModifiedUser(user.getUsername());
			user.setModifiedTime(now);
			
			return user;
		
		}else {
			//注册失败
			throw new UsernameConflictException("用户名（"+user.getUsername()+"）已存在。");
		}
	}
	
	//登录
	public User login(String username, String password) throws PasswordConflictException,UsernameConflictException{
		User user = findUserByUsername(username);
		if(user != null) {
			//从查询到的数据中获得uuid
			String salt = user.getUuid();
			//调用自身加密方法进行加密
			String newPassword = getMd5Password(password,salt);
			if(user.getPassword().equals(newPassword)) {
				//登录成功
				user.setPassword(null);
				user.setUuid(null);
				return user;
			}else {
				throw new PasswordConflictException("密码错误");
			}
		}else {
			throw new UsernameConflictException("用户名"+username+"不存在");
		}
	}
	

	//通过用户id修改密码
	public Integer changePassword(Integer uid,String oldPassword,String newPassword) throws UserNotFoundException, UserNotFoundException{
		//根据用户id获得用户信息
		User user = findUserById(uid);
		if(user==null) {
			throw new UserNotFoundException("尝试操作的用户不存在");
		}
		//获得用户原密码和盐值uuid
		String password = user.getPassword();
		String uuid = user.getUuid();
		
		Integer result = null;
		//进行原密码验证
		if(password.equals(getMd5Password(oldPassword,uuid))) {
			//密码匹配成功，可以修改密码
			 result = userMapper.changePassword(uid,getMd5Password(newPassword, uuid),user.getUsername(),new Date());
			if(result!=1) {
				throw new  PasswordNotFounfException("密码修改失败");
			}
		}else {
			//原密码验证失败
			throw new PasswordNotFounfException("密码验证不成功");
		}
		return result;
	}
	
	
	
	//修改用户个人信息
	public Integer changeInfo(Integer uid, String avatar,String username, Integer gender,  String phone, String email) throws UsernameConflictException,UserNotFoundException{
		System.out.println("1 业务层里的信息："+"username:"+username+",email:"+email+"avatar:"+avatar);
		
		//如果用户名，手机号，邮箱为"",则设为null;
		if(!Validator.checkUsername(username)) {
			username = null;
		}
//		if(!Validator.checkphone(phone)) {
//			phone = null;
//		}
//		if(!Validator.checkemail(email)) {
//			email = null;
//		}
		
		//修改用户名后的用户名不能是别人注册过的，如果是别人注册的还要判断被注册的用户名是否为自身用户名
		User olduser = findUserByUsername(username);
		Integer result = null;
		if(olduser!=null) {
			//次用户名被占用，判断查询出的用户名是否为自身用户名
			if(olduser.getId()==uid) {
				//新用户名与自己的老用户名相同
				username = null;
			}else {
				//新用户名被注册，不能使用此用户名
				throw new UsernameConflictException("用户名("+username+")已被注册");
			}
		}	
			//可以注册次用户名
			User user = new User(uid, username, gender, phone, email, avatar);
			System.out.println("4业务："+user.toString());
			User data = findUserById(uid);
			user.setModifiedUser(data.getUsername());
			user.setModifiedTime(new Date());
		   result = userMapper.changeInfo(user);
			if(result!=1) {
				throw new UserNotFoundException("尝试操作的用户不存在");
			}
			
		return result;
		
		
//		if (username != null) {
//			// 调用自身的findUserByUsername()根据用户名查询
//			User data = findUserByUsername(username);
//			if (data == null) {
//				// 结果为null：用户名没被占用，可以使用，用户名保持不变，等待后续代码将其更新到数据表中
//			} else {
//				// 如果不为null：根据用户名找到数据，则根据id还需要判断是不是当前用户
//				if (data.getId().equals(uid)) {
//					// 如果id匹配：用户名是自己的，没必要修改：设置为null
//					username = null;
//				} else {
//					// 如果id不匹配：用户名是别人：抛出异常UsernameConflictException
//					throw new UsernameConflictException(
//							"用户名(" + username + ")已经被注册！");
//				}
//			}
//		}
//
//		// 将各参数都封装到User类型的对象中
//		User user = new User(uid,avatar, username, gender, phone, email);
//		System.out.println("4业务："+user.toString());
//		// 封装日志数据
//		User data = findUserById(uid);
//		user.setModifiedUser(data.getUsername());
//		user.setModifiedTime(new Date());
//		// 调用持久层对象的changeInfo(User)方法，并获取返回值
//		Integer rows = userMapper.changeInfo(user);
//		// 判断返回值是否为：1
//		if (rows == 1) {
//			// -- 如果为1：返回1
//			return 1;
//		} else {
//			// -- 如果不为1：抛出异常UserNotFoundException
//			throw new UserNotFoundException("尝试操作的用户数据不存在！");
//		}
		
	}
	
	
	
	//根据用户名查询用户
	public User findUserByUsername(String username) {
		
		return userMapper.findUserByUsername(username);
	}
	
	//根据用户id查询用户密码
	public User findUserById(Integer id) {
		return userMapper.findUserById(id);
	}

	//使用md5+盐值对密码进行加密
	public String getMd5Password(String password, String salt) {
		//使用md5对原密码进行加密并转换为大写
		String str1 = DigestUtils.md5DigestAsHex(password.getBytes()).toUpperCase();
		//然后把加密后的密码和盐值进行拼接
		String str2 = str1+salt.toUpperCase();
		//对拼接后的密码再次加密
		String str3 = DigestUtils.md5DigestAsHex(str2.getBytes()).toUpperCase();
		return str3;
	}

	
	
	
	
	
	
}
