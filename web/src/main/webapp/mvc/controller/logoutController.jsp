<%@page import="business.exceptions.dao.NotFoundException"%>
<%@page import="business.enums.UserRole"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>
<%
String nextPage = "../../index.jsp";
String mensajeNextPage = "";
if (customerBean != null) {
	customerBean.setEmailUser("");
	response.sendRedirect(nextPage);
}
%>