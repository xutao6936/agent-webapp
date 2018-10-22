<?xml version="1.0" encoding="GB2312"?>
<%@ page language="java" contentType="text/html; charset=GB2312" %>
<vxml version="1.0">
<% request.setCharacterEncoding("GB2312"); %>
<%
	String status = (String)request.getAttribute("status");
	System.out.println(status);
	out.println("<var name=\"status\" expr=\"\'"+ status +"\'\"/>");
%>
	<form>	
		<block>			
			<log><%=status %></log>
			<return namelist="status"/>
		</block>
	</form>
</vxml>