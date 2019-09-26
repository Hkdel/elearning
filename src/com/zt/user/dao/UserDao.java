package com.zt.user.dao;

import java.util.List;
import java.util.Map;

import com.zt.user.po.Role;
import com.zt.user.po.User;
import com.zt.utils.PageUtils;

public interface UserDao {
	//ǰ̨�û���ѧ������¼����
	public User fontLogin(String accountName);
	//ǰ̨�û���ѧ����ע�᷽��
	public boolean regUser(User user);
	//����id��ѯ�û�
	public User findUserById(int id);
	//��̨�û���¼����
	public User login(String accountName);
	//��ѯ�����û���������
	public int getTotalSizeByFilter(Map filter);
	//����������ѯ���е��û����ҷ�ҳ
	public List<User> findAllUser(Map filter,PageUtils pageUtils);
	//ע���û�
	public boolean cancelUser(int userId);
	//�ָ��û�
	public boolean restoreUser(int userId);
	//����û�
	public boolean addUser(User user);
	//�޸��û�
	public boolean updateUser(User user);
	//��������
	public boolean resetPass(User user);
	
	public List<User> getScores();
	
	public List<User> findAllUser();
	
	public boolean updateFontUser(User user);
	
}