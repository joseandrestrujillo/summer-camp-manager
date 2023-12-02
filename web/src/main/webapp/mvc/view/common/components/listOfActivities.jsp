<%@page import="business.enums.EducativeLevel"%>
<%@page import="business.dtos.ActivityDTO"%>
<%@page import="java.util.List"%>
<%@page import="business.dtos.CampDTO"%>
<%@page import="business.utilities.Utils"%>
<%@page import="business.dtos.AssistantDTO"%>
<%@page import="business.dtos.CampDTO"%>
<%@page import="display.web.javabean.AssistantInfoBean"%>
<%@page import="business.enums.UserRole"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="display.web.Container" %>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>
<jsp:useBean  id="listOfCampsBean" scope="session" class="display.web.javabean.ListOfCampsBean"></jsp:useBean>

<ul>
	<%
	List<ActivityDTO> activityDTOs = Container.getInstance().getCampsManager().listOfActivities();
	
	for(ActivityDTO activity : activityDTOs) {
		String educativeLevel = activity.getEducativeLevel() == EducativeLevel.ELEMENTARY 
				? "Infantil"
				: activity.getEducativeLevel() == EducativeLevel.PRESCHOOL
					? "Juvenil"
					: "Adolescente";
	%>
		<li><%= activity.getActivityName() %>, <%= educativeLevel %></li>	
	<%
	}
	%>	
</ul>