package ptithcm.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptithcm.entity.Province;

public class ProvinceService {

	static public List<Province> findAll(SessionFactory factory) {
		Session session = factory.openSession();
		String hql = "from Province";
		Query query = session.createQuery(hql);
		List<Province> list = query.list();
		session.close();
		return list;
	}

	static public Province find(int id, SessionFactory factory) {
		Session session = factory.openSession();
		String hql = "from Province where id=" + id;
		Query query = session.createQuery(hql);
		List<Province> list = query.list();
		Province p = list.get(0);
		session.close();
		return p;
	}

}
