package ptithcm.controller;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
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

import ptithcm.entity.Account;
import ptithcm.entity.Comment;
import ptithcm.entity.DiaChi;
import ptithcm.entity.LichHen;
import ptithcm.entity.NhaTro;
import ptithcm.entity.Province;
import ptithcm.entity.ThongBao;
import ptithcm.entity.Ward;
import ptithcm.service.ProvinceService;

@Transactional
@Controller
@RequestMapping("/chutro/")
public class ChuTroController {
	@Autowired
	ServletContext context;
	
	@Autowired
	SessionFactory factory;
	
	@RequestMapping("")
	public String welcome() {
		return "redirect:index.htm";
	}
	
	@ModelAttribute("provinces")
	public Object getProvinces(HttpSession session){
		return session.getAttribute("provinces");
	}
	
	@ModelAttribute("user")
	public Account user(HttpSession session) {
		Session session2 = factory.getCurrentSession();
		return (Account) session2.get(Account.class, (String) session.getAttribute("username"));
	}
	
	List<Object> getList(String hql) {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery(hql);
		List<Object> list = query.list();
		session.clear();
		return list;
	}
	// ????? th??ng tin th??ng b??o t??? session
	@ModelAttribute("thongbaos")
	public <T> Collection<ThongBao> getThongBao(HttpSession session, ModelMap model) {
		Session session2 = factory.getCurrentSession();
		Account account = (Account) session.getAttribute("account");
		String tblichhen = "H??m nay b???n c?? m???t bu???i h???n xem ph??ng!";
		for (ThongBao tb : account.getThongBao()) {
			for (LichHen lichhen : account.getChuTro().getLichHen()) {
				if (lichhen.getThoigian().equals(new Date())&&!tb.getThongbao().equals(tblichhen)) {
					ThongBao thongbao = new ThongBao();
					thongbao.setAccount(account);
					thongbao.setThoigian(new Date());
					thongbao.setThongbao(tblichhen);
					thongbao.setLink("/chutro/lichhen.htm");
					Session session3 = factory.openSession();
					Transaction t = session3.beginTransaction();
					try {
						session3.save(thongbao);
						t.commit();
					} catch (Exception e) {
						t.rollback();
						model.addAttribute("error", "L???i!" + e);
					}
					break;
				}
			}
		}
		List<ThongBao> thongbao = (List<ThongBao>) account.getThongBao();
		Collections.sort(thongbao);
		return thongbao;
	}
	@RequestMapping("index")
	public String index(HttpSession session, ModelMap model) {
		return "chutro/index";
	}
	@RequestMapping("nhatro/{id}")
	public String nhatro(ModelMap model, @PathVariable("id") int id, HttpSession session) {
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		model.addAttribute("nhatro", nhatro);
		return "chutro/nhatro";
	}
	@RequestMapping("nhatro/xoa/{id}")
	public String xoanhatro(RedirectAttributes re, HttpSession session, @PathVariable("id") int id) {
		if(session.getAttribute("role").equals("2")) {
			re.addFlashAttribute("error", "B???n kh??ng c?? quy???n th???c hi???n t??nh n??ng n??y");
		}
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		if(nhatro!=null) {
			session2.clear();
			session2 = factory.openSession();
			Transaction t = session2.beginTransaction();
			try {
				session2.delete(nhatro);
				t.commit();
				re.addFlashAttribute("success", "???? x??a b??i ????ng s??? " + String.valueOf(id));
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("error", "L???i!" + e);
			}
		}
		else {
			re.addFlashAttribute("error", "????y kh??ng ph???i b??i vi???t c???a b???n! ");
		}
		return "redirect:../../index.htm";
	}
	@RequestMapping("nhatro/an/{id}")
	public String annhatro(RedirectAttributes re, HttpSession session, @PathVariable("id") int id) {
		if(session.getAttribute("role").equals("2")) {
			re.addFlashAttribute("error", "B???n kh??ng c?? quy???n th???c hi???n t??nh n??ng n??y");
		}
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		if(nhatro!=null) {
			session2.clear();
			session2 = factory.openSession();
			Transaction t = session2.beginTransaction();
			try {
				nhatro.setTinhtrang(0);
				session2.update(nhatro);
				t.commit();
				re.addFlashAttribute("success", "???? ???n b??i ????ng s??? " + String.valueOf(id));
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("error", "L???i!" + e);
			}
		} else {
			re.addFlashAttribute("error", "????y kh??ng ph???i b??i vi???t c???a b???n! ");
		}
		return "redirect:../"+id+".htm";
	}
	@RequestMapping(value="taobaidang", method = RequestMethod.GET)
	public String taobaidang(ModelMap model, HttpSession session) {
		model.addAttribute("featureid", 0);
		model.addAttribute("featurename", "taobaidang");
		return "chutro/them";
	}
	
