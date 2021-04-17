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

import ptithcm.entity.Account;
import ptithcm.entity.Province;
import ptithcm.service.ProvinceService;

@Transactional
@Controller
public class HomeController {
	@Autowired
	SessionFactory factory;
	@Autowired
	ServletContext context;

	@RequestMapping("taobaidang")
	public String taobaidang(ModelMap modelMap,HttpSession httpSession) {
		modelMap.put("account", new Account());
		return"formbaidang";
	}
	
	@RequestMapping("")
	public String welcome() {
		//return "redirect:/index.htm?page=1&view=grid";
		return "index";
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


	@RequestMapping("index")
	public String index(ModelMap model, HttpSession httpSession) {
//		String hql = "FROM Product";
//		List<Object> list = getList(hql);
//		model.addAttribute("products", list);
//		return "index";
//		return "redirect:/index.htm?page=1&view=grid";
		return "index";
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
}
