<%@page import="business.dtos.MonitorDTO"%>
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
	List<AssistantDTO> assistantDTOs = Container.getInstance().getAssistantsManager().getListOfRegisteredAssistant();
	
	for(AssistantDTO assistant : assistantDTOs) {
		long assistantAge = Utils.yearsBetween(assistant.getBirthDate(), Utils.getCurrentDate());
		String educativeLevelStr = "";
		
		if ((assistantAge >= 4) && (assistantAge <= 6)) {
			educativeLevelStr = "(Infantil)";
		} else if ((assistantAge >= 7) && (assistantAge <= 12)) {
			educativeLevelStr = "(Juvenil)";
		} else if ((assistantAge >= 13) && (assistantAge <= 17)) {
			educativeLevelStr = "(Adolescente)";
		} else {
			educativeLevelStr = "(No valido)";
		}
	
	%>
	
		
		<li>
			<%= assistant.getId() %>,
			<%= assistant.getFirstName() %> <%= assistant.getLastName() %>,
			<%= Utils.getStringDate(assistant.getBirthDate()) %> <%= educativeLevelStr %>, 
			Requiere atención Especial: <%= (assistant.isRequireSpecialAttention() ? "Si" : "No") %>
		</li>	
	<%
	}
	%>	
</ul>