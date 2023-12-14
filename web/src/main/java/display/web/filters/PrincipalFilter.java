package display.web.filters;

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
import display.web.javabean.MessageBean;


@WebFilter("/*")
public class PrincipalFilter implements Filter {

    public PrincipalFilter() {
    }

	public void destroy() {
	}

	private String encoding = "utf-8";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		httpRequest.setCharacterEncoding(encoding);

		MessageBean messageBean = (MessageBean) httpRequest.getSession().getAttribute("messageBean");
		
		String url = httpRequest.getRequestURI();
		
		if (messageBean == null || !messageBean.getUrl().contains(url)) {
			messageBean = new MessageBean();
			messageBean.setUrl(url);
		}
		
		httpRequest.getSession().setAttribute("messageBean", messageBean);
		
		LoginFilter.doFilter(request, response, chain);
    }

	public void init(FilterConfig filterConfig) throws ServletException {
		String encodingParam = filterConfig.getInitParameter("encoding");
		if (encodingParam != null) {
			encoding = encodingParam;
		}
	}

}
