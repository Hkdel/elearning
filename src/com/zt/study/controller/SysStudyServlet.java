package com.zt.study.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zt.study.dao.SourceTypeDao;
import com.zt.study.dao.StudyResourceDao;
import com.zt.study.dao.impl.SourceTypeDaoImpl;
import com.zt.study.dao.impl.StudyResourceDaoImpl;
import com.zt.study.po.SourceType;
import com.zt.study.po.StudyResource;
import com.zt.user.po.User;
import com.zt.utils.PageUtils;

@WebServlet("/admin/study/sysStudy")
public class SysStudyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SourceTypeDao typeDao;
	private StudyResourceDao sourceDao;
	public void init(ServletConfig config) throws ServletException {
		typeDao=new SourceTypeDaoImpl();
		sourceDao=new StudyResourceDaoImpl();
	}
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String method=request.getParameter("method");
		if("addType".equals(method)){
			addType(request,response);
		}
		if("updateType".equals(method)){
			updateType(request,response);
		}
		if("typeList".equals(method)){
			typeList(request,response);
		}
		if("delType".equals(method)){
			delType(request,response);
		}
		if("editType".equals(method)){
			editType(request,response);
		}
		if("updateStatus".equals(method)){
			updateStatus(request,response);
		}
		if("AuditResource".equals(method)){
			AuditResource(request,response);
		}
		if("updateResource".equals(method)){
			updateResource(request,response);
		}
		if("sourceList".equals(method)){
			sourceList(request,response);
		}
		
		
	}
	protected void addType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String typeName=request.getParameter("typeName");
		String status=request.getParameter("status");
		HttpSession session=request.getSession();
		User createUser=(User)session.getAttribute("loginSysUser");
		/*User user = new User();
		user.setId(1);*/
		SourceType type=new SourceType();
		type.setTypeName(typeName);
		type.setStatus(status);
		type.setUser(createUser);
		boolean f=typeDao.addSourceType(type);
		if(f){
			response.sendRedirect("sysStudy?method=typeList");
		}else{
			response.sendRedirect("error.jsp");
		}
	}
	protected void typeList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String typeName=request.getParameter("typeName");
		String status=request.getParameter("status");
		Map<Object, Object> filter=new HashMap<Object, Object>();
		if(typeName!=null&&!"".equals(typeName)){
			filter.put("typeName", typeName);
		}
		if(status!=null&&!"2".equals(status)){
			filter.put("status", status);
		}
		//2��������������Map filter ��ѯ���������ļ�¼����
		int totalSize=typeDao.getTotalSize(filter);
		//3:��ѯ��ǰҳ�ļ�¼����
		PageUtils pageUtils=new PageUtils();
		int currPage=1;//Ĭ���ǵ�һҳ
		String page=request.getParameter("page");//Ҳ���������뵱ǰҳ
		if(page!=null&&!"".equals(page)){
			currPage=Integer.parseInt(page);//������˵�ǰҳ  Ĭ�Ͼ��Ǵ��ĵ�ǰҳ
		}
		pageUtils.setCurrPage(currPage);//���õ�ǰҳ�ǵڼ�ҳ
		pageUtils.setPageSize(4);  //����ÿҳ������Ĭ����2
		pageUtils.setTotalSize(totalSize);	//�����ܼ�¼��
		pageUtils.setTotalPage(totalSize);	//������ҳ��
		//4:���ݵ�ǰҳ��Ϣ��ѯ��ǰҳ��¼
		List<SourceType> types=typeDao.searchTypeByPage(filter, pageUtils);
