package ptithcm.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ptithcm.entity.Brand;
import ptithcm.entity.Category;

@Transactional
@Controller
@RequestMapping("/admin/")
public class CategoryAndBrandController {
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
		String hql = "from Category order by name";
		List<Object> list = getList(hql);
		return list;
	}

	@ModelAttribute("brands")
	public List<Object> getsbrands() {
		String hql = "from Brand order by name";
		List<Object> list = getList(hql);
		return list;
	}

	@RequestMapping("category/table")
	public String table(ModelMap model) {
//		String hql = "FROM Category";
//		List<Object> list = getList(hql);
//		model.addAttribute("categories", list);
		return "admin/category-brand-table";
	}

	@RequestMapping(value = "category/insert", method = RequestMethod.GET)
	public String insertcate(ModelMap model) {
		model.addAttribute("p", new Category());
		model.addAttribute("action", "insert");
		model.addAttribute("type", "category");
		return "admin/category-brand-form";
	}

	@RequestMapping(value = "category/insert", method = RequestMethod.POST)
	public String insertcate(@ModelAttribute("p") Category c, RedirectAttributes re) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(c);
			t.commit();
			re.addFlashAttribute("message", "Insert OK !");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("message", "Insert Failed !" + e);
			return "redirect:/admin/category/insert.htm";
		} finally {
			session.close();
		}
		return "redirect:/admin/category/insert.htm";
	}

	@RequestMapping(value = "brand/insert", method = RequestMethod.GET)
	public String insertbrand(ModelMap model) {
		model.addAttribute("p", new Brand());
		model.addAttribute("action", "insert");
		model.addAttribute("type", "brand");
		return "admin/category-brand-form";
	}

	@RequestMapping(value = "brand/insert", method = RequestMethod.POST)
	public String insertbrand(@ModelAttribute("p") Brand b, RedirectAttributes re) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(b);
			t.commit();
			re.addFlashAttribute("message", "Insert OK !");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("message", "Insert Failed !" + e);
			return "redirect:/admin/brand/insert.htm";
		} finally {
			session.close();
		}
		return "redirect:/admin/brand/insert.htm";
	}

	@RequestMapping(value = "category/update/{cid}", method = RequestMethod.GET)
	public String updatecate(ModelMap model, @PathVariable("cid") int id, HttpSession httpSession) {
		Session session = factory.getCurrentSession();
		Category c = (Category) session.get(Category.class, id);
		model.addAttribute("p", c);
		model.addAttribute("action", "update");
		model.addAttribute("type", "category");
		httpSession.setAttribute("uptcid", id);
		return "admin/category-brand-form";
	}

	@RequestMapping(value = "category/update", method = RequestMethod.POST)
	public String updatecate(@ModelAttribute("p") Category c, HttpSession httpSession, RedirectAttributes re) {
		c.setId((int) httpSession.getAttribute("uptcid"));
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(c);
			t.commit();
			re.addFlashAttribute("message", "Success !");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("message", "Update failed !" + e);
		} finally {
			session.close();
		}
		return "redirect:/admin/category/update/" + c.getId() + ".htm";
	}

	@RequestMapping(value = "brand/update/{bid}", method = RequestMethod.GET)
	public String updatebrand(ModelMap model, @PathVariable("bid") int id, HttpSession httpSession) {
		Session session = factory.getCurrentSession();
		Brand p = (Brand) session.get(Brand.class, id);
		model.addAttribute("p", p);
		model.addAttribute("action", "update");
		model.addAttribute("type", "brand");

		httpSession.setAttribute("uptbid", id);
		return "admin/category-brand-form";
	}

	@RequestMapping(value = "brand/update", method = RequestMethod.POST)
	public String updatebrand(@ModelAttribute("p") Brand b, HttpSession httpSession, RedirectAttributes re) {
		b.setId((int) httpSession.getAttribute("uptbid"));
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(b);
			t.commit();
			re.addFlashAttribute("message", "Success !");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("message", "Update failed !" + e);
		} finally {
			session.close();
		}
		return "redirect:/admin/brand/update/" + b.getId() + ".htm";
	}

	@RequestMapping("category/delete/{id}")
	public String deletecate(RedirectAttributes re, @PathVariable("id") int id) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(session.get(Category.class, id));
			t.commit();
			re.addFlashAttribute("message", "Deleted !");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("message", "Delete Failed !");
		} finally {
			session.close();
		}
		return "redirect:/admin/category/table.htm";
	}
	@RequestMapping("brand/delete/{id}")
	public String deletebrand(RedirectAttributes re, @PathVariable("id") int id) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(session.get(Brand.class, id));
			t.commit();
			re.addFlashAttribute("message", "Deleted !");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("message", "Delete Failed !");
		} finally {
			session.close();
		}
		return "redirect:/admin/category/table.htm";
	}
}
