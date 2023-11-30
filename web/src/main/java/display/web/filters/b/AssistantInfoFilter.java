package display.web.filters.b;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.managers.UsersManager;
import display.web.Container;
import display.web.javabean.CustomerBean;

/**
 * Servlet Filter implementation class AssistantInfoFilter
 */
@WebFilter("/*")
public class AssistantInfoFilter implements Filter {
       
	public AssistantInfoFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		boolean skipFilter = (boolean) httpRequest.getAttribute("skipFilter");
		
		if(!skipFilter) {
	        CustomerBean customerBean = (CustomerBean) httpRequest.getSession().getAttribute("customerBean");
	        if (customerBean.getRoleUser().equals("ASSISTANT")) {
	        	UsersManager usersManager = Container.getInstance().getUserManager();
	            String url = httpRequest.getRequestURI();
	            String registerAssistantUrl = "/web/mvc/controller/registerAssistantController.jsp";
	            
	            if (!(usersManager.hasAssistantInfo(customerBean.getEmailUser()) || url.equals(registerAssistantUrl))) {
	            	httpResponse.sendRedirect(registerAssistantUrl);
	                return;
	            } else if (url.equals(registerAssistantUrl)) {
	        		httpRequest.setAttribute("skipFilter", true);
	            }
	        }
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}


}
