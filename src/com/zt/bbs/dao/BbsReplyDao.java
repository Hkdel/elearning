package com.zt.bbs.dao;

import java.util.List;

import com.zt.bbs.po.BbsReply;
import com.zt.utils.PageUtils;

public interface BbsReplyDao {
	public boolean addReply(BbsReply reply);//回复帖子
	
//	public boolean delReply(int id);//删除评论
	
	public boolean delReply2(int id);//删除评论的回复
	
	public int count(int id);
	
	public List<BbsReply> findByPage(int id,PageUtils page);//所有帖子的评论
	
	public List<BbsReply> findReply2(int id);//评论的所有回复
	
	public boolean addReply2(BbsReply reply);//回复评论
	
//	public BbsReply findByid(int id);
}
