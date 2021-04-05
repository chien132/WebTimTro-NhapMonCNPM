package ptithcm.controller;

import java.io.File;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ptithcm.entity.Product;

@Transactional
@Controller
@RequestMapping("/admin/product/")
public class ProductController {
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


	@RequestMapping("table")
	public String table(ModelMap model) {
		String hql = "FROM Product  order by cate_id";
		List<Object> list = getList(hql);
		model.addAttribute("products", list);
		return "admin/product-table";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("p", new Product());
		model.addAttribute("action", "add");
		return "admin/product-form";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(@ModelAttribute("p") Product p, @RequestParam("photo") MultipartFile photo,
			RedirectAttributes re) {
		Session session = factory.openSession();

		if (photo.getOriginalFilename().isEmpty()) {
			p.setImage("resources/images/products/default.jpg");
		} else if (!(photo.getContentType().contains("jpeg") || photo.getContentType().contains("png"))) {
			re.addFlashAttribute("message", "This file type is not supported !");
		} else {
			try {
				String photoPath = context.getRealPath("/resources/images/products/" + photo.getOriginalFilename());
				photo.transferTo(new File(photoPath));
				p.setImage("resources/images/products/" + photo.getOriginalFilename());
			} catch (Exception e) {
				re.addFlashAttribute("message", "Save file error: " + e);
				return "redirect:/admin/product/add.htm";
			}
		}

		Transaction t = session.beginTransaction();
		try {
			session.save(p);
			t.commit();
			re.addFlashAttribute("message", "Add OK !");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("message", "Add Failed !" + e);
			return "redirect:/admin/product/add.htm";
		} finally {
			session.close();
		}
		return "redirect:/admin/product/add.htm";
	}

	@RequestMapping(value = "update/{pid}", method = RequestMethod.GET)
	public String update(ModelMap model, @PathVariable("pid") int id, HttpSession httpSession) {
		Session session = factory.getCurrentSession();
		Product p = (Product) session.get(Product.class, id);
		model.addAttribute("p", p);
		model.addAttribute("action", "update");
		httpSession.setAttribute("uptpid", id);
		return "admin/product-form";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@ModelAttribute("p") Product p, HttpSession httpSession,
			@RequestParam("photo") MultipartFile photo, RedirectAttributes re) {
		Session session1 = factory.getCurrentSession();
		Session session2 = factory.openSession();
		p.setId((int) httpSession.getAttribute("uptpid"));

		if (photo.getOriginalFilename().isEmpty()) {
			Product temp = (Product) session1.get(Product.class, p.getId());
			p.setImage(temp.getImage());
		} else if (!(photo.getContentType().contains("jpeg") || photo.getContentType().contains("png"))) {
			re.addFlashAttribute("message", "This file type is not supported !");
		} else {
			try {
				String photoPath = context.getRealPath("/resources/images/products/" + photo.getOriginalFilename());
				photo.transferTo(new File(photoPath));
				p.setImage("resources/images/products/" + photo.getOriginalFilename());
			} catch (Exception e) {
				re.addFlashAttribute("message", "Save file error: " + e);
				return "redirect:/admin/product/update/" + p.getId() + ".htm";
			}
		}
		Transaction t = session2.beginTransaction();
		try {
			session2.update(p);
			t.commit();
			re.addFlashAttribute("message", "Success !");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("message", "Update failed !" + e);
		} finally {
			session2.close();
		}
		return "redirect:/admin/product/update/" + p.getId() + ".htm";
	}

	@RequestMapping("delete/{pid}")
	public String delete(RedirectAttributes re , @PathVariable("pid") int id) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(session.get(Product.class, id));
			t.commit();
			re.addFlashAttribute("message", "Deleted !");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("message", "Delete Failed !");
		} finally {
			session.close();
		}
		return "redirect:/admin/product/table.htm";
	}
}
