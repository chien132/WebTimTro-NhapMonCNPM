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
import ptithcm.entity.User;

@Transactional
@Controller
@RequestMapping("/admin/user/")
public class UserManagerController {
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

	@RequestMapping("table")
	public String table(ModelMap model) {
		String hql = "FROM User";
		List<Object> list = getList(hql);
		model.addAttribute("users", list);
		return "admin/user-table";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String insertuser(ModelMap model) {
		model.addAttribute("u", new User());
		model.addAttribute("action", "add");
		return "admin/user-form";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(@ModelAttribute("u") User u, @RequestParam("photo") MultipartFile photo, RedirectAttributes re) {
		u.setUsername(u.getUsername().trim());
		u.setPassword(u.getPassword().trim());
		Session session = factory.openSession();
		String hql = String.format("from User where username='%s'", u.getUsername());
		Query query = session.createQuery(hql);
		List<User> list = query.list();
		if (list.isEmpty()) {

			if (photo.getOriginalFilename().isEmpty()) {
				u.setAvatar("resources/images/avatar/user-default.png");
			} else if (!(photo.getContentType().contains("jpeg") || photo.getContentType().contains("png"))) {
				re.addFlashAttribute("message", "This file type is not supported !");
			} else {
				try {
					String photoPath = context.getRealPath("/resources/images/avatar/" + photo.getOriginalFilename());
					photo.transferTo(new File(photoPath));
					u.setAvatar("resources/images/avatar/" + photo.getOriginalFilename());
				} catch (Exception e) {
					re.addFlashAttribute("message", "Save file error: " + e);
					return "redirect:/admin/user/add.htm";
				}
			}

			Transaction t = session.beginTransaction();
			try {
				session.save(u);
				t.commit();
				re.addFlashAttribute("message", "Add OK !");
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("message", "Add Failed !" + e);
				return "redirect:/admin/user/add.htm";
			} finally {
				session.close();
			}
		} else {
			re.addFlashAttribute("message", "This username is not available !");
		}
		return "redirect:/admin/user/add.htm";
	}

	@RequestMapping(value = "update/{uid}", method = RequestMethod.GET)
	public String update(ModelMap model, @PathVariable("uid") int id, HttpSession httpSession) {
		Session session = factory.getCurrentSession();
		User u = (User) session.get(User.class, id);
		model.addAttribute("u", u);
		model.addAttribute("action", "update");
		httpSession.setAttribute("uptuid", id);
		return "admin/user-form";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@ModelAttribute("u") User u, HttpSession httpSession,
			@RequestParam("photo") MultipartFile photo, RedirectAttributes re) {
		u.setUsername(u.getUsername().trim());
		u.setPassword(u.getPassword().trim());
		Session session1 = factory.getCurrentSession();
		Session session2 = factory.openSession();
		u.setId((int) httpSession.getAttribute("uptuid"));

		String hql = String.format("from User where username='%s'", u.getUsername());
		Query query = session1.createQuery(hql);
		List<User> list = query.list();
		if (!list.isEmpty()) {
			if (list.get(0).getId() != u.getId()) {
				re.addFlashAttribute("message", "This username is not available !");
				return "redirect:/admin/user/update/" + u.getId() + ".htm";
			}

		}

		if (photo.getOriginalFilename().isEmpty()) {
			User temp = (User) session1.get(User.class, u.getId());
			u.setAvatar(temp.getAvatar());
		} else if (!(photo.getContentType().contains("jpeg") || photo.getContentType().contains("png"))) {
			re.addFlashAttribute("message", "This file type is not supported !");
		} else {
			try {
				String photoPath = context.getRealPath("/resources/images/avatar/" + photo.getOriginalFilename());
				photo.transferTo(new File(photoPath));
				u.setAvatar("resources/images/avatar/" + photo.getOriginalFilename());
			} catch (Exception e) {
				re.addFlashAttribute("message", "Save file error: " + e);
				return "redirect:/admin/user/update/" + u.getId() + ".htm";
			}
		}
		Transaction t = session2.beginTransaction();
		try {
			session2.update(u);
			t.commit();
			re.addFlashAttribute("message", "Success !");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("message", "Update failed !" + e);
		} finally {
			session2.close();
		}

		return "redirect:/admin/user/update/" + u.getId() + ".htm";

	}

	@RequestMapping("delete/{uid}")
	public String delete(HttpSession httpSession, RedirectAttributes re, @PathVariable("uid") int id) {
		User currentu = (User) httpSession.getAttribute("user");
		if (currentu.getId() != id) {
			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			try {
				session.delete(session.get(User.class, id));
				t.commit();
				re.addFlashAttribute("message", "Deleted !");
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("message", "Delete Failed !");
			} finally {
				session.close();
			}
		}
		return "redirect:/admin/user/table.htm";
	}
}
