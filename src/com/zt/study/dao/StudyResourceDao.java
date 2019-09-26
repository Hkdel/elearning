package com.zt.study.dao;

import java.util.List;
import java.util.Map;
import com.zt.study.po.StudyResource;
import com.zt.utils.PageUtils;

public interface StudyResourceDao {
	public boolean addResource(StudyResource resource);
	public boolean updateResource(StudyResource resource);//审核未通过前可以修改资源名称等
	public boolean checkResource(StudyResource resource);//审核 修改审核状态 审核时间 审核人
	public StudyResource getResourceById(int resId);	
	
	public boolean delResource(int resId);
	
	/*分页+模糊查询*/
	public int getTotalSize(Map<Object, Object> filter);
	public List<StudyResource> searchSourceByPage(Map<Object, Object> filter,PageUtils pageUtils);
	
	/*查询资源类型已经启用的*/
	public int getTotalSize2(Map<Object, Object> filter,int loginId);
	public List<StudyResource> searchSourceByPage2(Map<Object, Object> filter,PageUtils pageUtils,int loginId);
}
