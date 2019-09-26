package com.zt.user.dao;

import java.util.List;
import java.util.Map;

import com.zt.user.po.Auth;
import com.zt.user.po.Role;
import com.zt.utils.PageUtils;

public interface RoleDao {
		//查询所有角色的总数量
		public int getTotalSizeByFilter(Map filter);
		//根据条件查询所有的角色并且分页
		public List<Role> findAllRole(Map filter,PageUtils pageUtils);
		//查询所有的角色
		public List<Role> findAllRole();
		//根据id查询角色
		public Role getRoleById(int id);
		//查询角色拥有的权限
		public List<Auth> findRoleAuth(int id);
		//注销角色
		public boolean cancelRole(int roleId);
		//恢复角色
		public boolean restoreRole(int roleId);
		//修改角色
		public boolean updateRole(Role role);
		//添加角色
		public boolean addRole(Role role);
}