	@RequestMapping(value="taobaidang", method=RequestMethod.POST)
	public String taobaidang(RedirectAttributes re, HttpSession session,
			@RequestParam("tieuDe") String tieuDe,
			@RequestParam("diachi") String diachi,
			@RequestParam("ward") int ward,
			@RequestParam("district") int district,
			@RequestParam("province") int province,
			@RequestParam("soPhongChoThue") int soPhongChoThue,
			@RequestParam("soNguoiTrenPhong") int soNguoiTrenPhong,
			@RequestParam("soPhongCoSan") int soPhongCoSan,
			@RequestParam("dienTich") float dienTich,
			@RequestParam("tienCoc") BigDecimal tienCoc,
			@RequestParam("tienThue") BigDecimal tienThue,
			@RequestParam("mota") String mota,
			@RequestParam("anh1") MultipartFile anh1,
			@RequestParam("anh2") MultipartFile anh2) {
		Session session2 = factory.getCurrentSession();
		tieuDe = tieuDe.trim();
		diachi = diachi.trim();
		//Ki???m tra String tr???ng
		if(tieuDe.isBlank()) {
			re.addAttribute("error", "Ti??u ????? tr???ng!");
			return "redirect:taobaidang.htm";
		}
		if(diachi.isBlank()) {
			re.addAttribute("error", "S??? nh?? tr???ng!");
			return "redirect:taobaidang.htm";
		}
		//Ki???m tra ?????a ch???
		Ward w = (Ward) session2.get(Ward.class, ward);
		if(w==null) {
			re.addAttribute("error", "Sai ?????a ch???!");
			return "redirect:taobaidang.htm";
		} else if(w.getDistrict().getId()!=district) {
			re.addAttribute("error", "Sai ?????a ch???!");
			return "redirect:taobaidang.htm";
		} else if(w.getDistrict().getProvince().getId()!=province) {
			re.addAttribute("error", "Sai ?????a ch???!");
			return "redirect:taobaidang.htm";
		} else {
			String hql = "FROM DiaChi WHERE diaChi='" + diachi+"'";
			List<Object> list = getList(hql);
			if(!list.isEmpty()) {
				re.addAttribute("error", "Tr??ng s??? nh??!");
				return "redirect:taobaidang.htm";
			}
		}
		//Ki???m tra s???
		if(soPhongChoThue<0||soNguoiTrenPhong<0||soPhongCoSan<0||dienTich<0||tienCoc.compareTo(new BigDecimal(0))<0||tienThue.compareTo(new BigDecimal(0))<0) {
			re.addAttribute("error", "Kh??ng nh???p s??? ??m!");
			return "redirect:taobaidang.htm";
		}
		//Ki???m tra ???nh
		if(anh1.isEmpty()||anh2.isEmpty()) {
			re.addFlashAttribute("error", "Thi???u ???nh ti??u ?????");
			return "redirect:taobaidang.htm";
		}
		Account account = (Account) session.getAttribute("account");
		if(account.getChuTro()!=null) {
			NhaTro nhatro = new NhaTro();
			nhatro.setChuTro(account.getChuTro());
			DiaChi diaChi = new DiaChi();
			diaChi.setDiaChi(diachi);
			diaChi.setWard(w);
			diaChi.setNhaTro(nhatro);
			nhatro.setDiachi(diaChi);
			nhatro.setTieuDe(tieuDe);
			nhatro.setDienTich(dienTich);
			nhatro.setNgayThem(new Date());
			nhatro.setSoNguoiTrenPhong(soNguoiTrenPhong);
			nhatro.setSoPhongChoThue(soPhongChoThue);
			nhatro.setSoPhongCoSan(soPhongCoSan);
			nhatro.setTienCoc(tienCoc);
			nhatro.setTienThue(tienThue);
			nhatro.setTinhtrang(0);
			nhatro.setMoTa(mota);
			session2.clear();
			session2 = factory.openSession();
			Transaction t = session2.beginTransaction();
			try {
				session2.save(diaChi);
				session2.save(nhatro);
				ThongBao thongbao = new ThongBao();
				thongbao.setAccount(account);
				thongbao.setThoigian(new Date());
				thongbao.setThongbao("B??i vi???t c???a b???n ???? ???????c l??u th??nh c??ng! Admin s??? nhanh ch??ng duy???t b??i vi???t c???a b???n");
				thongbao.setLink("chutro/nhatro/" + nhatro.getId() + ".htm");
				session2.save(thongbao);
				t.commit();
				String path = context.getRealPath("/resources/images/nhatro/");
				anh1.transferTo(new File(path+nhatro.getId()+"_1.png"));
				anh2.transferTo(new File(path+nhatro.getId()+"_2.png"));
				re.addFlashAttribute("success", "B??i vi???t ???? l??u th??nh c??ng!");
			} catch (Exception e) {
				t.rollback();
				re.addFlashAttribute("error", "L???i! B??i vi???t c???a b???n ch??a ???????c l??u! H??y ki???m tra l???i th??ng tin v?? g???i l???i! N???u v???n kh??ng ???????c h??y b??o v???i admin!");
				return "redirect:taobaidang.htm";
			} finally {
				session2.clear();
			}
		} else {
			re.addFlashAttribute("error","B???n kh??ng c?? quy???n truy c???p ho???c ???? h???t phi??n l??m vi???c");
			return "redirect:../logout.htm";
		}
		return "redirect:index.htm";
	}	
	@RequestMapping(value="nhatro/doidiachi", method=RequestMethod.POST)
	public String doidiachi(HttpSession session, RedirectAttributes re,
			@RequestParam("id") int id,
			@RequestParam("diachi") String diachi,
			@RequestParam("ward") int ward,
			@RequestParam("district") int district,
			@RequestParam("province") int province) {
		
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		//Ki???m tra nh?? tr???
		if(nhatro.getChuTro()==null||!nhatro.getChuTro().getAccount().getUsername().equals(session.getAttribute("username"))) {
			re.addFlashAttribute("error", "B???n kh??ng c?? quy???n th???c hi???n h??nh ?????ng n??y!");
			return "redirect:../../index.htm";
		}
		//Ki???m tra ?????a ch???
		Ward w = (Ward) session2.get(Ward.class, ward);
		if(w==null) {
			re.addAttribute("error", "Sai ?????a ch???!");
			return "redirect:" + id + ".htm";
		} else if(w.getDistrict().getId()!=district) {
			re.addAttribute("error", "Sai ?????a ch???!");
			return "redirect:" + id + ".htm";
		} else if(w.getDistrict().getProvince().getId()!=province) {
			re.addAttribute("error", "Sai ?????a ch???!");
			return "redirect:" + id + ".htm";
		}
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			nhatro.getDiachi().setDiaChi(diachi);
			nhatro.getDiachi().setWard(w);
			session2.update(nhatro);
			t.commit();
			re.addFlashAttribute("success", "Thay ?????i th??nh c??ng!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "L???i! B??i vi???t c???a b???n ch??a ???????c l??u! H??y ki???m tra l???i th??ng tin v?? g???i l???i! N???u v???n kh??ng ???????c h??y b??o v???i admin!");
			return "redirect:" + id + ".htm";
		} finally {
			session2.clear();
		}
		return "redirect:" + id + ".htm";
	}
	@RequestMapping(value="nhatro/doidientich", method=RequestMethod.POST)
	public String doidientich(HttpSession session, RedirectAttributes re,
			@RequestParam("id") int id,
			@RequestParam("dienTich") float dienTich) {
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		//Ki???m tra nh?? tr???
		if(nhatro.getChuTro()==null||!nhatro.getChuTro().getAccount().getUsername().equals(session.getAttribute("username"))) {
			re.addFlashAttribute("error", "B???n kh??ng c?? quy???n th???c hi???n h??nh ?????ng n??y!");
			return "redirect:../../index.htm";
		}
		//Ki???m tra di???n t??ch
		if(dienTich<0) {
			re.addFlashAttribute("error", "S??? nh???p kh??ng h???p l???");
			return "redirect:" + id + ".htm";
		}
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			nhatro.setDienTich(dienTich);
			session2.update(nhatro);
			t.commit();
			re.addFlashAttribute("success", "Thay ?????i th??nh c??ng!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "L???i! B??i vi???t c???a b???n ch??a ???????c l??u! H??y ki???m tra l???i th??ng tin v?? g???i l???i! N???u v???n kh??ng ???????c h??y b??o v???i admin!");
			return "redirect:" + id + ".htm";
		} finally {
			session2.clear();
		}
		return "redirect:" + id + ".htm";
	}
	@RequestMapping(value="nhatro/doitiencoc", method=RequestMethod.POST)
	public String doitiencoc(HttpSession session, RedirectAttributes re,
			@RequestParam("id") int id,
			@RequestParam("tienCoc") BigDecimal tienCoc) {
		
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		//Ki???m tra nh?? tr???
		if(nhatro.getChuTro()==null||!nhatro.getChuTro().getAccount().getUsername().equals(session.getAttribute("username"))) {
			re.addFlashAttribute("error", "B???n kh??ng c?? quy???n th???c hi???n h??nh ?????ng n??y!");
			return "redirect:../../index.htm";
		}
		//Ki???m tra ti???n
		if(tienCoc.compareTo(BigDecimal.ZERO)<0) {
			re.addFlashAttribute("error", "S??? nh???p kh??ng h???p l???");
			return "redirect:" + id + ".htm";
		}
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			nhatro.setTienCoc(tienCoc);
			session2.update(nhatro);
			t.commit();
			re.addFlashAttribute("success", "Thay ?????i th??nh c??ng!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "L???i! B??i vi???t c???a b???n ch??a ???????c l??u! H??y ki???m tra l???i th??ng tin v?? g???i l???i! N???u v???n kh??ng ???????c h??y b??o v???i admin!");
			return "redirect:" + id + ".htm";
		} finally {
			session2.clear();
		}
		return "redirect:" + id + ".htm";
	}
	@RequestMapping(value="nhatro/doitienthue", method=RequestMethod.POST)
	public String doitienthue(HttpSession session, RedirectAttributes re,
			@RequestParam("id") int id,
			@RequestParam("tienThue") BigDecimal tienThue) {
		
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		//Ki???m tra nh?? tr???
		if(nhatro.getChuTro()==null||!nhatro.getChuTro().getAccount().getUsername().equals(session.getAttribute("username"))) {
			re.addFlashAttribute("error", "B???n kh??ng c?? quy???n th???c hi???n h??nh ?????ng n??y!");
			return "redirect:../../index.htm";
		}
		//Ki???m tra ti???n
		if(tienThue.compareTo(BigDecimal.ZERO)<0) {
			re.addFlashAttribute("error", "S??? nh???p kh??ng h???p l???");
			return "redirect:" + id + ".htm";
		}
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			nhatro.setTienThue(tienThue);
			session2.update(nhatro);
			t.commit();
			re.addFlashAttribute("success", "Thay ?????i th??nh c??ng!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "L???i! B??i vi???t c???a b???n ch??a ???????c l??u! H??y ki???m tra l???i th??ng tin v?? g???i l???i! N???u v???n kh??ng ???????c h??y b??o v???i admin!");
			return "redirect:" + id + ".htm";
		} finally {
			session2.clear();
		}
		return "redirect:" + id + ".htm";
	}
	@RequestMapping(value="nhatro/doisonguoitrenphong", method=RequestMethod.POST)
	public String doisonguoitrenphong(HttpSession session, RedirectAttributes re,
			@RequestParam("id") int id,
			@RequestParam("soNguoiTrenPhong") int soNguoiTrenPhong) {
		
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		//Ki???m tra nh?? tr???
		if(nhatro.getChuTro()==null||!nhatro.getChuTro().getAccount().getUsername().equals(session.getAttribute("username"))) {
			re.addFlashAttribute("error", "B???n kh??ng c?? quy???n th???c hi???n h??nh ?????ng n??y!");
			return "redirect:../../index.htm";
		}
		//Ki???m tra s???
		if(soNguoiTrenPhong<0) {
			re.addFlashAttribute("error", "S??? nh???p kh??ng h???p l???");
			return "redirect:" + id + ".htm";
		}
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			nhatro.setSoNguoiTrenPhong(soNguoiTrenPhong);
			session2.update(nhatro);
			t.commit();
			re.addFlashAttribute("success", "Thay ?????i th??nh c??ng!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "L???i! B??i vi???t c???a b???n ch??a ???????c l??u! H??y ki???m tra l???i th??ng tin v?? g???i l???i! N???u v???n kh??ng ???????c h??y b??o v???i admin!");
			return "redirect:" + id + ".htm";
		} finally {
			session2.clear();
		}
		return "redirect:" + id + ".htm";
	}
	@RequestMapping(value="nhatro/doisophongchothue", method=RequestMethod.POST)
	public String doisophongchothue(HttpSession session, RedirectAttributes re,
			@RequestParam("id") int id,
			@RequestParam("soPhongChoThue") int soPhongChoThue) {
		
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		//Ki???m tra nh?? tr???
		if(nhatro.getChuTro()==null||!nhatro.getChuTro().getAccount().getUsername().equals(session.getAttribute("username"))) {
			re.addFlashAttribute("error", "B???n kh??ng c?? quy???n th???c hi???n h??nh ?????ng n??y!");
			return "redirect:../../index.htm";
		}
		//Ki???m tra s???
		if(soPhongChoThue<0) {
			re.addFlashAttribute("error", "S??? nh???p kh??ng h???p l???");
			return "redirect:" + id + ".htm";
		}
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			nhatro.setSoPhongChoThue(soPhongChoThue);
			session2.update(nhatro);
			t.commit();
			re.addFlashAttribute("success", "Thay ?????i th??nh c??ng!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "L???i! B??i vi???t c???a b???n ch??a ???????c l??u! H??y ki???m tra l???i th??ng tin v?? g???i l???i! N???u v???n kh??ng ???????c h??y b??o v???i admin!");
			return "redirect:" + id + ".htm";
		} finally {
			session2.clear();
		}
		return "redirect:" + id + ".htm";
	}
	@RequestMapping(value="nhatro/doisophongcosan", method=RequestMethod.POST)
	public String doisophongcosan(HttpSession session, RedirectAttributes re,
			@RequestParam("id") int id,
			@RequestParam("soPhongCoSan") int soPhongCoSan) {
		
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		//Ki???m tra nh?? tr???
		if(nhatro.getChuTro()==null||!nhatro.getChuTro().getAccount().getUsername().equals(session.getAttribute("username"))) {
			re.addFlashAttribute("error", "B???n kh??ng c?? quy???n th???c hi???n h??nh ?????ng n??y!");
			return "redirect:../../index.htm";
		}
		//Ki???m tra s???
		if(soPhongCoSan<0) {
			re.addFlashAttribute("error", "S??? nh???p kh??ng h???p l???");
			return "redirect:" + id + ".htm";
		}
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			nhatro.setSoPhongCoSan(soPhongCoSan);
			session2.update(nhatro);
			t.commit();
			re.addFlashAttribute("success", "Thay ?????i th??nh c??ng!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "L???i! B??i vi???t c???a b???n ch??a ???????c l??u! H??y ki???m tra l???i th??ng tin v?? g???i l???i! N???u v???n kh??ng ???????c h??y b??o v???i admin!");
			return "redirect:" + id + ".htm";
		} finally {
			session2.clear();
		}
		return "redirect:" + id + ".htm";
	}
	
