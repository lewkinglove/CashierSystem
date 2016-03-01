<%@page import="com.thoughtworks.cashier.ConfigPool"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

	//如果配置系统尚未初始化
	//则进入初始化系统界面, 否则进入功能测试界面
	/**
	if (ConfigPool.getConfigs().size() == 0)
		response.sendRedirect(basePath + "init_system.jsp");
	else
		response.sendRedirect(basePath + "printer_test_page.jsp");
	**/	
%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>Welcome To Cashier System</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
</head>
<body>

</body>
</html>
