package com.zt.utils;
import java.sql.*;
/*
 * ��jdbc�����Ľ��з�װ�Ĺ�����
 * */
public class DBUtils {
	private static final String className="oracle.jdbc.driver.OracleDriver";
	private static final String url="jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	private static final String user="scott";
	private static final String password="tiger";
	//1:��ȡ���ݿ�����
	public static Connection getConnection(){
		Connection conn=null;
		try {
			Class.forName(className);
			conn=DriverManager.getConnection(url, user, password);
		 } catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	//2:�ͷ���Դ
	public static void close(ResultSet rs,Statement stmt,Connection conn){
		try{
			if(rs!=null){
				rs.close();
			}
			if(stmt!=null){
				stmt.close();
			}
			if(conn!=null){
				conn.close();
			}
		 }catch(Exception e){
			e.printStackTrace();
		}
	}
}
