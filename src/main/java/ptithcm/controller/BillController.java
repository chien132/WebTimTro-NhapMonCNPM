package ptithcm.controller;

import java.io.File;
import java.util.Collection;
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

import ptithcm.entity.Bill;
import ptithcm.entity.BillItem;
import ptithcm.entity.Product;

@Transactional
@Controller
@RequestMapping("/admin/bill/")
public class BillController {
	@Autowired
	SessionFactory factory;

	List<Object> getList(String hql) {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery(hql);
		List<Object> list = query.list();
		return list;
	}

	@ModelAttribute("users")
	public List<Object> getusers() {
		String hql = "from User";
		List<Object> list = getList(hql);
		return list;
	}

	@ModelAttribute("products")
	public List<Object> getproducts() {
		String hql = "from Product";
		List<Object> list = getList(hql);
		return list;
	}

	@RequestMapping("table")
	public String table(ModelMap model) {
		String hql = "FROM Bill";
		List<Object> list = getList(hql);
		model.addAttribute("bills", list);
		return "admin/bill-table";
	}

	@RequestMapping("view/{id}")
	public String view(@PathVariable("id") int id, ModelMap model) {
		Session session = factory.getCurrentSession();
		Bill b = (Bill) session.get(Bill.class, id);
		model.addAttribute("b", b);
		model.addAttribute("action", "view");
//		model.addAttribute("bi",b.getBillItems());

		return "admin/bill-form";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("b", new Bill());
		model.addAttribute("action", "add");
		return "admin/bill-form";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(@ModelAttribute("b") Bill b, RedirectAttributes re) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(b);
			t.commit();
			re.addFlashAttribute("message", "Add OK !");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("message", "Add Failed !" + e);
			return "redirect:/admin/bill/add.htm";
		} finally {
			session.close();
		}
		return "redirect:/admin/bill/edititem/" + b.getId() + ".htm";
	}

	@RequestMapping(value = "update/{bid}", method = RequestMethod.GET)
	public String update(ModelMap model, @PathVariable("bid") int id, HttpSession httpSession) {
		Session session = factory.getCurrentSession();
		Bill b = (Bill) session.get(Bill.class, id);
		model.addAttribute("b", b);
		model.addAttribute("action", "update");
		httpSession.setAttribute("uptbid", id);
		return "admin/bill-form";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@ModelAttribute("b") Bill b, HttpSession httpSession, RedirectAttributes re) {
		Session session = factory.openSession();
		b.setId((int) httpSession.getAttribute("uptbid"));
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
		return "redirect:/admin/bill/update/" + b.getId() + ".htm";
	}
	
	@RequestMapping("complete/{id}")
	public String complete(@PathVariable("id") int id) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		Bill bill=(Bill) session.get(Bill.class, id);
		bill.setStatus(true);
		try {
			session.update(bill);
			t.commit();
		} catch (Exception e) {
			t.rollback();
		} finally {
			session.close();
		}
		return "redirect:/admin/bill/table.htm";
	}
	@RequestMapping("delete/{id}")
	public String delete(RedirectAttributes re, @PathVariable("id") int id) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(session.get(Bill.class, id));
			t.commit();
			re.addFlashAttribute("message", "Deleted !");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("message", "Please delete bill items first !");
		} finally {
			session.close();
		}
		return "redirect:/admin/bill/table.htm";
	}

	// Bill Items

	@RequestMapping(value = "edititem/{id}", method = RequestMethod.GET)
	public String edititem(ModelMap model, @PathVariable("id") int id, HttpSession httpSession, RedirectAttributes re) {
		Session session = factory.getCurrentSession();
		Bill b = (Bill) session.get(Bill.class, id);
		model.addAttribute("b", b);
		model.addAttribute("action", "edititem");
		model.addAttribute("bi", new BillItem());
		httpSession.setAttribute("uptbid", id);
		return "admin/bill-form";
	}

	@RequestMapping(value = "edititem/{id}", method = RequestMethod.POST)
	public String edititem(ModelMap model, @PathVariable("id") int id, @ModelAttribute("bi") BillItem bi,
			RedirectAttributes re) {
		Session session = factory.getCurrentSession();
		Bill b = (Bill) session.get(Bill.class, id);
		Product p = (Product) session.get(Product.class, bi.getProduct().getId());
		bi.setBill(b);
		Session session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		Collection<BillItem> listitem = b.getBillItems();
		boolean exist = false;
		for (BillItem i : listitem) {
			if (bi.getProduct().getId() == i.getProduct().getId()) {
				bi.setId(i.getId());
				bi.setBill(i.getBill());
				bi.setAmount(i.getAmount() + bi.getAmount());
				exist = true;
			}
		}
		if (bi.getAmount() > p.getQuantity()) {
			re.addFlashAttribute("message", "We still have " + p.getQuantity() + " " + p.getName());
		} else {
			try {
				if (exist) {
					session2.update(bi);
				} else {
					session2.save(bi);
				}
				t.commit();
				re.addFlashAttribute("message", "Add Item OK !");
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("message", "Add Item Failed !" + e);
			} finally {
				session2.close();
			}
		}

		return "redirect:/admin/bill/edititem/" + b.getId() + ".htm";
	}

	@RequestMapping("edititem/{bid}/delete/{iid}")
	public String deleteitem(RedirectAttributes re, @PathVariable("bid") int bid, @PathVariable("iid") int iid) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(session.get(BillItem.class, iid));
			t.commit();
			re.addFlashAttribute("message", "Deleted !");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("message", "Delete Failed !");
		} finally {
			session.close();
		}
		return "redirect:/admin/bill/edititem/" + bid + ".htm";
	}

}
