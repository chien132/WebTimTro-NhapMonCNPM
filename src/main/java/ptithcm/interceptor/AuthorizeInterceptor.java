package ptithcm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ptithcm.entity.Account;

public class AuthorizeInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			response.sendRedirect(request.getContextPath() + "/login.htm");
			System.out.println("Khong co user");
			return false;
		}else {
			Account user = (Account) session.getAttribute("user");
			if (!user.getRole().equals("chutro")) {
				response.sendRedirect(request.getContextPath() + "/index.htm");
				System.out.println("User khong phai admin");
				return false;
			}
		}
		System.out.println("hail admin");
		return true;
	}

}
