package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.*;
import java.util.List;
import java.sql.ResultSet;

public class yuangongServlet extends HttpServlet  {
	public void service(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		request.setCharacterEncoding("gb2312");
		System.out.println("method"+request.getParameter("method"));
		String method=(String)request.getParameter("method");
		if(method.equals("insert")){//员工添加
			insertM(request, response);
		}else if(method.equals("list")){//员工列表
			ListM(request, response);
		}else if(method.equals("delete")){//员工删除
			deleteM(request, response);
		}else if(method.equals("modifyindex")){//员工修改页面
			modifyIndex(request, response);
		}else if(method.equals("mod")){//员工修改页面
			mod(request, response);
		}else if(method.equals("update")){//员工修改页面
			update(request, response);
		}
		else if(method.equals("modify")){//员工修改
			modifyM(request, response);
		}else if(method.equals("detail")){//员工详细信息
			detailM(request, response);
		}else if(method.equals("Plist")){//员工列表信息
			PListM(request, response);
		}
	}
	private void insertM(HttpServletRequest request, HttpServletResponse response){//员工添加
		DBO db=new DBO();//初始化数据链接基础类
		String sql="";//sql语句初始化
			String keyid =(String)request.getParameter("keyid");//员工表主键
String yhm=(String)request.getParameter("yhm");//用户名
String mm=(String)request.getParameter("mm");//密码
String xm=(String)request.getParameter("xm");//姓名
String bld=(String)request.getParameter("bld");//便利店

		response.setCharacterEncoding("gb2312");
 	
		
		int num=0;
		int row=1;
		ResultSet rs=null;
		try {
				
			sql="select count(1) as num from yuangong where yhm='"+yhm+"'";
			rs=db.query(sql);
			if(rs.next()){
				num=rs.getInt("num");
			}
			if(num>0){
				request.setAttribute("flag", "操作失败,用户名重复"); 
			}else{
				sql="insert into yuangong(yhm,mm,xm,bld) values('"+yhm+"','"+mm+"','"+xm+"','"+bld+"')";
				;//员工SQL 语句
			row=db.update(sql);//执行sql语句 insert语句
			if(row==1){
			request.setAttribute("flag", "操作成功"); 
			}else{
			request.setAttribute("flag", "操作失败"); 
			}
			}
			
			request.getRequestDispatcher("yuangong/yuangongadd.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}
	private void ListM(HttpServletRequest request, HttpServletResponse response){//员工信息列表
		ResultSet rs=null;
		String sql="select * from yuangong where 1=1 ";//查询员工 sql语句
		
		String yhm=(String)request.getParameter("yhm");//用户名
		if(yhm!=null&&!yhm.equals("")){
		sql+=" and yhm like '%"+yhm+"%'";//符合条件 拼接sql 用户名
		}
		
		DBO db=new DBO();//初始化数据链接基础类
		db.open();
		
		//员工信息查询
		response.setCharacterEncoding("gb2312");
		try{
			rs=db.query(sql);//查询结果

			//PrintWriter out = response.getWriter();
			request.setAttribute("rs", rs); 
		

			request.getRequestDispatcher("yuangong/yuangonglist.jsp").forward(request, response);
		}catch(Exception e){
			e.toString();
			e.printStackTrace();
		}
		
	}
	private void PListM(HttpServletRequest request, HttpServletResponse response){//员工分页信息列表
		ResultSet rs=null;
		String sql="select * from yuangong where 1=1 ";//查询员工 sql语句
		DBO db=new DBO();//初始化数据链接基础类
	   
	    String str=(String)request.getParameter("Page");
		
		response.setCharacterEncoding("gb2312");
		try{
			
if(str==null){
				str="0";
				}
rs=db.query(sql);

			PrintWriter out = response.getWriter();
			request.setAttribute("rs", rs); 
		

		request.getRequestDispatcher("yuangong/yuangongPlist.jsp?Page="+str).forward(request, response);
		}catch(Exception e){
			e.toString();
			e.printStackTrace();
		}
		
	}
	private void modifyIndex(HttpServletRequest request, HttpServletResponse response){//员工修改首页
			ResultSet rs=null;
	response.setCharacterEncoding("gb2312");

		
		
		String keyid =(String)request.getParameter("keyid");//员工表主键
String yhm=(String)request.getParameter("yhm");//用户名
String mm=(String)request.getParameter("mm");//密码
String xm=(String)request.getParameter("xm");//姓名
String bld=(String)request.getParameter("bld");//便利店

	
		String sql="select * from yuangong where ygid="+keyid;//查询一条要修改的信息
		DBO db=new DBO();//初始化数据链接基础类
		
		try{
			
			db.open();//初始化数据库链接
			rs=db.query(sql);//查询sql语句
			PrintWriter out = response.getWriter();
			request.setAttribute("rs", rs); 
			
			request.getRequestDispatcher("yuangong/yuangongmodify.jsp").forward(request, response);
			//response.sendRedirect("yuangong/yuangongmodify.jsp");
		}catch(Exception e){
			e.toString();
			e.printStackTrace();
		}
		
	}
	private void mod(HttpServletRequest request, HttpServletResponse response){//员工修改首页
		ResultSet rs=null;
response.setCharacterEncoding("gb2312");

	
	
	String keyid =(String)request.getSession().getAttribute("id");//员工表主键
String yhm=(String)request.getParameter("yhm");//用户名
String mm=(String)request.getParameter("mm");//密码
String xm=(String)request.getParameter("xm");//姓名
String bld=(String)request.getParameter("bld");//便利店


	String sql="select * from yuangong where ygid="+keyid;//查询一条要修改的信息
	DBO db=new DBO();//初始化数据链接基础类
	
	try{
		
		db.open();//初始化数据库链接
		rs=db.query(sql);//查询sql语句
		PrintWriter out = response.getWriter();
		request.setAttribute("rs", rs); 
		
		request.getRequestDispatcher("yuangong/modify.jsp").forward(request, response);
		//response.sendRedirect("yuangong/yuangongmodify.jsp");
	}catch(Exception e){
		e.toString();
		e.printStackTrace();
	}
	
}
	private void detailM(HttpServletRequest request, HttpServletResponse response){//员工详细信息页面
		
			ResultSet rs=null;
	response.setCharacterEncoding("gb2312");

		
		String keyid =(String)request.getParameter("keyid");//员工表主键
String yhm=(String)request.getParameter("yhm");//用户名
String mm=(String)request.getParameter("mm");//密码
String xm=(String)request.getParameter("xm");//姓名
String bld=(String)request.getParameter("bld");//便利店

	
		String sql="select * from yuangong where ygid="+keyid;//查询一条要修改的信息
		DBO db=new DBO();//初始化数据链接基础类
		
		try{
			
			db.open();
			rs=db.query(sql);//查询sql语句
			PrintWriter out = response.getWriter();
			request.setAttribute("rs", rs); 
			
			request.getRequestDispatcher("yuangong/yuangongdetail.jsp").forward(request, response);
			//response.sendRedirect("yuangong/yuangongmodify.jsp");
		}catch(Exception e){
			e.toString();
			e.printStackTrace();
		}
		
	}
	private void deleteM(HttpServletRequest request, HttpServletResponse response){//员工删除
		
		DBO db=new DBO();
	  response.setCharacterEncoding("gb2312");
	int row=1;
	String keyid=(String)request.getParameter("keyid"); 
	String sql="";
  sql="delete from yuangong where ygid='"+keyid+"'";//删除员工 信息sql语句
		try{
					 row=db.update(sql);// 执行sql语句

				if(row==1){
				request.setAttribute("flag", "删除成功"); 
				}else{
				request.setAttribute("flag", "删除失败"); 
				}
		request.getRequestDispatcher("yuangongServlet?method=list").forward(request, response);
		
			//response.sendRedirect("yuangongServlet?method=list");
		}catch(Exception e){
			e.toString();
			e.printStackTrace();
		}
		
	}
	private void update(HttpServletRequest request, HttpServletResponse response){//员工修改功能
		DBO db=new DBO();
		String keyid =(String)request.getSession().getAttribute("id");//用户表主键
	String yhm=(String)request.getParameter("yhm");//用户名
	String mm=(String)request.getParameter("mm");//密码
	String xm=(String)request.getParameter("xm");//姓名
	String bld=(String)request.getParameter("bld");//便利店


		response.setCharacterEncoding("gb2312");
		String sql="";
	       
	sql="update yuangong set yhm='"+yhm+"',mm='"+mm+"',xm='"+xm+"',bld='"+bld+"' where ygid='"+keyid+"'";

		int row=1;
			try {
						
			 row=db.update(sql);
				
				if(row==1){
				request.setAttribute("flag", "操作成功"); 
				}else{
				request.setAttribute("flag", "操作失败"); 
				}
				request.getRequestDispatcher("yuangongServlet?method=mod").forward(request, response);
				
				//response.sendRedirect("yuangongServlet?method=list");
			} catch (Exception e) {
				e.printStackTrace();
			}
					
		}
	private void modifyM(HttpServletRequest request, HttpServletResponse response){//员工修改功能
	DBO db=new DBO();
		String keyid =(String)request.getParameter("keyid");//员工表主键
String yhm=(String)request.getParameter("yhm");//用户名
String mm=(String)request.getParameter("mm");//密码
String xm=(String)request.getParameter("xm");//姓名
String bld=(String)request.getParameter("bld");//便利店


	response.setCharacterEncoding("gb2312");
	String sql="";
       
sql="update yuangong set yhm='"+yhm+"',mm='"+mm+"',xm='"+xm+"',bld='"+bld+"' where ygid='"+keyid+"'";

	int row=1;
		try {
					
		 row=db.update(sql);
			
			if(row==1){
			request.setAttribute("flag", "操作成功"); 
			}else{
			request.setAttribute("flag", "操作失败"); 
			}
			request.getRequestDispatcher("yuangongServlet?method=list").forward(request, response);
			
			//response.sendRedirect("yuangongServlet?method=list");
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}
}
