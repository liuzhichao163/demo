package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.ex.InsertDataException;

@Controller
@RequestMapping("/address")
public class AddressController extends BaseController{
	
	//声明业务层对象
	@Autowired
	private IAddressService addressService;
	
	//显示新增用户地址页面
	@RequestMapping("/list.do")
	public String showList() {
		return "address_List.do";
	}
	
	
	//增加收货地址
	@RequestMapping(value="/handle_add.do",method=RequestMethod.POST)
	public String handleAdd(Address address,HttpSession session,ModelMap modleMap) {
		//检查数据的有效性
		//省略。。。
		//从session中获取uid，并封装进Address对象中
		Integer uid = getUidFromSession(session);
		address.setUid(uid);
		//获取username
		String username = session.getAttribute("username").toString();
		
		try {
			addressService.add(username, address);
			//添加成功，则重定向，当前位置：/address/handle_add.do       目标位置：/address/list.do
			return "redirect:list.do";
			
		}catch (InsertDataException e) {
			modleMap.addAttribute("massage",e.getMessage());
			return "error";
		}
	}
}
