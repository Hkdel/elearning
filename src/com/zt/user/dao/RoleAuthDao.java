package com.zt.user.dao;

public interface RoleAuthDao {
	//添加角色权限
	public boolean addRoleAuth(int roleId, int[] authIds);
	//删除角色的权限
	public boolean delRoleAuth(int roleId);
}