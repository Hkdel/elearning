package com.zt.utils;
/*
 * ��ҳ�Ĺ�����
 *      �� 15����¼��ÿҳ��ʾ2�� ��8ҳ ��ǰ��1ҳ ��ҳ ��һҳ ��һҳ βҳ 
 * */
public class PageUtils {
      private int currPage;      //��ǰҳ��
      private int pageSize;      //ÿҳ��¼������ÿҳ��С��
      private int totalSize;    //�ܼ�¼��
      private int totalPage;    //��ҳ��      
        //        ��ҳ��  = �ܼ�¼��%ÿҳ��¼���� ==0 ? �ܼ�¼��/ÿҳ��¼����
       //                                         : �ܼ�¼��/ÿҳ��¼����+1
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
	 //        ��ҳ��  = �ܼ�¼��%ÿҳ��¼���� ==0 ? �ܼ�¼��/ÿҳ��¼����
    //                                         : �ܼ�¼��/ÿҳ��¼����+1
	public void setTotalPage(int totalSize) {
		if(totalSize%this.pageSize==0){
			this.totalPage = totalSize/this.pageSize;
		 }else{
			this.totalPage = totalSize/this.pageSize+1;
		 }
		
	}
      
}
