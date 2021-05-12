package ptithcm.controller;

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
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

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

import ptithcm.entity.*;
import ptithcm.service.ProvinceService;

@Transactional
@Controller
public class AccountController {
	@Autowired
	SessionFactory factory;

	@Autowired
	JavaMailSender mailer;

	List<Object> getList(String hql) {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery(hql);
		List<Object> list = query.list();
		return list;
	}

	public static boolean isContainSpecialWord(String str) {
		Pattern VALID_INPUT_REGEX = Pattern.compile("[$&+,:;=\\\\\\\\?@#|/'<>.^*()%!-]", Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_INPUT_REGEX.matcher(str);
		return matcher.find();
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(ModelMap model) {
		model.addAttribute("account", new Account());
		return "login";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(ModelMap model, @ModelAttribute("account") Account user, BindingResult errors,
			HttpSession httpSession) {
		user.setUsername(user.getUsername().trim());
		user.setPassword(user.getPassword().trim());
		if (user.getUsername().isEmpty()) {
			errors.rejectValue("username", "account", "Please enter your username !");
		} else if (user.getUsername().contains(" ")) {
			errors.rejectValue("username", "account", "Username must not contain space !");
		} else if (isContainSpecialWord(user.getUsername())) {
			errors.rejectValue("username", "account", "Username must not contain speacial character !");
		}
		if (user.getPassword().isEmpty()) {
			errors.rejectValue("password", "account", "Please enter your password !");
		} else if (user.getPassword().contains(" ")) {
			errors.rejectValue("password", "account", "Password must not contain space !");
		} else if (isContainSpecialWord(user.getPassword())) {
			errors.rejectValue("password", "account", "Password must not contain speacial character !");
		}
		if (!errors.hasErrors()) {
			Session session = factory.getCurrentSession();
			String hql = String.format("from Account where username='%s' and password='%s'", user.getUsername(),
					user.getPassword());
			Query query = session.createQuery(hql);
			List<Account> list = query.list();
			if (!list.isEmpty()) {
				System.out.println(list.get(0).getRole().getName());

				if (list.get(0).getChuTro() != null) {
					httpSession.setAttribute("username", list.get(0).getUsername());
					httpSession.setAttribute("account", list.get(0));
					return "redirect:/chutro/index.htm";
				} else if (list.get(0).getKhachThue() != null) {

					httpSession.setAttribute("username", list.get(0).getUsername());
					httpSession.setAttribute("account", list.get(0));
					return "redirect:/khachthue/index.htm";
				}

				httpSession.setAttribute("username", list.get(0).getUsername());
				httpSession.setAttribute("account", list.get(0));
				return "redirect:/admin/index.htm";

			} else {
				errors.rejectValue("username", "account", "Username of password is incorrect !");
				return "login";
			}
		}
		return "login";
	}

	@RequestMapping("logout")
	public String logout(ModelMap model, HttpSession session) {
		session.removeAttribute("username");
		session.removeAttribute("account");
		return "redirect:/index.htm";
	}

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String register(ModelMap model) {
		model.addAttribute("account", new Account());
		return "register";
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String register(ModelMap model, @ModelAttribute("account") Account user, BindingResult errors,
			HttpSession httpSession, HttpServletRequest request) {
		user.setUsername(user.getUsername().trim());
		user.setPassword(user.getPassword().trim().split(",")[0]);

		user.setCmnd(user.getCmnd().trim());
		user.setEmail(user.getEmail());
		user.setDienThoai(user.getDienThoai().trim());
		user.setNgayDangKy(new Date());
		if (request.getParameter("roles").isEmpty()) {
			errors.rejectValue("email", "Please enter your role !");
		} else {
			Session sessions = factory.getCurrentSession();
			Role role = (Role) sessions.get(Role.class, Integer.parseInt(request.getParameter("roles")));
			user.setRole(role);
		}
		if (user.getUsername().isEmpty()) {
			errors.rejectValue("username", "account", "Please enter your username !");
		} else if (user.getUsername().contains(" ")) {
			errors.rejectValue("username", "account", "Username must not contain space !");
		}
		if (user.getPassword().isEmpty()) {
			errors.rejectValue("password", "account", "Please enter your password !");
		} else if (user.getPassword().contains(" ")) {
			errors.rejectValue("password", "account", "Password must not contain space !");
		}
		if (!user.getEmail().isEmpty()) {
			Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail());
			if (!matcher.find()) {
				errors.rejectValue("email", "account", "Please enter a valid email !");
			}
		} else {
			errors.rejectValue("email", "account", "Please enter your email !");
		}
		if (!user.getDienThoai().isEmpty()) {
			Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\\b",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(user.getDienThoai());
			if (!matcher.find()) {
				errors.rejectValue("dienThoai", "account", "Please enter a valid phone number !");
			}
		} else {
			errors.rejectValue("dienThoai", "account", "Please enter your phone number !");
		}
		if (!user.getDienThoai().isEmpty()) {
			Pattern VALID_ID_NUMBER_REGEX = Pattern.compile("([0-9]{9,12})\\b", Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_ID_NUMBER_REGEX.matcher(user.getDienThoai());
			if (!matcher.find()) {
				errors.rejectValue("cmnd", "account", "Please enter a valid phone number !");
			}
		} else {
			errors.rejectValue("cmnd", "account", "Please enter your phone number !");
		}

		if (!errors.hasErrors()) {
			Session session = factory.getCurrentSession();
			String hql = String.format("from Account where username='%s'", user.getUsername());
			Query query = session.createQuery(hql);
			List<Account> list = query.list();
			if (list.isEmpty()) {
				Session session2 = factory.openSession();
				Transaction t = session2.beginTransaction();
				try {
					Path from = Paths.get("resouce/images/avatar/user-default.png");
					Path to = Paths.get("resouce/images/avatar/" + user.getUsername() + ".png");
					Files.copy(from, to);
					switch (Integer.parseInt(request.getParameter("roles"))) {
					case 1: {
						KhachThue khachThue = new KhachThue();
						khachThue.setAccount(user);
						user.setKhachThue(khachThue);
						session2.save(user);
						khachThue.setNamSinh(2000);
						session2.save(khachThue);
						break;
					}
					case 2: {
						ChuTro chuTro = new ChuTro();
						chuTro.setAccount(user);
						user.setChuTro(chuTro);
						session2.save(user);
						session2.save(chuTro);
						break;
					}
					}
					t.commit();
					model.addAttribute("message",
							"<script>alert('Tài khoản của bạn đã được tạo thành công');</script>");
					httpSession.setAttribute("account", user);
					return "redirect:/login.htm";
				} catch (Exception e) {
					t.rollback();
					model.addAttribute("message", "Tạo tài khoản không thành công!" + e);
					return "redirect:/register.htm";
				} finally {
					session2.close();
				}

			} else {
				errors.rejectValue("username", "account", "This username is available !");
			}

		}
		return "register";
	}

	@RequestMapping("password")
	public String password(ModelMap model) {
		model.addAttribute("account", new Account());
		return "password";
	}

	@RequestMapping(value = "password", method = RequestMethod.POST)
	public String forgotpassword(ModelMap model, @ModelAttribute("account") Account user, BindingResult errors) {
		user.setUsername(user.getUsername().trim());
		user.setEmail(user.getEmail().trim());
		if (user.getUsername().isEmpty()) {
			errors.rejectValue("username", "account", "Please enter your username !");
		} else if (user.getUsername().contains(" ")) {
			errors.rejectValue("username", "account", "Username must not contain space !");
		}
		if (!user.getEmail().isEmpty()) {
			Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail());
			if (!matcher.find()) {
				errors.rejectValue("email", "account", "Please enter a valid email !");
			}
		} else {
			errors.rejectValue("email", "account", "Please enter your email !");
		}

		if (!errors.hasErrors()) {
			String hql = String.format("from Account where username = '%s' and email='%s'", user.getUsername(),
					user.getEmail());
			List<Object> list = getList(hql);
			if (list.isEmpty()) {
				errors.rejectValue("email", "account", "No account have this email!");
				return "password";
			} else {
				try {
					String body = "This is your account infomation: \n";
					for (int i = 0; i < list.size(); i++) {
						Account u = (Account) list.get(0);

						body += "Username: " + u.getUsername() + "\nEmail: " + u.getEmail() + "\nPassword: "
								+ u.getPassword() + "\n\n";
					}
//				System.out.println(body);
					// String from = "XGear - PC & Laptop Gaming";
					MimeMessage mail = mailer.createMimeMessage();

					MimeMessageHelper helper = new MimeMessageHelper(mail);
					// helper.setFrom(from, from);
					helper.setTo(user.getEmail());
					// helper.setReplyTo(from,from);
					helper.setSubject("Forgot Password");
					helper.setText(body, true);

					mailer.send(mail);
				} catch (Exception e) {
					model.addAttribute("message", e);
				}
				model.addAttribute("message", "We have sent the password to your email. ");
			}
		}
		return "password";
	}

	@RequestMapping(value = "account/{username}", method = RequestMethod.GET)
	public String update(ModelMap model, @PathVariable("username") String username) {
		Session session = factory.getCurrentSession();
		Account user = (Account) session.get(Account.class, username);
		model.addAttribute("account", user);
		return "account";
	}

}
