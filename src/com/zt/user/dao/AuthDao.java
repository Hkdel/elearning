package com.zt.user.dao;

import java.util.List;
import java.util.Map;

import com.zt.user.po.Auth;
import com.zt.utils.PageUtils;

public interface AuthDao {
	// ��ӹ���ģ��
	public boolean addAuth(Auth auth);

	// ע������ģ��
	public boolean cancelAuth(int authId);

	// �ָ�����ģ��
	public boolean restoreAuth(int authId);

	// �޸Ĺ���ģ��
	public boolean updateAuth(Auth auth);

	// ����id��ѯ����ģ��
	public Auth getAuthById(int authId);

	// ��ѯ���й���ģ��
	public List<Auth> findAll();

	// ��ѯ����ģ������
	public int getTotalSizeByFilter(Map filter);

	// ����������ѯ���еĹ���ģ�鲢�ҷ�ҳ
	public List<Auth> findAll(Map filter, PageUtils pageUtils);

	// ��֪����ģ��id��ѯ��ģ�飨�Ѿ�֪��ģ��id,��ѯ�丸ģ�飬�����ģ���Ǹ�ģ�飬���ѯ��Ϊnull��
	public Auth getAuthParentById(int authId);

	// ��ѯ���и�ģ��
	public List<Auth> findParentAuth();
}
