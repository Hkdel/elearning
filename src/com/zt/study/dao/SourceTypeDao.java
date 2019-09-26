package com.zt.study.dao;

import java.util.List;
import java.util.Map;
import com.zt.study.po.SourceType;
import com.zt.utils.PageUtils;
/*
 * t_studyType表 对应的数据层访问接口
 * */
public interface SourceTypeDao {
	public boolean addSourceType(SourceType type);
	public boolean updateSourceType(SourceType type);
	public boolean delSourceType(int typeId);
	public SourceType getSourceTypeById(int typeId);
	public boolean updateTypeStatus(SourceType type);
	public List<SourceType> findAll();
	public List<SourceType> findAllType();
	/*分页+模糊查询*/
	public int getTotalSize(Map<Object, Object> filter);
	public List<SourceType> searchTypeByPage(Map<Object, Object> filter,PageUtils pageUtils);
}
