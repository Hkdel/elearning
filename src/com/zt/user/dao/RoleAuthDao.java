package com.zt.user.dao;

public interface RoleAuthDao {
	//��ӽ�ɫȨ��
	public boolean addRoleAuth(int roleId, int[] authIds);
	//ɾ����ɫ��Ȩ��
	public boolean delRoleAuth(int roleId);
}