package com.zt.bbs.dao;

import java.util.List;

import com.zt.bbs.po.BbsReply;
import com.zt.utils.PageUtils;

public interface BbsReplyDao {
	public boolean addReply(BbsReply reply);//�ظ�����
	
//	public boolean delReply(int id);//ɾ������
	
	public boolean delReply2(int id);//ɾ�����۵Ļظ�
	
	public int count(int id);
	
	public List<BbsReply> findByPage(int id,PageUtils page);//�������ӵ�����
	
	public List<BbsReply> findReply2(int id);//���۵����лظ�
	
	public boolean addReply2(BbsReply reply);//�ظ�����
	
//	public BbsReply findByid(int id);
}
