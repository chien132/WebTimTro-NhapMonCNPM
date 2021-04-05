package ptithcm.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//import ptithcm.bean.Major;

@Transactional
public class GlobalInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	SessionFactory factory;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		Session session = factory.getCurrentSession();
//		String hql = "FROM Major";
//		Query query = session.createQuery(hql);
//		List<Major> list = query.list();
//		request.setAttribute("majors", list);
		return super.preHandle(request, response, handler);
	}
}
