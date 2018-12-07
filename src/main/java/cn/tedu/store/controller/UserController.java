 package cn.tedu.store.controller;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.tedu.store.entity.ResponseResult;
import cn.tedu.store.entity.User;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.ex.PasswordConflictException;
import cn.tedu.store.service.ex.PasswordNotFounfException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameConflictException;
import cn.tedu.store.util.Validator;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	@Autowired
	private IUserService userService;
	//上传头像的最大尺寸kb
	public static final long MAX_AVATAR_SIZE = 100;
	
	//显示注册页面
	@RequestMapping("/reg.do")
	public String showReg() {
		return "user_reg";
	}
	
	//显示登录页面
	@RequestMapping("/login.do")
	public String showLogin() {
		return "user_login";
	}
	
	//显示修改密码界面
	@RequestMapping("/change_password.do")
	public String showChangePwd() {
		return "user_change_password";
	}
	
	//显示修改个人信息页面
	@RequestMapping("/change_info.do")
	public String showChangeInfo(HttpSession session,ModelMap modelMap) {
		//获取当前登录的用户id
		Integer uid = getUidFromSession(session);
		//查询当前登录的用户的数据
		User user = userService.findUserById(uid);
		//将用户数据封装，已准备转发
		modelMap.addAttribute("user",user );
		return "user_change_info";
	}
	
	//注册
	@RequestMapping(value="/handle_reg.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> handleReg(@RequestParam("username") String username,@RequestParam("password") String password,String email,String phone,Integer gender) {
		
		ResponseResult<Void> rr = null;
		
		//验证数据的有效性
		boolean result = Validator.checkUsername(username);
		System.out.println(result);
		//验证用户名格式
		if(!result) {
			rr = new ResponseResult<Void>(ResponseResult.STATE_ERR,"用户名格式错误");
			return rr;
		}
		//验证密码格式
		result = Validator.checkPassword(password);
		System.out.println(result);
		if(!result) {
			rr = new ResponseResult<Void>(ResponseResult.STATE_ERR,"密码格式错误");
			return rr;
		}
		
			try {
				User user = new User(username,password,gender,phone,email);
				//调用业务层的reg()方法实现注册功能
				userService.reg(user);
				//封装返回结果
				rr = new ResponseResult<Void>(ResponseResult.STATE_OK );
				
			}catch (UsernameConflictException e) {
				rr = new ResponseResult<Void>(e);
			}
		return rr;
	}
	
	//登录
	@RequestMapping(value="/handle_login.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> handleLogin(@RequestParam("username") String username,@RequestParam("password") String password,HttpSession session){
		ResponseResult<Void> rr = null;
		
		//验证用户名格式
		boolean result = Validator.checkUsername(username);
		System.out.println(result);
		if(!result) {
			rr = new ResponseResult<Void>(ResponseResult.STATE_ERR,"用户名格式不正确");
			return rr;
		}
		//验证密码格式
		result = Validator.checkPassword(password);
		System.out.println("密码"+result);
		if(!result) {
	     rr = new ResponseResult<Void>(ResponseResult.STATE_ERR,"密码格式错误");
	     return rr;
		}
		
			try {
				User user = userService.login(username, password);
				//登录成功后，将用户sessionId和用户名绑定到session上，用于验证（没有登录过，就不能显示主页）
				session.setAttribute("uid", user.getId());
				session.setAttribute("username",user.getUsername());
				rr = new ResponseResult<Void>(ResponseResult.STATE_OK);
				
			}catch (UsernameConflictException e) {
				rr = new ResponseResult<Void>(ResponseResult.STATE_ERR,e.getMessage());
				
			}catch (PasswordConflictException e) {
				rr = new ResponseResult<Void>(ResponseResult.STATE_PWD_ERR,e.getMessage());
			}
		return rr;
	}
	
	//修改密码(拦截器运行在Controller之前，如果每登录，session里就没有用户id，就走不到此方法)
	@RequestMapping(value="/handle_change_password.do",method=RequestMethod.POST )
	@ResponseBody
	public ResponseResult<Void> handleChangePwd(@RequestParam("old_password") String oldPassword,@RequestParam("new_password") String newPassword,HttpSession session){
		ResponseResult<Void> rr = null;
		try {
			//获得登录后绑定到session上的用户id
			//Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
			//调用父类的方法获取sesssion中的uid
			Integer uid = getUidFromSession(session);
			System.out.println(uid);
			userService.changePassword(uid, oldPassword, newPassword);
			rr = new ResponseResult<Void>(ResponseResult.STATE_OK);
		}catch (UserNotFoundException e) {
			rr = new ResponseResult<Void>(e);
		}catch(PasswordNotFounfException e) {
			rr = new ResponseResult<Void>(e);
		}
		return rr;
	}
	
	//修改用户信息
	@RequestMapping(value="/handle_change_info.do",method=	RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> handleChangeInfo(MultipartHttpServletRequest request,MultipartFile avatar,String username,Integer gender,String phone,String email,
														HttpSession session){
		
		ResponseResult<String> rr ;
		//用户上传的头像在服务器端的路径
		String avatarPath = null;
		//调用父类的方法获取sesssion中的uid
		Integer uid = getUidFromSession(session);
		
		//==========上传头像============
		//判断用户是否选择了上传头像
	 if(!avatar.isEmpty()) {
		//验证文件类型
		String contentType = avatar.getContentType();
		if(!"image/png".equals(contentType) && !"image/jpg".equals(contentType) && !"image/bmp".equals(contentType)) {
			rr = new ResponseResult<String>();
			rr.setState(ResponseResult.STATE_ERR);
			rr.setMessage("不支持上传"+contentType+"类型的文件");
			return rr;
		}
		//验证文件大小
		long size = avatar.getSize();
		if(size>MAX_AVATAR_SIZE*1024) {
			rr = new ResponseResult<String>();
			rr.setState(ResponseResult.STATE_ERR);
			rr.setMessage("上传的文件过大");
			return rr;
		}
		
		//保存头像的文件夹，所有用户头像都在这个文件夹中
		String avatarDirPath = request.getServletContext().getRealPath("/upload/image");  //获取/upload/image文件夹的路径
		File avatarDir = new File(avatarDirPath); //获取avatarDirPath路径下的文件夹
		System.out.println("[测试]avatarDirPath="+avatarDirPath);
		//如果保存头像的文件夹不存在，则创建
//		if(!avatarDir.exists()) {
//			avatarDir.mkdirs();
//		}
		//获取客户上传的原始文件的文件名
		String originalFilename = avatar.getOriginalFilename();
		//获取客户端上传的原始文件的扩展名
		String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
		//头像文件的文件名，每个用户的头象文件名都不应该相同
		Date date = new Date();
		String filename = getDateString(date)+uid+suffix; //当前时间+uid+扩展名
		//目标文件，即在服务端保存的用户头像文件
		File dest = new File(avatarDir,filename);  
		try {
			//将用户上传的头像保存到文件
			avatar.transferTo(dest);
			//确定用户头像在服务器的路径
			avatarPath = "upload/image/"+filename;
		}catch (IllegalStateException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}	
		
		//==========修改信息============
		try {
		//调用userservice的changeInfo方法，并获取返回值
	   Integer result = userService.changeInfo(uid,avatarPath,username, gender, phone, email);
		rr = new ResponseResult<String>(ResponseResult.STATE_OK);
		//将头像路径封装在返回路径对象的data属性里
		rr.setData(avatarPath);
		
		}catch (UsernameConflictException e) {
			rr = new ResponseResult<String>(-2,e.getMessage());
		}catch(UserNotFoundException e) {
			rr = new ResponseResult<String>(-1,e.getMessage());
		}
		return rr;
	}
	
	
	//将其设置为全局变量，可以节省内存,为避免线程安全问题，加final修饰
	private final String pattern = "yyMMddHHmmss";
	private final SimpleDateFormat sdf = new SimpleDateFormat(pattern,Locale.CHINA);
	//获取当前时间的字符串的方法
	private String getDateString(Date date) {
		
		return sdf.format(date);
	}

}







