package ptithcm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ptithcm.entity.Account;

public class AdminAuthorizeInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute("account") == null) {
			response.sendRedirect(request.getContextPath() + "/login.htm");
			return false;
		} else {
			Account account = (Account) session.getAttribute("account");
			if (account.getRole().getId() == 2) {
				response.sendRedirect(request.getContextPath() + "/khachthue/index.htm");
				return false;
			} else if (account.getRole().getId() == 1) {
				response.sendRedirect(request.getContextPath() + "/chutro/index.htm");
				return false;
			}
			return true;
		}
	}
}
