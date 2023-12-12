<%@page import="display.web.javabean.CampBean"%>
<%@page import="business.enums.EducativeLevel"%>
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

<%
List<CampDTO> camps = listOfCampsBean.getCamps();
%>
<div id="list_of_available_camps">
<%
for(CampDTO camp : camps) {
	CampBean campBean = new CampBean();
	campBean.setCamp(camp);
	int nInscriptions = Container.getInstance().getInscriptionManager().getNumberOfInscriptions(camp);
	int availableInscriptions = camp.getCapacity() - nInscriptions;
	campBean.setAvailableInscriptions(availableInscriptions);
	request.getSession().setAttribute("campBean", campBean);
	%>
		<div class="camp_details_div">
			<input type="radio" id="<%= camp.getCampID() %>" name="camp_id" value="<%= camp.getCampID() %>" required>
			<jsp:include page="./campDetails.jsp">
				<jsp:param name="cancelBtn" value="false"/>
			</jsp:include>
			<br>
		</div>
	<%
	request.getSession().setAttribute("campBean", null);
}
%>	

</div>
