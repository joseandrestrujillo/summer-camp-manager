<%@page import="business.utilities.Utils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>
    
<header>
	<h1>Camping Site</h1>
	<p>¡¡Bienvenido <jsp:getProperty property="emailUser" name="customerBean"/>!! <%= Utils.getStringDate(Utils.getCurrentDate()) %></p>
	<a href="/web/mvc/controller/modifyUserController.jsp">Modificar datos de usuario</a>
	<a href="/web/mvc/controller/logoutController.jsp">Desconectar</a>
</header>