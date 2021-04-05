package ptithcm.controller;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ptithcm.entity.User;

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
		model.addAttribute("user", new User());
		return "login";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(ModelMap model, @ModelAttribute("user") User user, BindingResult errors,
			HttpSession httpSession) {
		user.setUsername(user.getUsername().trim());
		user.setPassword(user.getPassword().trim());
		if (user.getUsername().isEmpty()) {
			errors.rejectValue("username", "user", "Please enter your username !");
		} else if (user.getUsername().contains(" ")) {
			errors.rejectValue("username", "user", "Username must not contain space !");
		}
		if (user.getPassword().isEmpty()) {
			errors.rejectValue("password", "user", "Please enter your password !");
		} else if (user.getPassword().contains(" ")) {
			errors.rejectValue("password", "user", "Password must not contain space !");
		}
		if (!errors.hasErrors()) {
			Session session = factory.getCurrentSession();
			String hql = String.format("from User where username='%s' and password='%s'", user.getUsername(),
					user.getPassword());
			System.out.println(hql);
			Query query = session.createQuery(hql);
			List<User> list = query.list();
			if (!list.isEmpty()) {
				System.out.println(list.get(0).getClass());
				httpSession.setAttribute("user", list.get(0));
				if (list.get(0).isAdmin()) {
					return "redirect:/admin/index.htm";
				}
				return "redirect:/index.htm";

			} else {

				errors.rejectValue("username", "user", "Username of password is incorrect !");
				return "login";
			}
		}
		return "login";
	}

	@RequestMapping("logout")
	public String logout(ModelMap model, HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/index.htm";
	}

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String register(ModelMap model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String register(ModelMap model, @ModelAttribute User user, BindingResult errors, HttpSession httpSession) {
		if (user.getUsername().trim().isEmpty()) {
			errors.rejectValue("username", "user", "Please enter your username !");
		} else if (user.getUsername().trim().contains(" ")) {
			errors.rejectValue("username", "user", "Username must not contain space !");
		}
		if (user.getPassword().trim().isEmpty()) {
			errors.rejectValue("password", "user", "Please enter your password !");
		} else if (user.getPassword().trim().contains(" ")) {
			errors.rejectValue("password", "user", "Password must not contain space !");
		}
		if (!user.getEmail().isEmpty()) {
			Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail());
			if (!matcher.find()) {
				errors.rejectValue("email", "user", "Please enter a valid email !");
			}
		}
		if (!errors.hasErrors()) {
			user.setAvatar("resources/images/avatar/user-default.png");
			Session session = factory.getCurrentSession();
			String hql = String.format("from User where username='%s'", user.getUsername());
			Query query = session.createQuery(hql);
			List<User> list = query.list();
			if (list.isEmpty()) {
				Session session2 = factory.openSession();
				Transaction t = session2.beginTransaction();
				user.setAdmin(false);
				try {
					session2.save(user);
					t.commit();
					model.addAttribute("message", "Thêm mới thành công !");
					httpSession.setAttribute("user", user);
					return "redirect:/index.htm";
				} catch (Exception e) {
					t.rollback();
					model.addAttribute("message", "Thêm mới thất bại !" + e);
					return "redirect:/register.htm";
				} finally {
					session2.close();
				}

			} else {
				errors.rejectValue("username", "user", "This username is not available !");
			}

		}
		return "register";
	}

	@RequestMapping("password")
	public String password() {
		return "password";
	}

	@RequestMapping(value = "password", params = "email", method = RequestMethod.GET)
	public String forgotpassword(ModelMap model, @PathParam("email") String email) {
		String hql = String.format("from User where email='%s'", email);
		List<Object> list = getList(hql);
		if (list.isEmpty()) {
			model.addAttribute("message", "No account have this email");
			return "password";
		} else {
			try {
				String body = "This is your account infomation: \n";
				for (int i = 0; i < list.size(); i++) {
					User u = (User) list.get(0);

					body += "Username: " + u.getUsername() + "\nEmail: " + u.getEmail() + "\nPassword: "
							+ u.getPassword() + "\n\n";
				}
//				System.out.println(body);
				// String from = "XGear - PC & Laptop Gaming";
				MimeMessage mail = mailer.createMimeMessage();

				MimeMessageHelper helper = new MimeMessageHelper(mail);
				// helper.setFrom(from, from);
				helper.setTo(email);
				// helper.setReplyTo(from,from);
				helper.setSubject("Forgot Password");
				helper.setText(body, true);

				mailer.send(mail);
			} catch (Exception e) {
				model.addAttribute("message", e);
			}
			model.addAttribute("message", "We have sent the password to your email.");
		}
		return "password";

	}

}
