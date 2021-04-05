package ptithcm.controller;

import java.io.File;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

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

import ptithcm.entity.Bill;
import ptithcm.entity.BillItem;
import ptithcm.entity.Product;
import ptithcm.entity.Province;
import ptithcm.entity.User;
import ptithcm.service.ProvinceService;

@Transactional
@Controller
public class HomeController {
	@Autowired
	SessionFactory factory;
	@Autowired
	ServletContext context;

	@RequestMapping("taobaidang")
	public String taobaidang(ModelMap modelMap) {
		modelMap.put("user",new User());
		return"formbaidang";
	}
	
	@RequestMapping("")
	public String welcome() {
		return "redirect:/index.htm?page=1&view=grid";
	}

	List<Object> getList(String hql) {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery(hql);
		List<Object> list = query.list();
		return list;
	}
	
	@ModelAttribute("provinces")
	public List<Province> getProvinces(){
		return ProvinceService.findAll(factory);
	}

	@ModelAttribute("ubills")
	public List<Object> getbills(HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("user");
		if (user != null) {
			String hql = "from Bill where user.id=" + user.getId();
			List<Object> list = getList(hql);
//			System.out.println(hql + list.size());
			return list;
		}
		return null;
	}

	@ModelAttribute("categories")
	public List<Object> getcates() {
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
	public List<Object> getbrands() {
		String hql = "from Brand";
		List<Object> list = getList(hql);
		return list;
	}

	@ModelAttribute("cart")
	public Bill getcart(HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("user");
		if (user != null) {
			Session session = factory.getCurrentSession();
			User u = (User) session.get(User.class, user.getId());
			Collection<Bill> listBills = u.getBills();
			for (Bill i : listBills) {
				if (!i.isPaid()) {
					return i;
				}
			}
		}
		return null;
	}

	@ModelAttribute("cartitem")
	public Collection<BillItem> getcartitem(HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("user");
		if (user != null) {
			Session session = factory.getCurrentSession();
			User u = (User) session.get(User.class, user.getId());
			Collection<Bill> listBills = u.getBills();
			for (Bill i : listBills) {
				if (!i.isPaid()) {
					return i.getBillItems();
				}
			}
		}
		return null;
	}

	@RequestMapping("index")
	public String index(ModelMap model, HttpSession httpSession) {
//		String hql = "FROM Product";
//		List<Object> list = getList(hql);
//		model.addAttribute("products", list);
//		return "index";
		return "redirect:/index.htm?page=1&view=grid";
	}

	public void loadindex(ModelMap model, String query, Integer page, String view) {
		query += " order by name";
		List<Object> list = getList(query);
		model.addAttribute("products", list.subList((page - 1) * 8, (page * 8 > list.size() ? list.size() : page * 8)));
		model.addAttribute("page", page);
		model.addAttribute("maxpage", Math.ceil(list.size() / 8.0));
		model.addAttribute("view", view);
//		System.out.println(list.size());
//		model.addAttribute("search", search);
//		model.addAttribute("brand", brandid);
//		model.addAttribute("cate", cateid);
	}

	@RequestMapping(value = "index", params = { "page", "view" })
	public String indexpage(ModelMap model, @PathParam("view") String view, @PathParam("page") Integer page) {
		String hql = "From Product";
		loadindex(model, hql, page, view);
		return "index";
	}

	@RequestMapping(value = "index", params = { "search", "page", "view" }, method = RequestMethod.GET)
	public String searchproduct(@PathParam("search") String search, @PathParam("page") Integer page,
			@PathParam("view") String view, ModelMap model) {
		String hql = "From Product p where p.name like '%" + search + "%'";
		loadindex(model, hql, page, view);
		model.addAttribute("search", search);
		return "index";
	}

	@RequestMapping(value = "index", params = { "page", "view", "brand" }, method = RequestMethod.GET)
	public String indexbrand(ModelMap model, @PathParam("brand") String brand, @PathParam("page") Integer page,
			@PathParam("view") String view) {
		String hql = String.format("FROM Product p where p.brand.id=%s", brand);
		loadindex(model, hql, 1, view);
		model.addAttribute("brand", brand);
		return "index";
	}

	@RequestMapping(value = "index", params = { "page", "view", "cate" }, method = RequestMethod.GET)
	public String indexcate(ModelMap model, @PathParam("cate") String cate, @PathParam("page") Integer page,
			@PathParam("view") String view) {
		String hql = String.format("FROM Product p where p.category.id=%s", cate);
		loadindex(model, hql, 1, view);
		model.addAttribute("cate", cate);
		return "index";
	}
//	@RequestMapping(value = "brand/{id}", method = RequestMethod.GET)
//	public String indexbrand(ModelMap model, @PathVariable("id") String id) {
//		String hql = String.format("FROM Product p where p.brand.id=%s", id);
//		loadindex(model, hql, 1, "grid");
//		return "index";
//	}
//
//	@RequestMapping(value = "category/{id}", method = RequestMethod.GET)
//	public String indexcate(ModelMap model, @PathVariable("id") String id) {
//		String hql = String.format("FROM Product p where p.category.id=%s", id);
//		loadindex(model, hql, 1, "grid");
//		return "index";
//	}

	@RequestMapping(value = "details/{pid}", method = RequestMethod.GET)
	public String details(ModelMap model, @PathVariable("pid") int pid) {
		Session session = factory.getCurrentSession();
		/*
		 * String hql = String.format("FROM Product p where p.id='%s'", pid);
		 * List<Object> list=getList(hql);
		 */
		model.addAttribute("indetail", 1);
		Product p = (Product) session.get(Product.class, pid);
		p.setDes(p.getDes().replaceAll("\n", "<br>"));
		model.addAttribute("p", p);
		return "details";
	}

	@RequestMapping(value = "addtocart/{id}/{qty}/{ret}", method = RequestMethod.POST)
	public String addtocart(ModelMap model, @PathVariable("qty") int qty, @PathVariable("ret") String ret,
			@PathVariable("id") int id, HttpSession httpSession) {
		if (ret.equals("details"))
			ret += "/" + id;
		User httpuser = (User) httpSession.getAttribute("user");
		if (httpuser == null)
			return "redirect:/login.htm";

		Bill bill = null;
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		User user = (User) session.get(User.class, httpuser.getId());
		Collection<Bill> listBills = user.getBills();
		for (Bill i : listBills) {
			if (!i.isPaid()) {
				bill = i;
				break;
			}
		}
		if (bill == null) {
			bill = new Bill();
			bill.setUser(user);
			bill.setPaid(false);
			bill.setStatus(false);
			try {
				session.save(bill);
			} catch (Exception e) {
				t.rollback();
				return "redirect:/" + ret + ".htm";
			}
		}
		Product p = (Product) session.get(Product.class, id);
		BillItem bi = new BillItem();
		bi.setBill(bill);
		bi.setAmount(0);
		bi.setProduct(p);
		boolean exist = false;

		if (bill.getBillItems() != null) {
			Collection<BillItem> listitem = bill.getBillItems();
			for (BillItem i : listitem) {
				if (bi.getProduct().getId() == i.getProduct().getId()) {
					bi = i;
					exist = true;
				}
			}
		}

		if (bi.getAmount() + qty > p.getQuantity()) {
			bi.setAmount(bi.getProduct().getQuantity());
			t.commit();
		} else {
			bi.setAmount(bi.getAmount() + qty);
			try {
				if (exist) {
					session.update(bi);
				} else {
					session.save(bi);
				}
				t.commit();
			} catch (Exception e) {
				System.out.println(e);
				t.rollback();
			} finally {
				session.close();
			}
		}
		return "redirect:/" + ret + ".htm";
	}

	@RequestMapping(value = "removeitem/{item}/{ret}")
	public String deleteitem(@PathVariable("item") int iid, @PathVariable("ret") String ret) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		BillItem bi = (BillItem) session.get(BillItem.class, iid);
		try {
			session.delete(bi);
			t.commit();
		} catch (Exception e) {
			System.out.println(e);
			t.rollback();
		} finally {
			session.close();
		}
		if (ret.equals("details"))
			ret = "details/" + bi.getProduct().getId();
		return "redirect:/" + ret + ".htm";
	}

