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
	// 验证码图片的宽度
    private int width = 150;   
    // 验证码图片的高度
    private int height = 50;   
    // 验证码字符个数   
    private int codeCount = 4;   
    private int xx = 0;   
    // 字体高度   
    private int fontHeight;   
    private int codeY;   
    
    char[] codeSequence = {'0','1', '2', '3', '4', '5', '6', '7', '8', '9' };   
	public void init(ServletConfig config) throws ServletException {
       /* // 宽度 
        String strWidth = this.getInitParameter("width");   
        // 高度   
        String strHeight = this.getInitParameter("height");   
        // 字符个数   
        String strCodeCount = this.getInitParameter("codeCount");   
        // 将配置的信息转换成数值   
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
		 // 定义图像buffer   
        BufferedImage buffImg = new BufferedImage(width, height,   
                BufferedImage.TYPE_INT_RGB);   
        Graphics2D gd = buffImg.createGraphics();   
  
        // 创建一个随机数生成器类   
        Random random = new Random();   
  
        // 将图像填充为白色   
        gd.setColor(Color.WHITE);   
        gd.fillRect(0, 0, width, height);   
  
        // 创建字体，字体的大小应该根据图片的高度来定  
        Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);   
        // 设置字体 
        gd.setFont(font);   
  
        // 画边框  
        gd.setColor(Color.BLACK);   
        gd.drawRect(0, 0, width - 1, height - 1);   
  
        // 随机产生20条干扰线，使图象中的认证码不易被其它程序探测到  
        gd.setColor(Color.BLACK);   
        for (int i = 0; i < 100; i++) {   
            int x = random.nextInt(width);   
            int y = random.nextInt(height);   
            int xl = random.nextInt(12);   
            int yl = random.nextInt(12);   
            gd.drawLine(x, y, x + xl, y + yl);   
        }   
  
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证
        StringBuffer randomCode = new StringBuffer();   
        int red = 0, green = 0, blue = 0;   
  
        // 随机产生codeCount数字的验证码  
        for (int i = 0; i < codeCount; i++) {   
        	  // 得到随机产生的验证码数字   
            String strRand = String.valueOf(codeSequence[random.nextInt(10)]);   
            // 产生随机的颜色分量来构不同颜色，这样输出的每位数字的颜色值都将不同
            red = random.nextInt(255);   
            green = random.nextInt(255);   
            blue = random.nextInt(255);   
  
         // 用随机产生的颜色将验证码绘制到图像中 
            gd.setColor(new Color(red, green, blue));   
            gd.drawString(strRand, (i + 1) * xx, codeY);   
  
         // 将产生的四个随机数组合在一起   
            randomCode.append(strRand);   
        }   
        // 将四位数字的验证码保存到Session中
        HttpSession session = request.getSession();   
        session.setAttribute("validateCode", randomCode.toString());   
  
        // 禁止图像缓存  
        response.setHeader("Pragma", "no-cache");   
        response.setHeader("Cache-Control", "no-cache");   
        response.setDateHeader("Expires", 0);   
  
        response.setContentType("image/jpeg");   
  
        // 将图像输出到Servlet输出流中 
        ServletOutputStream sos = response.getOutputStream();   
        ImageIO.write(buffImg, "jpeg", sos);   
        sos.close();   
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
