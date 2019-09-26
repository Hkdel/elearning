package com.zt.study.dao;

import java.util.List;
import java.util.Map;
import com.zt.study.po.StudyNote;
import com.zt.utils.PageUtils;

/*
 *   学习笔记表的 数据层访问接口
 *   对应   t_studyNote 表的添 删 改 查 操作方法*/
public interface StudyNoteDao {
	public boolean addStudyNote(StudyNote note);
	public boolean updateStudyNote(StudyNote note);
	public boolean delStudyNote(int noteId);
	public StudyNote getStudyNoteById(int noteId);
	/*分页+模糊查询*/
	public int getTotalSize(Map<Object, Object> filter,int loginId);
	public List<StudyNote> searchNotesByPage(Map<Object, Object> filter,PageUtils pageUtils,int loginId);
}
