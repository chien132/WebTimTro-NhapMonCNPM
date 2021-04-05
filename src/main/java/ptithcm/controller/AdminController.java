package ptithcm.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ptithcm.entity.Bill;

@Transactional
@Controller
@RequestMapping("/admin/")
public class AdminController {
	@Autowired
	SessionFactory factory;
	@Autowired
	ServletContext context;

	List<Object> getList(String hql) {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery(hql);
		List<Object> list = query.list();
		return list;
	}

	@ModelAttribute("categories")
	public List<Object> getstaffs() {
		String hql = "from Category";
		List<Object> list = getList(hql);
		return list;
	}

	@ModelAttribute("products")
	public List<Object> getproducts() {
		String hql = "from Product";
		List<Object> list = getList(hql);
		return list;
	}

	@ModelAttribute("brands")
	public List<Object> getsbrands() {
		String hql = "from Brand";
		List<Object> list = getList(hql);
		return list;
	}

	@RequestMapping("index")
	public String indexx(ModelMap model) {
		Session session = factory.getCurrentSession();
		String hql = "FROM Bill";
		Query query = session.createQuery(hql);
		List<Bill> list = query.list();
		List<Bill> successlist = new ArrayList<>();
		List<Bill> progresslist = new ArrayList<>();
		for (Bill i : list) {
			if (i.isStatus()) {
				successlist.add(i);
			} else if (i.isPaid()) {
				progresslist.add(i);
			}
		}
		long cash = 0;
		for (Bill i : successlist) {
			cash += i.getcartvalue();
		}
		long progresscash = 0;
		for (Bill i : progresslist) {
			progresscash += i.getcartvalue();
		}
		model.addAttribute("cash", cash);
		model.addAttribute("progresscash", progresscash);
		model.addAttribute("successlist", successlist);
		model.addAttribute("successnumber", successlist.size());
		model.addAttribute("progresslist", progresslist);
		model.addAttribute("progressnumber", progresslist.size());
		return "admin/index";
	}

	@RequestMapping(value = "index", params = { "from", "to" }, method = RequestMethod.POST)
	public String index(ModelMap model, @PathParam("from") String from, @PathParam("to") String to) {
		Session session = factory.getCurrentSession();
		String hql = String.format("FROM Bill where buydate>= '%s' and buydate<='%s'", from, to);
		Query query = session.createQuery(hql);
		List<Bill> list = query.list();
		List<Bill> successlist = new ArrayList<>();
		List<Bill> progresslist = new ArrayList<>();
		for (Bill i : list) {
			if (i.isStatus()) {
				successlist.add(i);
			} else if (i.isPaid()) {
				progresslist.add(i);
			}
		}
		long cash = 0;
		for (Bill i : successlist) {
			cash += i.getcartvalue();
		}
		long progresscash = 0;
		for (Bill i : progresslist) {
			progresscash += i.getcartvalue();
		}
		model.addAttribute("cash", cash);
		model.addAttribute("progresscash", progresscash);
		model.addAttribute("successlist", successlist);
		model.addAttribute("successnumber", successlist.size());
		model.addAttribute("progresslist", progresslist);
		model.addAttribute("progressnumber", progresslist.size());
		return "admin/index";
	}

}