//		request.setAttribute("status", status);
		HttpSession session=request.getSession();
		
		session.setAttribute("filter", filter);
		session.setAttribute("typeList", types);
		session.setAttribute("pageUtils", pageUtils);
		request.getRequestDispatcher("sourceType/sourceTypeList.jsp").forward(request, response);
		
	}
	protected void updateType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr=request.getParameter("id");
		int typeId=0;
		if(idStr!=null){
			typeId=Integer.parseInt(idStr);
		}
		String typeName=request.getParameter("typeName");
		String status=request.getParameter("status");
		SourceType type=typeDao.getSourceTypeById(typeId);
		type.setTypeName(typeName);
		type.setStatus(status);
		boolean f=typeDao.updateSourceType(type);
		if(f){
			response.sendRedirect("sysStudy?method=typeList");
		}else{
			response.sendRedirect("error.jsp");
		}
	}
	protected void editType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr=request.getParameter("id");
		int typeId=0;
		if(idStr!=null){
			typeId=Integer.parseInt(idStr);
		}
		SourceType type=typeDao.getSourceTypeById(typeId);
		HttpSession session=request.getSession();
 	   	session.setAttribute("type", type);
 	   	request.getRequestDispatcher("sourceType/sourceTypeUpdate.jsp").forward(request, response);
	}
	protected void delType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr=request.getParameter("id");
		int typeId=0;
		if(idStr!=null){
			typeId=Integer.parseInt(idStr);
		}
		boolean f=typeDao.delSourceType(typeId);
		if(f){
			response.sendRedirect("sysStudy?method=typeList");
		}else{
			response.sendRedirect("error.jsp");
		}
		
	}
	protected void updateStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr=request.getParameter("id");
		int typeId=0;
		if(idStr!=null){
			typeId=Integer.parseInt(idStr);
		}
		String statusStr=request.getParameter("status");
		if(statusStr.equals("1")){
			statusStr="0";
		}else{
			statusStr="1";
		}
		SourceType type=typeDao.getSourceTypeById(typeId);
		type.setStatus(statusStr);
		boolean f=typeDao.updateTypeStatus(type);
		if(f){
//			if(statusStr.equals("1")){
//				statusStr="0";
//			}else{
//				statusStr="1";
//			}
//			request.setAttribute("status", statusStr);
			request.getRequestDispatcher("sysStudy?method=typeList").forward(request, response);
//			response.sendRedirect("sysStudy?method=typeList");
		}else{
			response.sendRedirect("error.jsp");
		}
		
	}
	protected void AuditResource(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr=request.getParameter("id");
		int resId=0;
		if(idStr!=null){
			resId=Integer.parseInt(idStr);
		}
		StudyResource res=sourceDao.getResourceById(resId);
		HttpSession session=request.getSession();
 	   	session.setAttribute("res", res);
 	   	request.getRequestDispatcher("source/sourceAudit.jsp").forward(request, response);
	}
	protected void updateResource(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr=request.getParameter("id");
		int resId=0;
		if(idStr!=null){
			resId=Integer.parseInt(idStr);
		}
		String checkStatus=request.getParameter("checkStatus");
		String checkOpinion=request.getParameter("checkOpinion");
		StudyResource res=sourceDao.getResourceById(resId);
		res.setCheckStatus(checkStatus);
		res.setCheckOpinion(checkOpinion);
		HttpSession session=request.getSession();
		User checkUser=(User)session.getAttribute("loginSysUser");
		System.out.println(checkUser);
//		User user=new User();
//		user.setId(1);
		res.setCheckUser(checkUser);
		boolean f=sourceDao.checkResource(res);
		if(f){
			response.sendRedirect("sysStudy?method=sourceList");
		}else{
			response.sendRedirect("error.jsp");
		}
	}
	protected void sourceList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("name");
		String checkStatus=request.getParameter("checkStatus");
		String typeId=request.getParameter("typeId");
		String beginTime=request.getParameter("beginTime");
		String endTime=request.getParameter("endTime");
		Map<Object, Object> filter=new HashMap<Object, Object>();
		if(name!=null&&!"".equals(name)){
			filter.put("name", name);
		}
		if(checkStatus!=null&&!"3".equals(checkStatus)){
			filter.put("checkStatus", checkStatus);
		}
		if(typeId!=null&&!"0".equals(typeId)){
			filter.put("typeId", Integer.parseInt(typeId));
		}
		if(beginTime!=null&&!"".equals(beginTime)){
			filter.put("begin", beginTime);
		}
		if(endTime!=null&&!"".equals(endTime)){
			filter.put("end", endTime);
		}
		int totalSize=sourceDao.getTotalSize(filter);
		PageUtils pageUtils =new PageUtils();
		int currPage=1;
		String page=request.getParameter("page");//Ҳ���������뵱ǰҳ
		if(page!=null&&!"".equals(page)){
			currPage=Integer.parseInt(page);//������˵�ǰҳ  Ĭ�Ͼ��Ǵ��ĵ�ǰҳ
		}
		pageUtils.setCurrPage(currPage);//���õ�ǰҳ�ǵڼ�ҳ
		pageUtils.setPageSize(8);  //����ÿҳ����
		pageUtils.setTotalSize(totalSize);	//�����ܼ�¼��
		pageUtils.setTotalPage(totalSize);	//������ҳ��
		List<StudyResource> resList=sourceDao.searchSourceByPage(filter, pageUtils);
		request.setAttribute("filter", filter);
		request.setAttribute("resList", resList);
		request.setAttribute("pageUtils", pageUtils);
		
		List<SourceType> types=typeDao.findAll();
		request.setAttribute("typeList", types);
		request.getRequestDispatcher("source/sourceList.jsp").forward(request, response);
		
	}
	
	

}
