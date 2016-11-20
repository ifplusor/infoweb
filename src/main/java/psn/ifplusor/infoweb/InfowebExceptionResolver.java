package psn.ifplusor.infoweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author james
 * @version 11/14/16
 */
public class InfowebExceptionResolver implements HandlerExceptionResolver {

	private static Logger logger = LoggerFactory.getLogger(InfowebExceptionResolver.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
										 Object handler, Exception ex) {

		logger.error("Encounter exception: {}", ex.getMessage());

		return new ModelAndView("error");
	}
}