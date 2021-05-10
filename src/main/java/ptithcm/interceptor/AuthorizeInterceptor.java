package ptithcm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ptithcm.entity.Account;

public class AuthorizeInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	SessionFactory factory;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute("username") == null) {
			response.sendRedirect(request.getContextPath() + "/login.htm");
			return false;
		} else {
			Session session2 = factory.getCurrentSession();
			Account account = (Account) session2.get(Account.class, (String) session.getAttribute("username"));
			if(account.getRole().getId()==2) {
				response.sendRedirect(request.getContextPath() + "/khachthue/index.htm");
				return true;
			}else if(account.getRole().getId()==1){
				response.sendRedirect(request.getContextPath() + "/chutro/index.htm");
				return true;
			} else {
				response.sendRedirect(request.getContextPath() + "/admin/index.htm");
				return true;
			}
		}
	}
}
