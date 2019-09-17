package com.zt.user.po;

import com.zt.user.po.Auth;

public class Auth {
	/*
	 * id number primary key, name varchar2(50), url varchar2(100), parentId
	 * number, status char(1), createId number, createTime date,
	 */
	private int id; // ����
	private String name; // ����
	private String url; // ����·��
	private String status; // ״̬
	private User user;// ������
	private Auth parent; // ��������
}
