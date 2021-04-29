package ptithcm.controller;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.SessionScope;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ptithcm.entity.*;

@Transactional
@Controller
public class UserController {
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
		}
		if (user.getPassword().isEmpty()) {
			errors.rejectValue("password", "account", "Please enter your password !");
		} else if (user.getPassword().contains(" ")) {
			errors.rejectValue("password", "account", "Password must not contain space !");
		}
		if (!errors.hasErrors()) {
			Session session = factory.getCurrentSession();
			String hql = String.format("from Account where username='%s' and password='%s'", user.getUsername(),
					user.getPassword());
			System.out.println(hql);
			Query query = session.createQuery(hql);
			List<Account> list = query.list();
			if (!list.isEmpty()) {
				System.out.println(list.get(0).getRole().getName());

				if (list.get(0).getRole().getId() == 1) {
					httpSession.setAttribute("account", list.get(0).getChuTro());
					return "redirect:/index.htm";
				}
				httpSession.setAttribute("account", list.get(0).getKhachThue());
				return "redirect:/index.htm";

			} else {

				errors.rejectValue("username", "account", "Username of password is incorrect !");
				return "login";
			}
		}
		return "login";
	}

	@RequestMapping("logout")
	public String logout(ModelMap model, HttpSession session) {
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
			HttpSession httpSession, HttpServletRequest request, RedirectAttributes re) {
		user.setUsername(user.getUsername().trim());
		user.setPassword(user.getPassword().trim());
		user.setHoTen(user.getHoTen().trim());
		user.setCmnd(user.getCmnd().trim());
		user.setEmail(user.getEmail());
		user.setDienThoai(user.getDienThoai().trim());
		user.setNgayDangKy(new Date());
		System.out.println(Integer.parseInt(request.getParameter("rolef")));
		if (request.getParameter("rolef").isEmpty()) {
			errors.rejectValue("email", "Please enter your role !");
		} else {
			Session sessions = factory.getCurrentSession();
			Role role = (Role) sessions.get(Role.class, Integer.parseInt(request.getParameter("rolef")));
			user.setRole(role);
		}

		if (user.getUsername().isEmpty()) {
			errors.rejectValue("username", "account", "Hãy nhập username !");
		} else if (user.getUsername().contains(" ")) {
			errors.rejectValue("username", "account", "Username không được chứa khoảng trắng !");
		}
		if (user.getPassword().isEmpty()) {
			errors.rejectValue("password", "account", "Hãy nhập password !");
		} else if (user.getPassword().contains(" ")) {
			errors.rejectValue("password", "account", "Password không được chứa khoảng trắng !");
		}
		if (user.getHoTen().isEmpty()) {
			errors.rejectValue("hoTen", "account", "Hãy nhập họ tên !");
		}
		if (!user.getEmail().isEmpty()) {
			Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail());
			if (!matcher.find()) {
				errors.rejectValue("email", "account", "Email chưa đúng định dạng !");
			}
		} else {
			errors.rejectValue("email", "account", "Hãy nhập email !");
		}
		if (!user.getDienThoai().isEmpty()) {
			Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\\b",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(user.getDienThoai());
			if (!matcher.find()) {
				errors.rejectValue("dienThoai", "account", "Số điện thoại không đúng định dạng !");
			}
		} else {
			errors.rejectValue("dienThoai", "account", "Hãy nhập số điện thoại !");
		}
		if (!user.getDienThoai().isEmpty()) {
			Pattern VALID_ID_NUMBER_REGEX = Pattern.compile("([0-9]{9,12})\\b", Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_ID_NUMBER_REGEX.matcher(user.getDienThoai());
			if (!matcher.find()) {
				errors.rejectValue("cmnd", "account", "Số điện thoại không đúng định dạng !");
			}
		} else {
			errors.rejectValue("cmnd", "account", "Hãy nhập số điện thoại !");
		}
		if (!errors.hasErrors()) {
			System.out.println("Khong co loi");
			user.setAvatar("resources/images/avatar/user-default.png");
			Session session = factory.getCurrentSession();
			String hql = String.format("from Account where username='%s'", user.getUsername());
			Query query = session.createQuery(hql);
			List<Account> list = query.list();
			if (list.isEmpty()) {
				Session session2 = factory.openSession();
				Transaction t = session2.beginTransaction();
				// user.setRole(null);
				try {
					System.out.println(user.getRole().getId());
					switch (user.getRole().getId()) {
					case 2: {
						System.out.println("khach thue");
						KhachThue khachThue = new KhachThue();
						khachThue.setNamSinh(1950);
						khachThue.setAccount(user);
						user.setKhachThue(khachThue);
						session2.save(user);
						session2.save(khachThue);
						break;
					}
					case 1: {
						System.out.println("Chu tro");
						ChuTro chuTro = new ChuTro();
						chuTro.setAccount(user);
						user.setChuTro(chuTro);
						session2.save(user);
						session2.save(chuTro);
						break;
					}
					}
					t.commit();
					re.addFlashAttribute("message",
							"<script>alert('Tài khoản của bạn đã được tạo thành công');</script>");
					httpSession.setAttribute("account", user);
					System.out.println("thanh cong");
					return "redirect:/index.htm";
				} catch (Exception e) {
					t.rollback();
					model.addAttribute("message", "Tạo tài khoản không thành công!\n" + e);
					System.out.println("loi " + e);
					model.put("account", user);
					return "register";
				} finally {
					session2.close();
				}

			} else {
				errors.rejectValue("username", "account", "This username is not available !");
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
				model.addAttribute("message", "<script>alert('We have sent the password to your email.';</script>");
			}

		}
		return "password";
	}
}
