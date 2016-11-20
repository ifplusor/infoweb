package psn.ifplusor.core.security;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import psn.ifplusor.core.common.CodeAndMessage;

public class LocalAccessDeniedHandler implements AccessDeniedHandler {

	private static final Logger logger = LoggerFactory.getLogger(LocalAccessDeniedHandler.class);

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
					   AccessDeniedException ex) throws IOException, ServletException {

		if (isAjaxRequest(request)) {
			logger.debug("access denied. request via ajax.");

			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain");
			response.getWriter().write(CodeAndMessage.JSONCodeAndMessage(400, "no authorized."));
			response.getWriter().close();
		} else {
			request.setAttribute("isAjaxRequest", false);
			request.setAttribute("message", ex.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/security/denied");
			dispatcher.forward(request, response);
		}
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		return header != null && "XMLHttpRequest".equals(header);
	}
}