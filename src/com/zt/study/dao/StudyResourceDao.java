package com.zt.study.dao;

import java.util.List;
import java.util.Map;
import com.zt.study.po.StudyResource;
import com.zt.utils.PageUtils;

public interface StudyResourceDao {
	public boolean addResource(StudyResource resource);
	public boolean updateResource(StudyResource resource);//���δͨ��ǰ�����޸���Դ���Ƶ�
	public boolean checkResource(StudyResource resource);//��� �޸����״̬ ���ʱ�� �����
	public StudyResource getResourceById(int resId);	
	
	public boolean delResource(int resId);
	
	/*��ҳ+ģ����ѯ*/
	public int getTotalSize(Map<Object, Object> filter);
	public List<StudyResource> searchSourceByPage(Map<Object, Object> filter,PageUtils pageUtils);
	
	/*��ѯ��Դ�����Ѿ����õ�*/
	public int getTotalSize2(Map<Object, Object> filter,int loginId);
	public List<StudyResource> searchSourceByPage2(Map<Object, Object> filter,PageUtils pageUtils,int loginId);
}
