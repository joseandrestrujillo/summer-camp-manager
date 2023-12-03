package display.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.dtos.AssistantDTO;
import business.dtos.InscriptionDTO;
import business.enums.InscriptionType;
import business.exceptions.inscription.AfterLateTimeException;
import business.exceptions.inscription.AfterStartTimeException;
import business.exceptions.inscription.AssistantAlreadyEnrolledException;
import business.exceptions.inscription.MaxAssistantExcededException;
import business.exceptions.inscription.NeedToAddAnSpecialMonitorException;
import business.exceptions.inscription.WrongEducativeLevelException;
import business.managers.InscriptionManager;
import business.utilities.Utils;
import display.web.Container;
import display.web.javabean.CustomerBean;

/**
 * Servlet implementation class CampsServlet
 */
@WebServlet("/enroll")
public class EnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EnrollServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String showConfirmAlertStr = (String) request.getSession().getAttribute("showConfirmAlert");
		boolean showConfirmAlert = showConfirmAlertStr != null && showConfirmAlertStr.equals("true");
		
		if(showConfirmAlert) {
			RequestDispatcher disp = request.getRequestDispatcher("/mvc/view/assistant/enroll/availableCampsViewConfirmLatelyInscription.jsp");
			disp.forward(request, response);
		} else {
			RequestDispatcher disp = request.getRequestDispatcher("/mvc/view/assistant/availableCampsView.jsp");
			disp.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String wasConfirmedStr = request.getParameter("wasConfirmed");
		boolean wasConfirmed = wasConfirmedStr != null && wasConfirmedStr.equals("true");
		
		String alreadyAdviseStr = request.getParameter("alreadyAdvise");
		boolean alreadyAdvise = alreadyAdviseStr != null && alreadyAdviseStr.equals("true");

		if (!wasConfirmed && alreadyAdvise) {
			request.getSession().setAttribute("showConfirmAlert", "false");
			doGet(request, response);
			return;
		}
		
		String typeStr = request.getParameter("type");
		InscriptionType type = InscriptionType.valueOf(typeStr);
		
		String campIdStr = request.getParameter("camp_id");
		int campId = Integer.valueOf(campIdStr);
		
		InscriptionManager inscriptionManager = Container.getInstance().getInscriptionManager();
		
		CustomerBean customerBean = (CustomerBean) request.getSession().getAttribute("customerBean");
		Date currentDate = Utils.getCurrentDate();
		

		if (inscriptionManager.willBeEarly(campId, currentDate) || wasConfirmed) {
			AssistantDTO assistant = Container.getInstance().getUserManager().getAssistantInfo(customerBean.getEmailUser());

			InscriptionDTO inscription;
			try {
				inscription = inscriptionManager.enroll(assistant.getId(), campId, currentDate, type == InscriptionType.PARTIAL, assistant.isRequireSpecialAttention());
				request.getSession().setAttribute("enrollMsg", "Inscripción realizada correctamente. Precio: " + inscription.getPrice() + " euros. ");
			} catch (WrongEducativeLevelException e) {
				request.getSession().setAttribute("enrollError", "El nivel educativo de este campamento no es adecuado para el asistente. ");
			} catch (NeedToAddAnSpecialMonitorException e) {
				request.getSession().setAttribute("enrollError", "Es necesario añadir un monitor de atención especial antes de inscribir al asistente. ");
			} catch (AssistantAlreadyEnrolledException e) {
				request.getSession().setAttribute("enrollError", "Este asistente ya está inscrito en el campamento. ");
			} catch (MaxAssistantExcededException e) {
				request.getSession().setAttribute("enrollError", "No hay plazas libres para inscribir al asistente a todas las actividades de la modalidad seleccionada del campamento. ");
			} catch (AfterStartTimeException e) {
				request.getSession().setAttribute("enrollError", "El campamento ya ha comenzado. ");
			} catch (AfterLateTimeException e) {
				request.getSession().setAttribute("enrollError", "No es posible inscribirse a un campamento si no se hace con al menos 48h de antelación. ");
			}
		} else {
			if (!alreadyAdvise) {
				request.getSession().setAttribute("showConfirmAlert", "true");				
			} else {
				request.getSession().setAttribute("showConfirmAlert", "false");
			}
		}
		
		doGet(request, response);
	}

}
