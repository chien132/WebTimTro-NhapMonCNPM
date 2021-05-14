package ptithcm.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ptithcm.entity.Account;
import ptithcm.entity.ChuTro;
import ptithcm.entity.KhachThue;
import ptithcm.entity.NhaTro;
import ptithcm.entity.Province;
import ptithcm.entity.Role;
import ptithcm.service.ProvinceService;

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

	@ModelAttribute("accounts")
	public List<Object> getAccounts() {
		String hql = "from Account";
		List<Object> list = getList(hql);
		return list;
	}

	@ModelAttribute("chutros")
	public List<Object> getChuTros() {
		String hql = "from ChuTro";
		List<Object> list = getList(hql);
		return list;
	}

	@ModelAttribute("khachthues")
	public List<Object> getKhachThues() {
		String hql = "from KhachThue";
		List<Object> list = getList(hql);
		return list;
	}

	@RequestMapping("")
	public String welcome() {
		return "redirect:/admin/index.htm";
	}

	@RequestMapping("index")
	public String index() {
		return "redirect:/admin/account.htm";
	}

	// Account manager

	@RequestMapping("account")
	public String viewaccount() {
		return "admin/accounttable";
	}

	@RequestMapping(value = "editaccount/{username}", method = RequestMethod.GET)
	public String editaccount(ModelMap modelMap, @PathVariable("username") String username) {
		Session session = factory.getCurrentSession();
		Account account = (Account) session.get(Account.class, username);
		modelMap.addAttribute("account", account);
		modelMap.addAttribute("action", "edit");
		return "admin/accountform";
	}

	@RequestMapping(value = "editaccount", method = RequestMethod.POST)
	public String editaccountpost(@ModelAttribute("account") Account account, RedirectAttributes re,
			BindingResult errors, ModelMap model, @RequestParam("photo") MultipartFile photo) {

		account.setPassword(account.getPassword().trim().split(",")[0]);
		account.setCmnd(account.getCmnd().trim());
		account.setHoTen(account.getHoTen().trim());
		account.setEmail(account.getEmail());
		account.setDienThoai(account.getDienThoai().trim());

		if (account.getPassword().isEmpty()) {
			errors.rejectValue("password", "account", "Hãy nhập mật khẩu !");
		} else if (account.getPassword().contains(" ")) {
			errors.rejectValue("password", "account", "Mật khẩu không được chứa khoảng trắng !");
		}

		if (account.getCmnd().isEmpty()) {
			errors.rejectValue("cmnd", "account", "Hãy nhập CMND !");
		} else if (account.getPassword().contains(" ")) {
			errors.rejectValue("cmnd", "account", "CMND không được chứa khoảng trắng !");
		}

		if (account.getHoTen().isEmpty()) {
			errors.rejectValue("hoTen", "account", "Hãy nhập họ tên !");
		}

		if (!account.getEmail().isEmpty()) {
			Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(account.getEmail());
			if (!matcher.find()) {
				errors.rejectValue("email", "account", "Email không hợp lệ !");
			}
		} else {
			errors.rejectValue("email", "account", "Hãy nhập email !");
		}

		if (!account.getDienThoai().isEmpty()) {
			Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\\b",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(account.getDienThoai());
			Pattern VALID_ID_NUMBER_REGEX = Pattern.compile("([0-9]{9,12})\\b", Pattern.CASE_INSENSITIVE);
			Matcher matcher2 = VALID_ID_NUMBER_REGEX.matcher(account.getDienThoai());
			if (!matcher.find() || !matcher2.find()) {
				errors.rejectValue("dienThoai", "account", "Số điện thoại không hợp lệ !");
			}
		} else {
			errors.rejectValue("dienThoai", "account", "Hãy nhập số điện thoại !");
		}

		if (!errors.hasErrors()) {

			Session session2 = factory.openSession();
			Account oldAccount = (Account) session2.get(Account.class, account.getUsername());
			System.out.println(oldAccount.getUsername());
			oldAccount.setPassword(account.getPassword());
			oldAccount.setHoTen(account.getHoTen());
			oldAccount.setCmnd(account.getCmnd());
			oldAccount.setDienThoai(account.getDienThoai());
			oldAccount.setEmail(account.getEmail());

			Transaction t = session2.beginTransaction();
			try {
				if (photo.getOriginalFilename().isEmpty()) {

				} else if (!(photo.getContentType().contains("jpeg") || photo.getContentType().contains("png"))) {
					model.addAttribute("message", "File ảnh không đúng định dạng !");
					model.addAttribute("account", account);
					model.addAttribute("action", "edit");
					return "admin/accountform";
				} else {
					try {
						String photoPath = context
								.getRealPath("resources/images/avatar/" + account.getUsername() + ".png");
						photo.transferTo(new File(photoPath));
					} catch (Exception e) {
						re.addFlashAttribute("message", "Save file error: " + e);
						return "redirect:/admin/addaccount.htm";
					}
				}

				session2.update(oldAccount);
				t.commit();
				re.addFlashAttribute("message", "Thành công");
				return "redirect:/admin/editaccount/" + account.getUsername() + ".htm";
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("message", "Thất bại: " + e);
				return "redirect:/admin/editaccount/" + account.getUsername() + ".htm";
			} finally {
				session2.close();
			}
		}
		model.addAttribute("account", account);
		model.addAttribute("action", "edit");
		return "admin/accountform";
	}

	@RequestMapping(value = "addaccount", method = RequestMethod.GET)
	public String addaccount(ModelMap modelMap) {
		modelMap.addAttribute("account", new Account());
		modelMap.addAttribute("action", "add");
		return "admin/accountform";
	}

	@RequestMapping(value = "addaccount", method = RequestMethod.POST)
	public String addaccountpost(@ModelAttribute("account") Account account, RedirectAttributes re,
			BindingResult errors, ModelMap model, HttpServletRequest request,
			@RequestParam("photo") MultipartFile photo) {
		account.setUsername(account.getUsername().trim());
		account.setPassword(account.getPassword().trim().split(",")[0]);
		account.setCmnd(account.getCmnd().trim());
		account.setHoTen(account.getHoTen().trim());
		account.setEmail(account.getEmail());
		account.setDienThoai(account.getDienThoai().trim());
		account.setNgayDangKy(new Date());
		if (request.getParameter("roles").isEmpty()) {
			errors.rejectValue("email", "Hãy chọn loại tài khoản !");
		} else {
			Session sessions = factory.getCurrentSession();
			Role role = (Role) sessions.get(Role.class, Integer.parseInt(request.getParameter("roles")));
			account.setRole(role);
		}

		if (account.getUsername().isEmpty()) {
			errors.rejectValue("username", "account", "Hãy nhập username !");
		} else if (account.getUsername().contains(" ")) {
			errors.rejectValue("username", "account", "Username không được chứa khoảng trắng !");
		}

		if (account.getPassword().isEmpty()) {
			errors.rejectValue("password", "account", "Hãy nhập mật khẩu !");
		} else if (account.getPassword().contains(" ")) {
			errors.rejectValue("password", "account", "Mật khẩu không được chứa khoảng trắng !");
		}

		if (account.getCmnd().isEmpty()) {
			errors.rejectValue("cmnd", "account", "Hãy nhập CMND !");
		} else if (account.getPassword().contains(" ")) {
			errors.rejectValue("cmnd", "account", "CMND không được chứa khoảng trắng !");
		}

		if (account.getHoTen().isEmpty()) {
			errors.rejectValue("hoTen", "account", "Hãy nhập họ tên !");
		}

		if (!account.getEmail().isEmpty()) {
			Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(account.getEmail());
			if (!matcher.find()) {
				errors.rejectValue("email", "account", "Email không hợp lệ !");
			}
		} else {
			errors.rejectValue("email", "account", "Hãy nhập email !");
		}

		if (!account.getDienThoai().isEmpty()) {
			Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\\b",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(account.getDienThoai());
			Pattern VALID_ID_NUMBER_REGEX = Pattern.compile("([0-9]{9,12})\\b", Pattern.CASE_INSENSITIVE);
			Matcher matcher2 = VALID_ID_NUMBER_REGEX.matcher(account.getDienThoai());
			if (!matcher.find() || !matcher2.find()) {
				errors.rejectValue("dienThoai", "account", "Số điện thoại không hợp lệ !");
			}
		} else {
			errors.rejectValue("dienThoai", "account", "Hãy nhập số điện thoại !");
		}

		if (!errors.hasErrors()) {
			Session session = factory.getCurrentSession();
			String hql = String.format("from Account where username='%s'", account.getUsername());
			Query query = session.createQuery(hql);
			List<Account> list = query.list();
			if (list.isEmpty()) {
				Session session2 = factory.openSession();
				Transaction t = session2.beginTransaction();
				try {
					if (photo.getOriginalFilename().isEmpty()) {
						Path from = Paths.get(context.getRealPath("/resources/images/avatar/user-default.png"));
						Path to = Paths
								.get(context.getRealPath("/resources/images/avatar/" + account.getUsername() + ".png"));
						Files.copy(from, to);
					} else if (!(photo.getContentType().contains("jpeg") || photo.getContentType().contains("png"))) {
						re.addFlashAttribute("message", "File ảnh không đúng định dạng !");
					} else {
						try {
							String photoPath = context
									.getRealPath("resources/images/avatar/" + account.getUsername() + ".png");
							photo.transferTo(new File(photoPath));
						} catch (Exception e) {
							re.addFlashAttribute("message", "Save file error: " + e);
							return "redirect:/admin/addaccount.htm";
						}
					}

					switch (Integer.parseInt(request.getParameter("roles"))) {
					case 2: {
						KhachThue khachThue = new KhachThue();
						khachThue.setAccount(account);
						account.setKhachThue(khachThue);
						session2.save(account);
						khachThue.setNamSinh(2000);
						session2.save(khachThue);
						break;
					}
					case 1: {
						ChuTro chuTro = new ChuTro();
						chuTro.setAccount(account);
						account.setChuTro(chuTro);
						session2.save(account);
						session2.save(chuTro);
						break;
					}
					}
					t.commit();
					re.addFlashAttribute("message", "Tài khoản đã được tạo thành công");
					return "redirect:/admin/account.htm";
				} catch (Exception e) {
					t.rollback();
					model.addAttribute("message", "Tạo tài khoản không thành công!" + e);
//					model.addAttribute("account", account);
//					model.addAttribute("action", "add");
//					return "admin/accountform";
				} finally {
					session2.close();
				}

			} else {
				errors.rejectValue("username", "account", "This username is available !");
			}
		}
		model.addAttribute("account", account);
		model.addAttribute("action", "add");
		return "admin/accountform";
	}

	@RequestMapping("deleteaccount/{username}")
	public String deleteaccount(HttpSession httpSession, RedirectAttributes re,
			@PathVariable("username") String username) {
		Account currentaAccount = (Account) httpSession.getAttribute("account");
		if (!currentaAccount.getUsername().equals(username)) {
			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			Account account = (Account) session.get(Account.class, username);
			try {
				if (account.getRole().getId() == 1) {
					session.delete(account.getChuTro());
				} else if (account.getRole().getId() == 2) {
					session.delete(account.getKhachThue());
				}
				session.delete(account);
				t.commit();
				re.addFlashAttribute("message", "Đã xóa " + username);
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("message", "Không thể xóa !\n" + e);
			} finally {
				session.close();
			}
		} else {
			re.addFlashAttribute("message", "Không thể xóa chính bạn !");
		}
		return "redirect:/admin/account.htm";
	}

	// Chu tro
	@RequestMapping("chutro")
	public String viewchutro() {
		return "admin/chutrotable";
	}

