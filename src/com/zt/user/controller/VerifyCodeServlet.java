package com.zt.user.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class VerifyCodeServlet
 */

@WebServlet(name="VerifyCodeServlet",urlPatterns="/verifyCode",
initParams={@WebInitParam(name="width",
value="120"),@WebInitParam(name="height",value="40")},loadOnStartup = 1)
public class VerifyCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// ��֤��ͼƬ�Ŀ��
    private int width = 150;   
    // ��֤��ͼƬ�ĸ߶�
    private int height = 50;   
    // ��֤���ַ�����   
    private int codeCount = 4;   
    private int xx = 0;   
    // ����߶�   
    private int fontHeight;   
    private int codeY;   
    
    char[] codeSequence = {'0','1', '2', '3', '4', '5', '6', '7', '8', '9' };   
	public void init(ServletConfig config) throws ServletException {
       /* // ��� 
        String strWidth = this.getInitParameter("width");   
        // �߶�   
        String strHeight = this.getInitParameter("height");   
        // �ַ�����   
        String strCodeCount = this.getInitParameter("codeCount");   
        // �����õ���Ϣת������ֵ   
        try {   
            if (strWidth != null && strWidth.length() != 0) {   
                width = Integer.parseInt(strWidth);   
            }   
            if (strHeight != null && strHeight.length() != 0) {   
                height = Integer.parseInt(strHeight);   
            }   
            if (strCodeCount != null && strCodeCount.length() != 0) {   
                codeCount = Integer.parseInt(strCodeCount);   
            }   
        } catch (NumberFormatException e) {
         e.printStackTrace();
        }  */
		xx = width / (codeCount + 1);   
        fontHeight = height - 2;   
        codeY = height - 4;   
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // ����ͼ��buffer   
        BufferedImage buffImg = new BufferedImage(width, height,   
                BufferedImage.TYPE_INT_RGB);   
        Graphics2D gd = buffImg.createGraphics();   
  
        // ����һ���������������   
        Random random = new Random();   
  
        // ��ͼ�����Ϊ��ɫ   
        gd.setColor(Color.WHITE);   
        gd.fillRect(0, 0, width, height);   
  
        // �������壬����Ĵ�СӦ�ø���ͼƬ�ĸ߶�����  
        Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);   
        // �������� 
        gd.setFont(font);   
  
        // ���߿�  
        gd.setColor(Color.BLACK);   
        gd.drawRect(0, 0, width - 1, height - 1);   
  
        // �������20�������ߣ�ʹͼ���е���֤�벻�ױ���������̽�⵽  
        gd.setColor(Color.BLACK);   
        for (int i = 0; i < 100; i++) {   
            int x = random.nextInt(width);   
            int y = random.nextInt(height);   
            int xl = random.nextInt(12);   
            int yl = random.nextInt(12);   
            gd.drawLine(x, y, x + xl, y + yl);   
        }   
  
        // randomCode���ڱ��������������֤�룬�Ա��û���¼�������֤
        StringBuffer randomCode = new StringBuffer();   
        int red = 0, green = 0, blue = 0;   
  
        // �������codeCount���ֵ���֤��  
        for (int i = 0; i < codeCount; i++) {   
        	  // �õ������������֤������   
            String strRand = String.valueOf(codeSequence[random.nextInt(10)]);   
            // �����������ɫ����������ͬ��ɫ�����������ÿλ���ֵ���ɫֵ������ͬ
            red = random.nextInt(255);   
            green = random.nextInt(255);   
            blue = random.nextInt(255);   
  
         // �������������ɫ����֤����Ƶ�ͼ���� 
            gd.setColor(new Color(red, green, blue));   
            gd.drawString(strRand, (i + 1) * xx, codeY);   
  
         // ���������ĸ�����������һ��   
            randomCode.append(strRand);   
        }   
        // ����λ���ֵ���֤�뱣�浽Session��
        HttpSession session = request.getSession();   
        session.setAttribute("validateCode", randomCode.toString());   
  
        // ��ֹͼ�񻺴�  
        response.setHeader("Pragma", "no-cache");   
        response.setHeader("Cache-Control", "no-cache");   
        response.setDateHeader("Expires", 0);   
  
        response.setContentType("image/jpeg");   
  
        // ��ͼ�������Servlet������� 
        ServletOutputStream sos = response.getOutputStream();   
        ImageIO.write(buffImg, "jpeg", sos);   
        sos.close();   
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