	@RequestMapping(value="nhatro/doitieude", method=RequestMethod.POST)
	public String doisophongcosan(HttpSession session, RedirectAttributes re,
			@RequestParam("id") int id,
			@RequestParam("tieuDe") String tieuDe) {
		
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		//Ki???m tra nh?? tr???
		if(nhatro.getChuTro()==null||!nhatro.getChuTro().getAccount().getUsername().equals(session.getAttribute("username"))) {
			re.addFlashAttribute("error", "B???n kh??ng c?? quy???n th???c hi???n h??nh ?????ng n??y!");
			return "redirect:../../index.htm";
		}
		//Ki???m tra s???
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			nhatro.setTieuDe(tieuDe);
			session2.update(nhatro);
			t.commit();
			re.addFlashAttribute("success", "Thay ?????i th??nh c??ng!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "L???i! B??i vi???t c???a b???n ch??a ???????c l??u! H??y ki???m tra l???i th??ng tin v?? g???i l???i! N???u v???n kh??ng ???????c h??y b??o v???i admin!");
			return "redirect:" + id + ".htm";
		} finally {
			session2.clear();
		}
		return "redirect:" + id + ".htm";
	}
	
	@RequestMapping(value="nhatro/doimota", method=RequestMethod.POST)
	public String doimota(HttpSession session, RedirectAttributes re,
			@RequestParam("id") int id,
			@RequestParam("mota") String mota) {
		
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		//Ki???m tra nh?? tr???
		if(nhatro.getChuTro()==null||!nhatro.getChuTro().getAccount().getUsername().equals(session.getAttribute("username"))) {
			re.addFlashAttribute("error", "B???n kh??ng c?? quy???n th???c hi???n h??nh ?????ng n??y!");
			return "redirect:../../index.htm";
		}
		//Ki???m tra mota
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			nhatro.setMoTa(mota);
			session2.update(nhatro);
			t.commit();
			re.addFlashAttribute("success", "Thay ?????i th??nh c??ng!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "L???i! B??i vi???t c???a b???n ch??a ???????c l??u! H??y ki???m tra l???i th??ng tin v?? g???i l???i! N???u v???n kh??ng ???????c h??y b??o v???i admin!");
			return "redirect:" + id + ".htm";
		} finally {
			session2.clear();
		}
		return "redirect:" + id + ".htm";
	}
	@RequestMapping("lichhen")
	public String lichhen(HttpSession session, ModelMap model) {
		Session session2 = factory.getCurrentSession();
		Account account = (Account) session.getAttribute("account");
		session2.clear();
		String hql = "FROM LichHen WHERE ";
		int dem=0;
		for (NhaTro nhatro:account.getChuTro().getNhaTro()) {
			hql += "idnhatro=" + nhatro.getId();
			dem++;
			if(dem<account.getChuTro().getNhaTro().size()) hql+=" OR ";
		}
		hql += " ORDER BY thoigian DESC";
		List<Object> lichhens = getList(hql);
		model.addAttribute("lichhens", lichhens);
		return "chutro/lichhen";
	}
	