//	@RequestMapping(value = "editaccount/{username}", method = RequestMethod.GET)
//	public String editaccount(ModelMap modelMap, @PathVariable("username") String username) {
//		Session session = factory.getCurrentSession();
//		Account account = (Account) session.get(Account.class, username);
//		modelMap.addAttribute("account", account);
//		modelMap.addAttribute("action", "edit");
//		return "admin/accountform";
//	}
//
//	@RequestMapping(value = "editaccount", method = RequestMethod.POST)
//	public String editaccountpost(@ModelAttribute("account") Account account, RedirectAttributes re,
//			BindingResult errors, ModelMap model, @RequestParam("photo") MultipartFile photo) {
//
//		account.setPassword(account.getPassword().trim().split(",")[0]);
//		account.setCmnd(account.getCmnd().trim());
//		account.setHoTen(account.getHoTen().trim());
//		account.setEmail(account.getEmail());
//		account.setDienThoai(account.getDienThoai().trim());
//
//		if (account.getPassword().isEmpty()) {
//			errors.rejectValue("password", "account", "Hãy nhập mật khẩu !");
//		} else if (account.getPassword().contains(" ")) {
//			errors.rejectValue("password", "account", "Mật khẩu không được chứa khoảng trắng !");
//		}
//
//		if (account.getCmnd().isEmpty()) {
//			errors.rejectValue("cmnd", "account", "Hãy nhập CMND !");
//		} else if (account.getPassword().contains(" ")) {
//			errors.rejectValue("cmnd", "account", "CMND không được chứa khoảng trắng !");
//		}
//
//		if (account.getHoTen().isEmpty()) {
//			errors.rejectValue("hoTen", "account", "Hãy nhập họ tên !");
//		}
//
//		if (!account.getEmail().isEmpty()) {
//			Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
//					Pattern.CASE_INSENSITIVE);
//			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(account.getEmail());
//			if (!matcher.find()) {
//				errors.rejectValue("email", "account", "Email không hợp lệ !");
//			}
//		} else {
//			errors.rejectValue("email", "account", "Hãy nhập email !");
//		}
//
//		if (!account.getDienThoai().isEmpty()) {
//			Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\\b",
//					Pattern.CASE_INSENSITIVE);
//			Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(account.getDienThoai());
//			Pattern VALID_ID_NUMBER_REGEX = Pattern.compile("([0-9]{9,12})\\b", Pattern.CASE_INSENSITIVE);
//			Matcher matcher2 = VALID_ID_NUMBER_REGEX.matcher(account.getDienThoai());
//			if (!matcher.find() || !matcher2.find()) {
//				errors.rejectValue("dienThoai", "account", "Số điện thoại không hợp lệ !");
//			}
//		} else {
//			errors.rejectValue("dienThoai", "account", "Hãy nhập số điện thoại !");
//		}
//
//		if (!errors.hasErrors()) {
//
//			Session session2 = factory.openSession();
//			Account oldAccount = (Account) session2.get(Account.class, account.getUsername());
//			System.out.println(oldAccount.getUsername());
//			oldAccount.setPassword(account.getPassword());
//			oldAccount.setHoTen(account.getHoTen());
//			oldAccount.setCmnd(account.getCmnd());
//			oldAccount.setDienThoai(account.getDienThoai());
//			oldAccount.setEmail(account.getEmail());
//
//			Transaction t = session2.beginTransaction();
//			try {
//				if (photo.getOriginalFilename().isEmpty()) {
//
//				} else if (!(photo.getContentType().contains("jpeg") || photo.getContentType().contains("png"))) {
//					model.addAttribute("message", "File ảnh không đúng định dạng !");
//					model.addAttribute("account", account);
//					model.addAttribute("action", "edit");
//					return "admin/accountform";
//				} else {
//					try {
//						String photoPath = context
//								.getRealPath("resources/images/avatar/" + account.getUsername() + ".png");
//						photo.transferTo(new File(photoPath));
//					} catch (Exception e) {
//						re.addFlashAttribute("message", "Save file error: " + e);
//						return "redirect:/admin/addaccount.htm";
//					}
//				}
//
//				session2.update(oldAccount);
//				t.commit();
//				re.addFlashAttribute("message", "Thành công");
//				return "redirect:/admin/editaccount/" + account.getUsername() + ".htm";
//			} catch (Exception e) {
//				t.rollback();
//				re.addFlashAttribute("message", "Thất bại: " + e);
//				return "redirect:/admin/editaccount/" + account.getUsername() + ".htm";
//			} finally {
//				session2.close();
//			}
//		}
//		model.addAttribute("account", account);
//		model.addAttribute("action", "edit");
//		return "admin/accountform";
//	}
//
//	@RequestMapping(value = "addaccount", method = RequestMethod.GET)
//	public String addaccount(ModelMap modelMap) {
//		modelMap.addAttribute("account", new Account());
//		modelMap.addAttribute("action", "add");
//		return "admin/accountform";
//	}
//
//	@RequestMapping(value = "addaccount", method = RequestMethod.POST)
//	public String addaccountpost(@ModelAttribute("account") Account account, RedirectAttributes re,
//			BindingResult errors, ModelMap model, HttpServletRequest request,
//			@RequestParam("photo") MultipartFile photo) {
//		account.setUsername(account.getUsername().trim());
//		account.setPassword(account.getPassword().trim().split(",")[0]);
//		account.setCmnd(account.getCmnd().trim());
//		account.setHoTen(account.getHoTen().trim());
//		account.setEmail(account.getEmail());
//		account.setDienThoai(account.getDienThoai().trim());
//		account.setNgayDangKy(new Date());
//		if (request.getParameter("roles").isEmpty()) {
//			errors.rejectValue("email", "Hãy chọn loại tài khoản !");
//		} else {
//			Session sessions = factory.getCurrentSession();
//			Role role = (Role) sessions.get(Role.class, Integer.parseInt(request.getParameter("roles")));
//			account.setRole(role);
//		}
//
//		if (account.getUsername().isEmpty()) {
//			errors.rejectValue("username", "account", "Hãy nhập username !");
//		} else if (account.getUsername().contains(" ")) {
//			errors.rejectValue("username", "account", "Username không được chứa khoảng trắng !");
//		}
//
//		if (account.getPassword().isEmpty()) {
//			errors.rejectValue("password", "account", "Hãy nhập mật khẩu !");
//		} else if (account.getPassword().contains(" ")) {
//			errors.rejectValue("password", "account", "Mật khẩu không được chứa khoảng trắng !");
//		}
//
//		if (account.getCmnd().isEmpty()) {
//			errors.rejectValue("cmnd", "account", "Hãy nhập CMND !");
//		} else if (account.getPassword().contains(" ")) {
//			errors.rejectValue("cmnd", "account", "CMND không được chứa khoảng trắng !");
//		}
//
//		if (account.getHoTen().isEmpty()) {
//			errors.rejectValue("hoTen", "account", "Hãy nhập họ tên !");
//		}
//
//		if (!account.getEmail().isEmpty()) {
//			Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
//					Pattern.CASE_INSENSITIVE);
//			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(account.getEmail());
//			if (!matcher.find()) {
//				errors.rejectValue("email", "account", "Email không hợp lệ !");
//			}
//		} else {
//			errors.rejectValue("email", "account", "Hãy nhập email !");
//		}
//
//		if (!account.getDienThoai().isEmpty()) {
//			Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\\b",
//					Pattern.CASE_INSENSITIVE);
//			Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(account.getDienThoai());
//			Pattern VALID_ID_NUMBER_REGEX = Pattern.compile("([0-9]{9,12})\\b", Pattern.CASE_INSENSITIVE);
//			Matcher matcher2 = VALID_ID_NUMBER_REGEX.matcher(account.getDienThoai());
//			if (!matcher.find() || !matcher2.find()) {
//				errors.rejectValue("dienThoai", "account", "Số điện thoại không hợp lệ !");
//			}
//		} else {
//			errors.rejectValue("dienThoai", "account", "Hãy nhập số điện thoại !");
//		}
//
//		if (!errors.hasErrors()) {
//			Session session = factory.getCurrentSession();
//			String hql = String.format("from Account where username='%s'", account.getUsername());
//			Query query = session.createQuery(hql);
//			List<Account> list = query.list();
//			if (list.isEmpty()) {
//				Session session2 = factory.openSession();
//				Transaction t = session2.beginTransaction();
//				try {
//					if (photo.getOriginalFilename().isEmpty()) {
//						Path from = Paths.get(context.getRealPath("/resources/images/avatar/user-default.png"));
//						Path to = Paths
//								.get(context.getRealPath("/resources/images/avatar/" + account.getUsername() + ".png"));
//						Files.copy(from, to);
//					} else if (!(photo.getContentType().contains("jpeg") || photo.getContentType().contains("png"))) {
//						re.addFlashAttribute("message", "File ảnh không đúng định dạng !");
//					} else {
//						try {
//							String photoPath = context
//									.getRealPath("resources/images/avatar/" + account.getUsername() + ".png");
//							photo.transferTo(new File(photoPath));
//						} catch (Exception e) {
//							re.addFlashAttribute("message", "Save file error: " + e);
//							return "redirect:/admin/addaccount.htm";
//						}
//					}
//
//					switch (Integer.parseInt(request.getParameter("roles"))) {
//					case 2: {
//						KhachThue khachThue = new KhachThue();
//						khachThue.setAccount(account);
//						account.setKhachThue(khachThue);
//						session2.save(account);
//						khachThue.setNamSinh(2000);
//						session2.save(khachThue);
//						break;
//					}
//					case 1: {
//						ChuTro chuTro = new ChuTro();
//						chuTro.setAccount(account);
//						account.setChuTro(chuTro);
//						session2.save(account);
//						session2.save(chuTro);
//						break;
//					}
//					}
//					t.commit();
//					re.addFlashAttribute("message", "Tài khoản đã được tạo thành công");
//					return "redirect:/admin/account.htm";
//				} catch (Exception e) {
//					t.rollback();
//					model.addAttribute("message", "Tạo tài khoản không thành công!" + e);
////					model.addAttribute("account", account);
////					model.addAttribute("action", "add");
////					return "admin/accountform";
//				} finally {
//					session2.close();
//				}
//
//			} else {
//				errors.rejectValue("username", "account", "This username is available !");
//			}
//		}
//		model.addAttribute("account", account);
//		model.addAttribute("action", "add");
//		return "admin/accountform";
//	}
//
//	@RequestMapping("deleteaccount/{username}")
//	public String delete(HttpSession httpSession, RedirectAttributes re, @PathVariable("username") String username) {
//		Account currentaAccount = (Account) httpSession.getAttribute("account");
//		if (currentaAccount.getUsername() != username) {
//			Session session = factory.openSession();
//			Transaction t = session.beginTransaction();
//			Account account = (Account) session.get(Account.class, username);
//			try {
//				if (account.getRole().getId() == 1) {
//					session.delete(account.getChuTro());
//				} else if (account.getRole().getId() == 2) {
//					session.delete(account.getKhachThue());
//				}
//				session.delete(account);
//				t.commit();
//				re.addFlashAttribute("message", "Đã xóa " + username);
//			} catch (Exception e) {
//				t.rollback();
//				re.addFlashAttribute("message", "Không thể xóa !\n" + e);
//			} finally {
//				session.close();
//			}
//		}
//		return "redirect:/admin/account.htm";
//	}

	// NhaTro
	@RequestMapping(value = "nhatro", params = "chu")
	public String viewnhatro(@PathParam("chu") int chu, ModelMap model) {
		String hql = "";
		if (chu == -1) {
			hql = "from NhaTro";
		} else {
			hql = "from NhaTro where chuTro.id='" + chu + "'";
		}
		List<Object> list = getList(hql);
		model.addAttribute("nhatros", list);
		model.addAttribute("chu", chu);
		return "admin/nhatrotable";
	}
	
	@RequestMapping("test")
	public String test(ModelMap model,HttpSession httpSession) {
		model.addAttribute("account", new Account());
		model.addAttribute("provinces",httpSession.getAttribute("provinces"));
		return "admin/nhatroform";
	}

	@RequestMapping(value = "editnhatro/{id}", params = "chu", method = RequestMethod.GET)
	public String editnhatro(ModelMap modelMap, @PathVariable("id") int id, @PathParam("chu") int chu) {
		Session session = factory.getCurrentSession();
		NhaTro nhaTro = (NhaTro) session.get(NhaTro.class, id);
		modelMap.addAttribute("nhatro", nhaTro);
		modelMap.addAttribute("action", "edit");
		String returnString = "";
		if (chu == -1) {
			returnString = "admin/nhatro.htm?chu=-1";
		} else {
			returnString = "admin/nhatro,htm?chu=" + chu;
		}
		modelMap.addAttribute("chu", chu);
		return "admin/nhatroform";
	}