	@RequestMapping(value = "edititem/{item}/{number}")
	public String edititem(@PathVariable("item") int iid, @PathVariable("number") int number) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		BillItem bi = (BillItem) session.get(BillItem.class, iid);
		if (bi.getAmount() > bi.getProduct().getQuantity()) {
			bi.setAmount(bi.getProduct().getQuantity());
		}
		if ((bi.getAmount() + number) >= 0 && (bi.getAmount() + number) <= bi.getProduct().getQuantity()) {
			bi.setAmount(bi.getAmount() + number);
		}
		try {
			if (bi.getAmount() == 0) {
				session.delete(bi);
			} else {
				session.update(bi);
			}
			t.commit();
		} catch (Exception e) {
			System.out.println(e);
			t.rollback();
		} finally {
			session.close();
		}
		return "redirect:/cart.htm";
	}

	@RequestMapping("cart")
	public String cart(HttpSession httpSession) {
		User u = (User) httpSession.getAttribute("user");
		if (u == null) {
			return "redirect:/login.htm";
		}
		return "cart";
	}

	@RequestMapping(value = "adddetail/{id}", params = "qty", method = RequestMethod.POST)
	public String addtocart(ModelMap model, @PathParam("qty") int qty, @PathVariable("id") int id,
			HttpSession httpSession) {
		String ret = "details/" + id;
		User httpuser = (User) httpSession.getAttribute("user");
		if (httpuser == null)
			return "redirect:/login.htm";

		Bill bill = null;
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		User user = (User) session.get(User.class, httpuser.getId());
		Collection<Bill> listBills = user.getBills();
		for (Bill i : listBills) {
			if (!i.isPaid()) {
				bill = i;
				break;
			}
		}
		if (bill == null) {
			bill = new Bill();
			bill.setUser(user);
			bill.setPaid(false);
			bill.setStatus(false);
			try {
				session.save(bill);
			} catch (Exception e) {
				t.rollback();
				return "redirect:/" + ret + ".htm";
			}
		}
		Product p = (Product) session.get(Product.class, id);
		BillItem bi = new BillItem();
		bi.setBill(bill);
		bi.setAmount(0);
		bi.setProduct(p);
		boolean exist = false;

		if (bill.getBillItems() != null) {
			Collection<BillItem> listitem = bill.getBillItems();
			for (BillItem i : listitem) {
				if (bi.getProduct().getId() == i.getProduct().getId()) {
					bi = i;
					exist = true;
				}
			}
		}

		if (bi.getAmount() + qty > p.getQuantity()) {
			bi.setAmount(bi.getProduct().getQuantity());
			t.commit();
		} else {
			bi.setAmount(bi.getAmount() + qty);
			try {
				if (exist) {
					session.update(bi);
				} else {
					session.save(bi);
				}
				t.commit();
			} catch (Exception e) {
				System.out.println(e);
				t.rollback();
			} finally {
				session.close();
			}
		}
		return "redirect:/" + ret + ".htm";
	}

	@RequestMapping("billlist")
	public String billlist(HttpSession httpSession) {
		User u = (User) httpSession.getAttribute("user");

		if (u.getBills() != null) {
			for (Bill i : u.getBills()) {
				if (i.isPaid()) {
					return "redirect:/checkout.htm?id=" + i.getId();
				}
			}
		}
		return "redirect:/index.htm";
	}

	@RequestMapping(value = "checkout.htm", params = "id")
	public String checkout(ModelMap model, @PathParam("id") int id, RedirectAttributes re) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		Bill bill = (Bill) session.get(Bill.class, id);
		if (bill.isPaid() == true) {
			model.addAttribute("bill", bill);
			return "billdetails";
		}
		if (bill.getBillItems().size() == 0) {
			re.addFlashAttribute("message", "Your cart is empty !");
			return "redirect:/cart.htm";
		}
		for (BillItem i : bill.getBillItems()) {
			i.getProduct().setQuantity(i.getProduct().getQuantity() - i.getAmount());
		}
		bill.setPaid(true);
		bill.setBuydate(new java.util.Date());
		try {
			session.update(bill);
			t.commit();
		} catch (Exception e) {
			t.rollback();
		} finally {
			session.close();
		}
		model.addAttribute("bill", bill);
		return "billdetails";
	}

	@RequestMapping(value = "profile", method = RequestMethod.GET)
	public String profile(ModelMap model, HttpSession httpSession) {
		User u = (User) httpSession.getAttribute("user");
		model.addAttribute("user",u);
		return "profile";
	}

	@RequestMapping(value = "editprofile", method = RequestMethod.POST)
	public String update(@ModelAttribute("user") User u, HttpSession httpSession,
			@RequestParam("photo") MultipartFile photo, RedirectAttributes re) {
		User prev = (User) httpSession.getAttribute("user");
		Session session1 = factory.getCurrentSession();
		Session session2 = factory.openSession();
		u.setId(prev.getId());
		u.setAdmin(prev.isAdmin());

		String hql = String.format("from User where username='%s'", u.getUsername());
		Query query = session1.createQuery(hql);
		List<User> list = query.list();
		if (!list.isEmpty()) {
			if (list.get(0).getId() != u.getId()) {
				re.addFlashAttribute("message", "This username is not available !");
				return "redirect:/profile.htm";
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
				return "redirect:/profile.htm";
			}
		}
		Transaction t = session2.beginTransaction();
		try {
			session2.update(u);
			t.commit();
			httpSession.setAttribute("user", u);
			re.addFlashAttribute("message", "Success !");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("message", "Update failed !" + e);
		} finally {
			session2.close();
		}
		return "redirect:/profile.htm";

	}
}
