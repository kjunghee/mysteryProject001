package com.kjunghee.test.db.dao;

import java.util.List;

import com.kjunghee.test.db.dto.TbUserDTO;

public interface TbUserDAO {
	public List<TbUserDTO> listSelect();
	public List<TbUserDTO> listSearch(String nameKeyword, String departmentKeyword);
	public Integer userInsert(TbUserDTO dto);
	public void userDelete(Integer selectIndexNo);
	public Integer userUpdate(TbUserDTO dto);
}
