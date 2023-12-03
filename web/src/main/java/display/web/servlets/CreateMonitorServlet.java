package display.web.servlets;

import java.util.Arrays;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.dtos.ActivityDTO;
import business.dtos.CampDTO;
import business.dtos.MonitorDTO;
import business.enums.EducativeLevel;
import business.enums.TimeSlot;
import business.exceptions.activity.ActivityAlreadyExistException;
import business.exceptions.activity.ActivityNotFoundException;
import business.exceptions.activity.MaxMonitorsAddedException;
import business.exceptions.activity.MonitorIsNotInActivityException;
import business.exceptions.activity.NotEnoughMonitorsException;
import business.exceptions.camp.CampAlreadyRegisteredException;
import business.exceptions.camp.NotTheSameLevelException;
import business.exceptions.inscription.AssistantAlreadyEnrolledException;
import business.exceptions.monitor.MonitorAlreadyExistException;
import business.exceptions.monitor.MonitorNotFoundException;
import business.managers.CampsManager;
import business.utilities.Utils;
import display.cli.menus.Common;
import display.web.Container;
import display.web.javabean.ActivityFormBean;
import display.web.javabean.CampFormBean;

@WebServlet("/createMonitor")
public class CreateMonitorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CreateMonitorServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher disp = request.getRequestDispatcher("/mvc/view/admin/createMonitorView/monitorInfoFormView.jsp");
		disp.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		String monitorDni = request.getParameter("dni");
		String monitorFirstName = request.getParameter("firstName");
		String monitorLastName = request.getParameter("lastName");
		String isSpecialEducatorStr = request.getParameter("isSpecialEducator");
		boolean isSpecialEducator = isSpecialEducatorStr == "true" ? true : false;

		if(!(monitorDni.length() == 9)) {
			request.getSession().setAttribute("createMonitorError", "Formato de DNI invalido. ");
		}
		
		int dni = Integer.valueOf(monitorDni.substring(0, 8));
		
		
		MonitorDTO monitor = new MonitorDTO(dni, monitorFirstName, monitorLastName, isSpecialEducator);
		
		CampsManager campsManager = Container.getInstance().getCampsManager();
		
		try {									
			campsManager.registerMonitor(monitor);
			request.getSession().setAttribute("createMonitorMsg", "Monitor dado de alta correctamente. ");
		} catch (MonitorAlreadyExistException e) {
			request.getSession().setAttribute("createMonitorError", "Este DNI ya ha sido registrado en nuestro sistema. ");
		}
		
		response.sendRedirect("/web/monitorsManager");
	}

}
