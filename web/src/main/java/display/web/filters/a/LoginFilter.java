package display.web.filters.a;

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

import display.web.javabean.CustomerBean;


@WebFilter("/*")
public class LoginFilter implements Filter {

    public LoginFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpRequest.setAttribute("skipFilter", false);
		
        CustomerBean customerBean = (CustomerBean) httpRequest.getSession().getAttribute("customerBean");

        String url = httpRequest.getRequestURI();

        if ((url.equals("/web/mvc/controller/loginController.jsp") || url.equals("/web/mvc/controller/registerController.jsp")) && (customerBean == null || customerBean.getEmailUser().equals(""))) {
    		httpRequest.setAttribute("skipFilter", true);
        }else if (customerBean == null || customerBean.getEmailUser().equals("")) {
        	httpResponse.sendRedirect("/web/mvc/controller/loginController.jsp");
        	return;
        } else if (url.endsWith("/loginController.jsp") || url.endsWith("/registerController.jsp")) {
        	httpResponse.sendRedirect("/web");
        	return;
        }
		
		
		chain.doFilter(request, response);
    }

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
