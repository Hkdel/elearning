package com.zt.user.po;

import java.util.Date;

public class Role {
/*
 *  id number primary key,
       name varchar2(50),
       status char(1),
       createId number,
       createTime date       
 */
	private int id;//主键
	private String name;//角色名称
	private String status;//角色状态
	private User user;//创建人
	private Date createTime;//创建时间
}
