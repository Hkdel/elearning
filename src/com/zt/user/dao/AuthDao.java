package com.zt.user.dao;

import java.util.List;
import java.util.Map;

import com.zt.user.po.Auth;
import com.zt.utils.PageUtils;

public interface AuthDao {
	// 添加功能模块
	public boolean addAuth(Auth auth);

	// 注销功能模块
	public boolean cancelAuth(int authId);

	// 恢复功能模块
	public boolean restoreAuth(int authId);

	// 修改功能模块
	public boolean updateAuth(Auth auth);

	// 根据id查询功能模块
	public Auth getAuthById(int authId);

	// 查询所有功能模块
	public List<Auth> findAll();

	// 查询功能模块总数
	public int getTotalSizeByFilter(Map filter);

	// 根据条件查询所有的功能模块并且分页
	public List<Auth> findAll(Map filter, PageUtils pageUtils);

	// 已知道子模块id查询父模块（已经知道模块id,查询其父模块，如果该模块是父模块，则查询出为null）
	public Auth getAuthParentById(int authId);

	// 查询所有父模块
	public List<Auth> findParentAuth();
}
