package com.zt.user.dao;

import java.util.List;
import java.util.Map;

import com.zt.user.po.Auth;
import com.zt.user.po.Role;
import com.zt.utils.PageUtils;

public interface RoleDao {
		//��ѯ���н�ɫ��������
		public int getTotalSizeByFilter(Map filter);
		//����������ѯ���еĽ�ɫ���ҷ�ҳ
		public List<Role> findAllRole(Map filter,PageUtils pageUtils);
		//��ѯ���еĽ�ɫ
		public List<Role> findAllRole();
		//����id��ѯ��ɫ
		public Role getRoleById(int id);
		//��ѯ��ɫӵ�е�Ȩ��
		public List<Auth> findRoleAuth(int id);
		//ע����ɫ
		public boolean cancelRole(int roleId);
		//�ָ���ɫ
		public boolean restoreRole(int roleId);
		//�޸Ľ�ɫ
		public boolean updateRole(Role role);
		//��ӽ�ɫ
		public boolean addRole(Role role);
}