	@RequestMapping("lichhen/huy/{id}")
	public String huy(ModelMap model, HttpSession session, RedirectAttributes re, @PathVariable("id") int id) {
		Session session2 = factory.getCurrentSession();
		LichHen lichhen = (LichHen) session2.get(LichHen.class, id);
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			session2.delete(lichhen);
			ThongBao thongbao = new ThongBao();
			thongbao.setAccount(lichhen.getKhachThue().getAccount());
			thongbao.setThoigian(new Date());
			thongbao.setThongbao(session.getAttribute("username") + " ???? h???y 1 l???ch h???n v???i b???n");
			thongbao.setLink("khachthue/lichhen.htm");
			session2.save(thongbao);
			t.commit();
			re.addFlashAttribute("success", "???? x??a th??nh c??ng!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "Kh??ng th??? x??a l???ch h???n n??y! H??y ki???m tra l???i th??ng tin v?? g???i l???i! N???u v???n kh??ng ???????c h??y b??o v???i admin!" );
		} finally {
			session2.close();
		}
		return "redirect:../../lichhen.htm";
	}
	@RequestMapping(value="lichhen/td", method=RequestMethod.POST)
	public String thaydoi(HttpSession session, RedirectAttributes re, 
			@RequestParam("id") int id,
			@RequestParam("thoigian") String thoigian) {
		Session session2 = factory.getCurrentSession();
		LichHen lichhen = (LichHen) session2.get(LichHen.class, id);
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			lichhen.setThoigian(new SimpleDateFormat("yyyy-MM-dd").parse(thoigian));
			ThongBao thongbao = new ThongBao();
			thongbao.setAccount(lichhen.getKhachThue().getAccount());
			thongbao.setThoigian(new Date());
			thongbao.setThongbao(session.getAttribute("username")+" ???? thay ?????i ng??y h???n! H??y ki???m tra l???i l???ch h???n");
			thongbao.setLink("khachthue/lichhen.htm");
			session2.update(lichhen);
			session2.save(thongbao);
			t.commit();
			re.addFlashAttribute("success", "Thay ?????i ???? ???????c l??u! H??y ki???m tra l???i th??ng tin");
		} catch (Exception e) {
			re.addFlashAttribute("error", "L???i! Th??ng tin thay ?????i ch??a ???????c l??u! H??y ki???m tra l???i th??ng tin v?? g???i l???i! N???u v???n kh??ng ???????c h??y b??o v???i admin!");
			t.rollback();
		} finally {
			session2.clear();
		}
		return "redirect:../lichhen.htm";
	}
	@RequestMapping("lichhen/dongy/{id}")
	public String dongy(ModelMap model, HttpSession session, RedirectAttributes re, @PathVariable("id") int id) {
		Session session2 = factory.getCurrentSession();
		LichHen lichhen = (LichHen) session2.get(LichHen.class, id);
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			lichhen.setDongy(true);
			session2.update(lichhen);
			ThongBao thongbao = new ThongBao();
			thongbao.setAccount(lichhen.getKhachThue().getAccount());
			thongbao.setThoigian(new Date());
			thongbao.setThongbao(session.getAttribute("username") + " ???? ?????ng y v???i 1 l???ch h???n v???i b???n");
			thongbao.setLink("khachthue/lichhen.htm");
			session2.save(thongbao);
			t.commit();
			re.addFlashAttribute("success", "Thay ?????i ???? ???????c l??u!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "L???i! Th??ng tin thay ?????i ch??a ???????c l??u! H??y ki???m tra l???i th??ng tin v?? g???i l???i! N???u v???n kh??ng ???????c h??y b??o v???i admin!");
		} finally {
			session2.close();
		}
		return "redirect:../../lichhen.htm";
	}
	@RequestMapping("lichhen/kodongy/{id}")
	public String kodongy(ModelMap model, HttpSession session, RedirectAttributes re, @PathVariable("id") int id) {
		Session session2 = factory.getCurrentSession();
		LichHen lichhen = (LichHen) session2.get(LichHen.class, id);
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			lichhen.setDongy(false);
			session2.update(lichhen);
			ThongBao thongbao = new ThongBao();
			thongbao.setAccount(lichhen.getKhachThue().getAccount());
			thongbao.setThoigian(new Date());
			thongbao.setThongbao(session.getAttribute("username") + " ???? h???y ?????ng ?? v???i 1 l???ch h???n v???i b???n");
			thongbao.setLink("khachthue/lichhen.htm");
			session2.save(thongbao);
			t.commit();
			re.addFlashAttribute("success", "Thay ?????i ???? ???????c l??u!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "L???i! Th??ng tin thay ?????i ch??a ???????c l??u! H??y ki???m tra l???i th??ng tin v?? g???i l???i! N???u v???n kh??ng ???????c h??y b??o v???i admin!");
		} finally {
			session2.close();
		}
		return "redirect:../../lichhen.htm";
	}
	@RequestMapping("lichhen/thanhcong/{id}")
	public String thanhcong(ModelMap model, HttpSession session, RedirectAttributes re, @PathVariable("id") int id) {
		Session session2 = factory.getCurrentSession();
		LichHen lichhen = (LichHen) session2.get(LichHen.class, id);
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			lichhen.setThanhcong(true);
			NhaTro nhatro = lichhen.getNhaTro();
			if(nhatro.getSoPhongCoSan()!=0) {
				nhatro.setSoPhongCoSan(nhatro.getSoPhongCoSan()-1);
				session2.update(nhatro);
			}
			session2.update(lichhen);
			ThongBao thongbao = new ThongBao();
			thongbao.setAccount(lichhen.getKhachThue().getAccount());
			thongbao.setThoigian(new Date());
			thongbao.setThongbao("B???n ???? th??nh c??ng t??m ???????c tr???. Xin ch??c m???ng! H??y ????? l???i ????nh gi?? ??? b??i ????ng");
			thongbao.setLink("khachthue/lichhen.htm");
			session2.save(thongbao);
			t.commit();
			re.addFlashAttribute("success", "Thay ?????i ???? ???????c l??u!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "L???i! Th??ng tin thay ?????i ch??a ???????c l??u! H??y ki???m tra l???i th??ng tin v?? g???i l???i! N???u v???n kh??ng ???????c h??y b??o v???i admin!");
		} finally {
			session2.close();
		}
		return "redirect:../../lichhen.htm";
	}
	@RequestMapping("lichhen/kothanhcong/{id}")
	public String kothanhcong(ModelMap model, HttpSession session, RedirectAttributes re, @PathVariable("id") int id) {
		Session session2 = factory.getCurrentSession();
		LichHen lichhen = (LichHen) session2.get(LichHen.class, id);
		session2.clear();
		session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		try {
			lichhen.setThanhcong(false);
			NhaTro nhatro = lichhen.getNhaTro();
			nhatro.setSoPhongCoSan(nhatro.getSoPhongCoSan()+1);
			session2.update(lichhen);
			ThongBao thongbao = new ThongBao();
			thongbao.setAccount(lichhen.getKhachThue().getAccount());
			thongbao.setThoigian(new Date());
			thongbao.setThongbao(session.getAttribute("username") + " ???? h???y th??nh c??ng v???i 1 l???ch h???n v???i b???n");
			thongbao.setLink("khachthue/lichhen.htm");
			session2.save(thongbao);
			t.commit();
			re.addFlashAttribute("success", "Thay ?????i ???? ???????c l??u!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("error", "L???i! Th??ng tin thay ?????i ch??a ???????c l??u! H??y ki???m tra l???i th??ng tin v?? g???i l???i! N???u v???n kh??ng ???????c h??y b??o v???i admin!");
		} finally {
			session2.close();
		}
		return "redirect:../../lichhen.htm";
	}
	
	public static List<Object> thongkediem(Account account, Calendar dau, Calendar cuoi) throws Exception{
		//?????m s??? th??ng
		int nam = cuoi.get(Calendar.YEAR) - dau.get(Calendar.YEAR);
		int thang = cuoi.get(Calendar.MONTH) - dau.get(Calendar.MONTH);
		int cot = nam*12 + thang + 1;
		//data[] c?? d???ng ["Th???i gian", "Nh?? tr??? [1]", "Nh?? tr??? [2]",...],
		//				["1/2021", 4.6, 3.0,...],
		//				["2/2021", 4.3, 2.5,...],...
		List<Object> data= new ArrayList<Object>();
		List<Object> head = new ArrayList<Object>();
		head.add("Th???i gian");
		for (NhaTro nt:account.getChuTro().getNhaTro()) {
			head.add("Nh?? tr???: [" + String.valueOf(nt.getId()) + "]");
		}
		data.add(head);
		Calendar datetemp = dau;
		int dem=0;
		for (int i=0; i<cot; i++) {//i l?? d??ng	
			List<Object> row = new ArrayList<Object>();
			if(datetemp.get(Calendar.MONTH)==0) row.add("12/" + String.valueOf(datetemp.get(Calendar.YEAR)));
			else row.add(String.valueOf(datetemp.get(Calendar.MONTH)) + "/" + String.valueOf(datetemp.get(Calendar.YEAR)));
			for (NhaTro nt:account.getChuTro().getNhaTro()) {
				//t??nh ??i???m cho bi???u ????? ???????ng
				dem=1;//m???u s???
				float diem=5;
				for (Comment comment:nt.getComment()) {
					if(comment.getThoigian().before(datetemp)||comment.getThoigian().equals(datetemp)) {
						diem+=comment.getDiem();											
						dem++;
					}//cho t???i th???i ??i???m ???????c ch???n diem = t???ng ??i???m/m???u s???
				}
				row.add(diem/dem);
			}
			data.add(row);
			datetemp.add(Calendar.MONTH, 1);
		}
		return data;
	}
	public static List<Object> thongketinhtrang(Account account, Calendar dau, Calendar cuoi) throws Exception{
		List<Object> data = new ArrayList<Object>();
		int dem=0;
		for (NhaTro nt:account.getChuTro().getNhaTro()) {
			List<Object> dong = new ArrayList<>();
			dong.add("Nh?? tr??? [" + String.valueOf(nt.getId())+"]");
			//					nh?? tr???, dy, cdy, tc, tb
			//data[] c?? d???ng ["Nh?? tr??? [1]", 1, 1, 1, 1],
			//				["Nh?? tr??? [2]", 1, 1, 1, 1],...
			int dy=0, cdy=0, tc=0, tb=0;
			boolean co=false;
			for (LichHen lh:nt.getLichHen()) {
				if(lh.getThoigian().before(cuoi.getTime())&&lh.getThoigian().after(dau.getTime())) {
					if(lh.getDongy()) dy++;
					else if(lh.getThoigian().before(new Date())) tb++;
					else cdy++;
					if(lh.getThanhcong()) tc++;
					dong.add(dy); dong.add(tc); dong.add(tb); dong.add(cdy);dong.add(dem); dem++;
					co=true;
				}
			}
			if (co) data.add(dong);
		}
		return data;
	}
	@RequestMapping(value="thongkes", params = {"begin","end"}, method=RequestMethod.GET)
	public String thongke(HttpSession session, ModelMap model,
			@RequestParam("begin") String begin, @RequestParam("end") String end) {
		try {
			Calendar dau = new GregorianCalendar(Integer.parseInt(begin.split("-")[0]), Integer.parseInt(begin.split("-")[1]), Integer.parseInt(begin.split("-")[2]));
			Calendar cuoi = new GregorianCalendar(Integer.parseInt(end.split("-")[0]), Integer.parseInt(end.split("-")[1]), Integer.parseInt(end.split("-")[2]));
			Calendar dau1 = new GregorianCalendar(Integer.parseInt(begin.split("-")[0]), Integer.parseInt(begin.split("-")[1]), Integer.parseInt(begin.split("-")[2]));
			Calendar cuoi1 = new GregorianCalendar(Integer.parseInt(end.split("-")[0]), Integer.parseInt(end.split("-")[1]), Integer.parseInt(end.split("-")[2]));
			//kt ng??y trc c?? > ng??y sau ko
			if (dau.equals(cuoi)||dau.after(cuoi)) throw new ParseException(end, 0);			
			model.addAttribute("data", thongkediem((Account) session.getAttribute("account"), dau, cuoi));		
			model.addAttribute("pie", thongketinhtrang((Account) session.getAttribute("account"), dau1, cuoi1));
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			model.addAttribute("error", "Ng??y kh??ng h???p l???!");
		} catch (Exception e) {
			model.addAttribute("error", "L???i");
		} finally {
			model.addAttribute("begin", begin);
			model.addAttribute("end", end);
			return "chutro/thongke";
		}
	}
	@RequestMapping(value="thongke", method = RequestMethod.GET)
	public String thongke() {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		return "redirect:thongkes.htm?begin=2021-01-01&end="+date;
	}
}
