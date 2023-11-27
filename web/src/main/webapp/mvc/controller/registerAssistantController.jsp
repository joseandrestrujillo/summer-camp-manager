<%@page import="business.exceptions.dao.NotFoundException"%>
<%@page import="business.enums.UserRole"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>
¡¡Bienvenido <jsp:getProperty property="emailUser" name="customerBean"/>!! 
<a href="./mvc/controller/logoutController.jsp">Cerrar sesion</a>

Tienes que registrar la informacion de asistente
...
aqui va un formulario