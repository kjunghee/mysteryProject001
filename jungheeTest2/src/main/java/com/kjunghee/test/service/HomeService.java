package com.kjunghee.test.service;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kjunghee.test.db.dao.TbUserDAO;
import com.kjunghee.test.db.dto.TbUserDTO;

@Service
@MapperScan(basePackages="com.kjunghee.test.db.dao")
public class HomeService {
	
	@Autowired
	private SqlSession sqlSession;
	
	
	
	@Autowired
	private TbUserDAO tbUserDAO;

	
	//전부검색
	public List<TbUserDTO> listSelect() {
		List<TbUserDTO> result = new ArrayList<TbUserDTO>();
		TbUserDAO dao = sqlSession.getMapper(TbUserDAO.class);
		result = dao.listSelect();
		return result;
	}

	public List<TbUserDTO> listSearch(String nameKeyword, String departmentKeyword) {
		List<TbUserDTO> result = tbUserDAO.listSearch(nameKeyword, departmentKeyword);
		return result;
	}
	
	public Integer userInsert(TbUserDTO dto) {
		Integer result = 0;
		TbUserDAO dao = sqlSession.getMapper(TbUserDAO.class);
		
		result = dao.userInsert(dto);
		return result;
	}
	
	public void userDelete(Integer selectIndexNo) {
		tbUserDAO.userDelete(selectIndexNo);
	}
	
	public Integer userUpdate(TbUserDTO dto) {
		Integer result = 0;
		TbUserDAO dao = sqlSession.getMapper(TbUserDAO.class);
		result = dao.userUpdate(dto);
		return result;
	}
}
