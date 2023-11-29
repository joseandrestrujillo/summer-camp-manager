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
			response.getWriter().append("AssistantInfoFilter");
			httpRequest.setAttribute("skipFilter", true);
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}


}
