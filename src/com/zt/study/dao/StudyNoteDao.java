package com.zt.study.dao;

import java.util.List;
import java.util.Map;
import com.zt.study.po.StudyNote;
import com.zt.utils.PageUtils;

/*
 *   ѧϰ�ʼǱ�� ���ݲ���ʽӿ�
 *   ��Ӧ   t_studyNote ����� ɾ �� �� ��������*/
public interface StudyNoteDao {
	public boolean addStudyNote(StudyNote note);
	public boolean updateStudyNote(StudyNote note);
	public boolean delStudyNote(int noteId);
	public StudyNote getStudyNoteById(int noteId);
	/*��ҳ+ģ����ѯ*/
	public int getTotalSize(Map<Object, Object> filter,int loginId);
	public List<StudyNote> searchNotesByPage(Map<Object, Object> filter,PageUtils pageUtils,int loginId);
}