//	@RequestMapping(value = "editaccount", method = RequestMethod.POST)
//	public String editaccountpost(@ModelAttribute("account") Account account, RedirectAttributes re,
//			BindingResult errors, ModelMap model, @RequestParam("photo") MultipartFile photo) {
//
//		account.setPassword(account.getPassword().trim().split(",")[0]);
//		account.setCmnd(account.getCmnd().trim());
//		account.setHoTen(account.getHoTen().trim());
//		account.setEmail(account.getEmail());
//		account.setDienThoai(account.getDienThoai().trim());
//
//		if (account.getPassword().isEmpty()) {
//			errors.rejectValue("password", "account", "Hãy nhập mật khẩu !");
//		} else if (account.getPassword().contains(" ")) {
//			errors.rejectValue("password", "account", "Mật khẩu không được chứa khoảng trắng !");
//		}
//
//		if (account.getCmnd().isEmpty()) {
//			errors.rejectValue("cmnd", "account", "Hãy nhập CMND !");
//		} else if (account.getPassword().contains(" ")) {
//			errors.rejectValue("cmnd", "account", "CMND không được chứa khoảng trắng !");
//		}
//
//		if (account.getHoTen().isEmpty()) {
//			errors.rejectValue("hoTen", "account", "Hãy nhập họ tên !");
//		}
//
//		if (!account.getEmail().isEmpty()) {
//			Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
//					Pattern.CASE_INSENSITIVE);
//			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(account.getEmail());
//			if (!matcher.find()) {
//				errors.rejectValue("email", "account", "Email không hợp lệ !");
//			}
//		} else {
//			errors.rejectValue("email", "account", "Hãy nhập email !");
//		}
//
//		if (!account.getDienThoai().isEmpty()) {
//			Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\\b",
//					Pattern.CASE_INSENSITIVE);
//			Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(account.getDienThoai());
//			Pattern VALID_ID_NUMBER_REGEX = Pattern.compile("([0-9]{9,12})\\b", Pattern.CASE_INSENSITIVE);
//			Matcher matcher2 = VALID_ID_NUMBER_REGEX.matcher(account.getDienThoai());
//			if (!matcher.find() || !matcher2.find()) {
//				errors.rejectValue("dienThoai", "account", "Số điện thoại không hợp lệ !");
//			}
//		} else {
//			errors.rejectValue("dienThoai", "account", "Hãy nhập số điện thoại !");
//		}
//
//		if (!errors.hasErrors()) {
//
//			Session session2 = factory.openSession();
//			Account oldAccount = (Account) session2.get(Account.class, account.getUsername());
//			System.out.println(oldAccount.getUsername());
//			oldAccount.setPassword(account.getPassword());
//			oldAccount.setHoTen(account.getHoTen());
//			oldAccount.setCmnd(account.getCmnd());
//			oldAccount.setDienThoai(account.getDienThoai());
//			oldAccount.setEmail(account.getEmail());
//
//			Transaction t = session2.beginTransaction();
//			try {
//				if (photo.getOriginalFilename().isEmpty()) {
//
//				} else if (!(photo.getContentType().contains("jpeg") || photo.getContentType().contains("png"))) {
//					model.addAttribute("message", "File ảnh không đúng định dạng !");
//					model.addAttribute("account", account);
//					model.addAttribute("action", "edit");
//					return "admin/accountform";
//				} else {
//					try {
//						String photoPath = context
//								.getRealPath("resources/images/avatar/" + account.getUsername() + ".png");
//						photo.transferTo(new File(photoPath));
//					} catch (Exception e) {
//						re.addFlashAttribute("message", "Save file error: " + e);
//						return "redirect:/admin/addaccount.htm";
//					}
//				}
//
//				session2.update(oldAccount);
//				t.commit();
//				re.addFlashAttribute("message", "Thành công");
//				return "redirect:/admin/editaccount/" + account.getUsername() + ".htm";
//			} catch (Exception e) {
//				t.rollback();
//				re.addFlashAttribute("message", "Thất bại: " + e);
//				return "redirect:/admin/editaccount/" + account.getUsername() + ".htm";
//			} finally {
//				session2.close();
//			}
//		}
//		model.addAttribute("account", account);
//		model.addAttribute("action", "edit");
//		return "admin/accountform";
//	}
//
//	@RequestMapping(value = "addaccount", method = RequestMethod.GET)
//	public String addaccount(ModelMap modelMap) {
//		modelMap.addAttribute("account", new Account());
//		modelMap.addAttribute("action", "add");
//		return "admin/accountform";
//	}
//
//	@RequestMapping(value = "addaccount", method = RequestMethod.POST)
//	public String addaccountpost(@ModelAttribute("account") Account account, RedirectAttributes re,
//			BindingResult errors, ModelMap model, HttpServletRequest request,
//			@RequestParam("photo") MultipartFile photo) {
//		account.setUsername(account.getUsername().trim());
//		account.setPassword(account.getPassword().trim().split(",")[0]);
//		account.setCmnd(account.getCmnd().trim());
//		account.setHoTen(account.getHoTen().trim());
//		account.setEmail(account.getEmail());
//		account.setDienThoai(account.getDienThoai().trim());
//		account.setNgayDangKy(new Date());
//		if (request.getParameter("roles").isEmpty()) {
//			errors.rejectValue("email", "Hãy chọn loại tài khoản !");
//		} else {
//			Session sessions = factory.getCurrentSession();
//			Role role = (Role) sessions.get(Role.class, Integer.parseInt(request.getParameter("roles")));
//			account.setRole(role);
//		}
//
//		if (account.getUsername().isEmpty()) {
//			errors.rejectValue("username", "account", "Hãy nhập username !");
//		} else if (account.getUsername().contains(" ")) {
//			errors.rejectValue("username", "account", "Username không được chứa khoảng trắng !");
//		}
//
//		if (account.getPassword().isEmpty()) {
//			errors.rejectValue("password", "account", "Hãy nhập mật khẩu !");
//		} else if (account.getPassword().contains(" ")) {
//			errors.rejectValue("password", "account", "Mật khẩu không được chứa khoảng trắng !");
//		}
//
//		if (account.getCmnd().isEmpty()) {
//			errors.rejectValue("cmnd", "account", "Hãy nhập CMND !");
//		} else if (account.getPassword().contains(" ")) {
//			errors.rejectValue("cmnd", "account", "CMND không được chứa khoảng trắng !");
//		}
//
//		if (account.getHoTen().isEmpty()) {
//			errors.rejectValue("hoTen", "account", "Hãy nhập họ tên !");
//		}
//
//		if (!account.getEmail().isEmpty()) {
//			Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
//					Pattern.CASE_INSENSITIVE);
//			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(account.getEmail());
//			if (!matcher.find()) {
//				errors.rejectValue("email", "account", "Email không hợp lệ !");
//			}
//		} else {
//			errors.rejectValue("email", "account", "Hãy nhập email !");
//		}
//
//		if (!account.getDienThoai().isEmpty()) {
//			Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\\b",
//					Pattern.CASE_INSENSITIVE);
//			Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(account.getDienThoai());
//			Pattern VALID_ID_NUMBER_REGEX = Pattern.compile("([0-9]{9,12})\\b", Pattern.CASE_INSENSITIVE);
//			Matcher matcher2 = VALID_ID_NUMBER_REGEX.matcher(account.getDienThoai());
//			if (!matcher.find() || !matcher2.find()) {
//				errors.rejectValue("dienThoai", "account", "Số điện thoại không hợp lệ !");
//			}
//		} else {
//			errors.rejectValue("dienThoai", "account", "Hãy nhập số điện thoại !");
//		}
//
//		if (!errors.hasErrors()) {
//			Session session = factory.getCurrentSession();
//			String hql = String.format("from Account where username='%s'", account.getUsername());
//			Query query = session.createQuery(hql);
//			List<Account> list = query.list();
//			if (list.isEmpty()) {
//				Session session2 = factory.openSession();
//				Transaction t = session2.beginTransaction();
//				try {
//					if (photo.getOriginalFilename().isEmpty()) {
//						Path from = Paths.get(context.getRealPath("/resources/images/avatar/user-default.png"));
//						Path to = Paths
//								.get(context.getRealPath("/resources/images/avatar/" + account.getUsername() + ".png"));
//						Files.copy(from, to);
//					} else if (!(photo.getContentType().contains("jpeg") || photo.getContentType().contains("png"))) {
//						re.addFlashAttribute("message", "File ảnh không đúng định dạng !");
//					} else {
//						try {
//							String photoPath = context
//									.getRealPath("resources/images/avatar/" + account.getUsername() + ".png");
//							photo.transferTo(new File(photoPath));
//						} catch (Exception e) {
//							re.addFlashAttribute("message", "Save file error: " + e);
//							return "redirect:/admin/addaccount.htm";
//						}
//					}
//
//					switch (Integer.parseInt(request.getParameter("roles"))) {
//					case 2: {
//						KhachThue khachThue = new KhachThue();
//						khachThue.setAccount(account);
//						account.setKhachThue(khachThue);
//						session2.save(account);
//						khachThue.setNamSinh(2000);
//						session2.save(khachThue);
//						break;
//					}
//					case 1: {
//						ChuTro chuTro = new ChuTro();
//						chuTro.setAccount(account);
//						account.setChuTro(chuTro);
//						session2.save(account);
//						session2.save(chuTro);
//						break;
//					}
//					}
//					t.commit();
//					re.addFlashAttribute("message", "Tài khoản đã được tạo thành công");
//					return "redirect:/admin/account.htm";
//				} catch (Exception e) {
//					t.rollback();
//					model.addAttribute("message", "Tạo tài khoản không thành công!" + e);
////					model.addAttribute("account", account);
////					model.addAttribute("action", "add");
////					return "admin/accountform";
//				} finally {
//					session2.close();
//				}
//
//			} else {
//				errors.rejectValue("username", "account", "This username is available !");
//			}
//		}
//		model.addAttribute("account", account);
//		model.addAttribute("action", "add");
//		return "admin/accountform";
//	}
//
//	@RequestMapping("deleteaccount/{username}")
//	public String delete(HttpSession httpSession, RedirectAttributes re, @PathVariable("username") String username) {
//		Account currentaAccount = (Account) httpSession.getAttribute("account");
//		if (currentaAccount.getUsername() != username) {
//			Session session = factory.openSession();
//			Transaction t = session.beginTransaction();
//			Account account = (Account) session.get(Account.class, username);
//			try {
//				if (account.getRole().getId() == 1) {
//					session.delete(account.getChuTro());
//				} else if (account.getRole().getId() == 2) {
//					session.delete(account.getKhachThue());
//				}
//				session.delete(account);
//				t.commit();
//				re.addFlashAttribute("message", "Đã xóa " + username);
//			} catch (Exception e) {
//				t.rollback();
//				re.addFlashAttribute("message", "Không thể xóa !\n" + e);
//			} finally {
//				session.close();
//			}
//		}
//		return "redirect:/admin/account.htm";
//	}

}
