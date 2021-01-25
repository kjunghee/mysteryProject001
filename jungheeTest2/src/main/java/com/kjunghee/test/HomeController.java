package com.kjunghee.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kjunghee.test.db.dto.TbUserDTO;
import com.kjunghee.test.db.model.TbUserModel;
import com.kjunghee.test.service.HomeService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

//	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private HomeService homeService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String list(Model model) {

		//전체검색
		
//		List<TbUserDTO> result = homeService.listSelect();
//		List<TbUserModel> userList = new ArrayList<TbUserModel>();
//		for (TbUserDTO dto : result) {
//			
//			TbUserModel tbUserModel = new TbUserModel();
//			tbUserModel.setIndex_id(dto.getIndex_id());
//			tbUserModel.setName(dto.getName());
//			tbUserModel.setDepartment(dto.getDepartment());
//			tbUserModel.setPosition(dto.getPosition());
//			tbUserModel.setGender(dto.getGender());
//			tbUserModel.setHireDate(dto.getHireDate());
//			tbUserModel.setPhoneNumber(dto.getPhoneNumber());
//			userList.add(tbUserModel);
//
//		}
//
//		model.addAttribute("userList", userList);
//		

		return "html/list";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> search(Model model, @RequestParam("nameKeyword") String nameKeyword, @RequestParam("departmentKeyword") String departmentKeyword) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<TbUserDTO> tbUserList = homeService.listSearch(nameKeyword, departmentKeyword);
		List<TbUserModel> userList = new ArrayList<TbUserModel>();
		for (TbUserDTO dto : tbUserList) {
			TbUserModel tbUserModel = new TbUserModel();
			tbUserModel.setIndex_id(dto.getIndex_id());
			tbUserModel.setName(dto.getName());
			tbUserModel.setDepartment(dto.getDepartment());
			tbUserModel.setPosition(dto.getPosition());
			tbUserModel.setGender(dto.getGender());
			
			tbUserModel.setDepartmentCode(dto.getDepartmentCode());
			tbUserModel.setPositionCode(dto.getPositionCode());
			tbUserModel.setGenderCode(dto.getGenderCode());
			
			tbUserModel.setHireDate(dto.getHireDate());
			tbUserModel.setPhoneNumber(dto.getPhoneNumber());
			userList.add(tbUserModel);
		}
		result.put("userList", userList);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> register(
			@RequestParam("name") String name, 
			@RequestParam("department") String department,
			@RequestParam("position") String position, 
			@RequestParam("gender") String gender, 
			@RequestParam("hireDate") String hireDate, 
			@RequestParam("phoneNumber") String phoneNumber) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		TbUserDTO dto = new TbUserDTO();
		dto.setName(name);
		dto.setDepartment(department);
		dto.setPosition(position);
		dto.setGender(gender);
		
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date newHireDate = transFormat.parse(hireDate); 
		dto.setHireDate(newHireDate);
		dto.setPhoneNumber(phoneNumber);
		Integer insertRes = homeService.userInsert(dto);
		result.put("msg", insertRes == 1 ? "success" : "fail");
		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("selectIndexNo") Integer selectIndexNo) {
		Map<String, Object> result = new HashMap<String, Object>();
		homeService.userDelete(selectIndexNo);
		result.put("msg", "success");
		return result;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(
			@RequestParam("index_id") Long index_id,
			@RequestParam("name") String name, 
			@RequestParam("department") String department,
			@RequestParam("position") String position, 
			@RequestParam("gender") String gender, 
			@RequestParam("hireDate") String hireDate, 
			@RequestParam("phoneNumber") String phoneNumber) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		TbUserDTO dto = new TbUserDTO();
		dto.setIndex_id(index_id);
		dto.setName(name);
		dto.setDepartment(department);
		dto.setPosition(position);
		dto.setGender(gender);
		
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date newHireDate = transFormat.parse(hireDate); 
		dto.setHireDate(newHireDate);
		System.out.println(newHireDate.toString());
		dto.setPhoneNumber(phoneNumber);
		Integer UpdateRes = homeService.userUpdate(dto);
		result.put("msg", UpdateRes == 1 ? "success" : "fail");
		return result;
	}
	

}
