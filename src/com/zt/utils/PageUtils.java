package com.zt.utils;
/*
 * 分页的工具类
 *      共 15条记录，每页显示2条 共8页 当前第1页 首页 上一页 下一页 尾页 
 * */
public class PageUtils {
      private int currPage;      //当前页数
      private int pageSize;      //每页记录条数（每页大小）
      private int totalSize;    //总记录数
      private int totalPage;    //总页数      
        //        总页数  = 总记录数%每页记录条数 ==0 ? 总记录数/每页记录条数
       //                                         : 总记录数/每页记录条数+1
	public int getCurrPage() {
		return currPage;
	}
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	public int getTotalPage() {
		return totalPage;
	}
	 //        总页数  = 总记录数%每页记录条数 ==0 ? 总记录数/每页记录条数
    //                                         : 总记录数/每页记录条数+1
	public void setTotalPage(int totalSize) {
		if(totalSize%this.pageSize==0){
			this.totalPage = totalSize/this.pageSize;
		 }else{
			this.totalPage = totalSize/this.pageSize+1;
		 }
		
	}
      
}
