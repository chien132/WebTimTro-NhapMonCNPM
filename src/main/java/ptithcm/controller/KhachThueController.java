package ptithcm.controller;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.transaction.Synchronization;
import javax.transaction.Transactional;


import org.springframework.mail.javamail.JavaMailSender;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.engine.transaction.spi.LocalStatus;
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

import ptithcm.entity.*;

import ptithcm.service.ProvinceService;

@Transactional
@Controller
@RequestMapping("/khachthue/")
public class KhachThueController {
	@Autowired
	SessionFactory factory;

	@Autowired
	JavaMailSender mailer;
	
	@Autowired
	ServletContext context;
	
	List<Object> nhatros;
	Province province;
	District district;
	Ward ward;
	int page;
	
	List<Object> getList(String hql) {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery(hql);
		List<Object> list = query.list();
		return list;
	}
	public static boolean isContainSpecialWord(String str) {
		Pattern VALID_INPUT_REGEX = Pattern.compile("[$&+,:;=\\\\\\\\?@#|/'<>.^*()%!-]",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_INPUT_REGEX.matcher(str);
		return matcher.find();
	}
	@ModelAttribute("provinces")
	public List<Province> getProvinces(){
		return ProvinceService.findAll(factory);
	}
	@ModelAttribute("ques")
	public List<Province> getQues(){
		return ProvinceService.findAll(factory);
	}
	//Đổ thông tin account từ session
	@ModelAttribute("user")
	public Account user(HttpSession session) {
		Session session2 = factory.getCurrentSession();
		return (Account) session2.get(Account.class, (String) session.getAttribute("username"));
	}
	//Đổ thông tin thông báo từ session
	@ModelAttribute("thongbaos")
	public <T> Collection<ThongBao> getThongBao(HttpSession session, ModelMap model){
		Session session2 = factory.getCurrentSession();
		Account account = (Account) session2.get(Account.class, (String) session.getAttribute("username"));
		String tbnhapthongtin = "Hãy nhập thông tin thêm khách hàng để sử dụng một số dịch vụ đặc biệt của trang web";
		String tblichhen = "Hôm nay bạn có một buổi hẹn xem phòng!";
		for (ThongBao tb:account.getThongBao()) {
			if(!tb.getThongbao().equals(tbnhapthongtin)) {
				ThongBao thongbao = new ThongBao();
				thongbao.setAccount(account);
				thongbao.setThoigian(new Date());
				thongbao.setThongbao(tbnhapthongtin);
				thongbao.setLink("/khachthue/thongtinthem.htm");
				Session session3 = factory.openSession();
				Transaction t = session3.beginTransaction();
				try {
					session3.save(thongbao);
					t.commit();
				} catch (Exception e){
					t.rollback();
					model.addAttribute("message", "Lỗi!" + e);
				}
			}
			for (LichHen lichhen:account.getKhachThue().getLichHen()) {
				if(lichhen.getThoigian().equals(new Date())) {
					ThongBao thongbao = new ThongBao();
					thongbao.setAccount(account);
					thongbao.setThoigian(new Date());
					thongbao.setThongbao(tblichhen);
					thongbao.setLink("/khachthue/lichhen.htm");
					Session session3 = factory.openSession();
					Transaction t = session3.beginTransaction();
					try {
						session3.save(thongbao);
						t.commit();
					} catch (Exception e){
						t.rollback();
						model.addAttribute("message", "Lỗi!" + e);
					}
					break;
				}
			}
		}
		List<ThongBao> thongbao = (List<ThongBao>) account.getThongBao();
		Collections.sort(thongbao);
		return thongbao;
	}
	@RequestMapping(value="index")
	public String index(ModelMap model) {
		String hql="FROM NhaTro "
				+ "WHERE tinhtrang=1 "
				+ "ORDER BY diem DESC";
		List<Object> listNhaTro = getList(hql);
		if(listNhaTro.size()+10<page*10) {
			model.addAttribute("message", "Không tìm thấy trang !");
		}else {
			this.nhatros = listNhaTro;
			this.page = 1;
			model.addAttribute("nhatros", listNhaTro);
			model.addAttribute("page", 1);
			model.addAttribute("end", listNhaTro.size()%10!=0?listNhaTro.size()/10+1:listNhaTro.size()/10);
			this.province = null;
			this.district = null;
			this.ward = null;
			model.addAttribute("feature","index");
		}
		return "khachthue/index";
	}
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("username");
		return "redirect:../index.htm";
	}
	
	@RequestMapping(value="timkiem", params = {"province","district","ward"})
	public String timkiem(ModelMap model, 
			@RequestParam("province") String p, 
			@RequestParam("district") String d, 
			@RequestParam("ward") String w) {
		String hql = "FROM NhaTro "
				+ "WHERE tinhtrang = 1 "
				+ "ORDER BY diem DESC";
		List<Object> listNhaTro = getList(hql);
		try {
			int province = Integer.parseInt(p);
			int district = Integer.parseInt(d);
			int ward = Integer.parseInt(w);
			if(ward!=0) {
				int dem=0;
				while (true) {
					for (Object nt:listNhaTro) {
						NhaTro nhatro = (NhaTro) nt;
						if(nhatro.getWardId()!=ward) {
							listNhaTro.remove(nt);
							break;
						}else dem++;
						
					} if(dem==listNhaTro.size()) break;
					else dem=0;
				}
			}
		if(listNhaTro.size()+10<page*10) {
			model.addAttribute("message", "Không tìm thấy trang !");
		} else {
			this.nhatros = listNhaTro;
			this.page = 1;
			Session session = factory.getCurrentSession();
			this.province = (Province) session.get(Province.class, province);
			this.district = (District) session.get(District.class, district);
			this.ward = (Ward) session.get(Ward.class, ward);
			model.addAttribute("nhatros", listNhaTro);
			model.addAttribute("page", 1);
			model.addAttribute("end", listNhaTro.size()%10!=0?listNhaTro.size()/10+1:listNhaTro.size()/10);
			model.addAttribute("province", this.province);
			model.addAttribute("district", this.district);
			model.addAttribute("ward", this.ward);
			model.addAttribute("feature","timkiem");
		}
		} catch (Exception e) {
			model.addAttribute("message","Tìm kiếm thất bại!");
		}
		return "khachthue/index";
	}
		
	
	@RequestMapping(value="timkiem", params = {"province","district"})
	public String timkiem(ModelMap model,
			@RequestParam("province") String p, 
			@RequestParam("district") String d) {
		String hql = "FROM NhaTro "
				+ "WHERE tinhtrang = 1 "
				+ "ORDER BY diem DESC";
		List<Object> listNhaTro = getList(hql);
		try {
			int province = Integer.parseInt(p);
			int district = Integer.parseInt(d);
			if(district!=0) {
				int dem=0;
				while (true) {
					for (Object nt:listNhaTro) {
						NhaTro nhatro = (NhaTro) nt;
						if(nhatro.getDistrictId()!=district) {
							listNhaTro.remove(nt);
							break;
						}else dem++;
						
					} if(dem==listNhaTro.size()) break;
					else dem=0;
				}
			}
			if(listNhaTro.size()+10<page*10) {
				model.addAttribute("message", "Không tìm thấy trang !");
			}else {
				this.nhatros = listNhaTro;
				this.page = 1;
				Session session = factory.getCurrentSession();
				this.province = (Province) session.get(Province.class, province);
				this.district = (District) session.get(District.class, district);
				this.ward = null;
				model.addAttribute("nhatros", listNhaTro);
				model.addAttribute("page", 1);
				model.addAttribute("end", listNhaTro.size()%10!=0?listNhaTro.size()/10+1:listNhaTro.size()/10);
				model.addAttribute("province", this.province);
				model.addAttribute("district", this.district);
				model.addAttribute("ward", this.ward);
				model.addAttribute("feature","timkiem");
			}
		} catch(Exception e) {
			model.addAttribute("message", "Tìm kiếm thất bại!");
		}
		return "khachthue/index";
	}
		
	
	@RequestMapping(value="timkiem", params = {"province"})
	public String timkiem(ModelMap model,
			@RequestParam("province") String p) {
		String hql = "FROM NhaTro "
				+ "WHERE tinhtrang = 1 "
				+ "ORDER BY diem DESC";
		List<Object> listNhaTro = getList(hql);
		try {
			int province = Integer.parseInt(p);
			if(province!=0) {
				int dem=0;
				while (true) {
					for (Object nt:listNhaTro) {
						NhaTro nhatro = (NhaTro) nt;
						if(nhatro.getProvinceId()!=province) {
							listNhaTro.remove(nt);
							break;
						}else dem++;
						
					} if(dem==listNhaTro.size()) break;
					else dem=0;
				}
			}
			if(listNhaTro.size()+10<page*10) {
				model.addAttribute("message", "Không tìm thấy trang !");
			}else {
				this.nhatros = listNhaTro;
				this.page = 1;
				Session session = factory.getCurrentSession();
				this.province = (Province) session.get(Province.class, province);
				this.district = null;
				this.ward = null;
				model.addAttribute("nhatros", listNhaTro);
				model.addAttribute("page", 1);
				model.addAttribute("end", listNhaTro.size()%10!=0?listNhaTro.size()/10+1:listNhaTro.size()/10);
				model.addAttribute("province", this.province);
				model.addAttribute("district", this.district);
				model.addAttribute("ward", this.ward);
				model.addAttribute("feature","timkiem");
			}
			} catch (Exception e) {
				model.addAttribute("message", "Tìm kiếm thất bại!");
			}
		return "khachthue/index";
	}
	@RequestMapping(value="timkiem")
	public String timkiem() {
		return "redirect:index.htm";
	}
	@RequestMapping("nhatro/{id}")
	public String nhatro(ModelMap model, @PathVariable("id") int id, HttpSession session) {
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		model.addAttribute("nhatro",nhatro);
		LichHen lichhen = new LichHen();
		model.addAttribute("lichhen", lichhen);
		return "khachthue/nhatro";
	}
	@RequestMapping(value="timkiem",params={"page"})
	public String page(ModelMap model, @RequestParam("page") int page) {
		model.addAttribute("nhatros", this.nhatros);
		model.addAttribute("page", page);
		model.addAttribute("end", this.nhatros.size()%10!=0?this.nhatros.size()/10+1:this.nhatros.size()/10);
		return "khachthue/index";
	}
	@RequestMapping(value="loc", method=RequestMethod.POST)
	public String loc(ModelMap model, 
			@RequestParam("diem") String d,
			@RequestParam("soluot") String sl,
			@RequestParam("songuoi") String sn,
			@RequestParam("giathue") String gt,
			RedirectAttributes re) {
		try {
			if(!d.isEmpty()) {
				float diem = Float.parseFloat(d);
				int dem=0;
				while(true) {
					for (Object nt:this.nhatros) {
						NhaTro nhatro = (NhaTro) nt;
						if(nhatro.getDiem()<diem) {
							this.nhatros.remove(nt);
							break;
						} else dem++;
					}if(dem==this.nhatros.size()) break;
					else dem=0;
				}
			}
			if(!sl.isEmpty()) {
				int soluot = Integer.parseInt(sl), dem=0;
				while(true) {
					for (Object nt:this.nhatros) {
						NhaTro nhatro = (NhaTro) nt;
						if(nhatro.getSoLuot()<soluot) {
							this.nhatros.remove(nt);
							break;
						}else dem++;
					}if(dem==this.nhatros.size()) break;
					else dem=0;
				}
			}
			if(!sn.isEmpty()) {
				int songuoi = Integer.parseInt(sn), dem=0;
				while(true) {
					for (Object nt:this.nhatros) {
						NhaTro nhatro = (NhaTro) nt;
						if(nhatro.getSoNguoiTrenPhong()<songuoi) {
							this.nhatros.remove(nt);
							break;
						}else dem++;
					}if(dem==this.nhatros.size()) break;
					else dem=0;
				}
			}
			if(!gt.isEmpty()) {
				BigDecimal giathue = new BigDecimal(gt);
				int dem=0;
				while(true) {
					for (Object nt:this.nhatros) {
						NhaTro nhatro = (NhaTro) nt;
						if(nhatro.getTienThue().compareTo(giathue)>0) {
							this.nhatros.remove(nt);
							break;
						}else dem++;
					}if(dem==this.nhatros.size()) break;
					else dem=0;
				}
			}
			model.addAttribute("nhatros", nhatros);
			model.addAttribute("page", 1);
			model.addAttribute("end", nhatros.size()%10!=0?nhatros.size()/10+1:nhatros.size()/10);
		}catch(Exception e) {
			re.addFlashAttribute("message", "Không thể lọc ! " + e);
			return "redirect:index.htm";
		}
		return "khachthue/index";
	}
	@RequestMapping("tudong")
	public String tudong(ModelMap model, HttpSession session, RedirectAttributes re) {
		/*
		 * Session session2 = factory.getCurrentSession(); Account account = (Account)
		 * session2.get(Account.class, (String) session.getAttribute("username"));
		 * KhachThue khachthue = account.getKhachThue(); try {
		 * if(khachthue.getNamSinh()==0 && khachthue.getQueQuan().isEmpty() &&
		 * khachthue.getTruong()==null) { re.addFlashAttribute("message",
		 * "Hãy cập nhật thông tin thêm để thực hiện chức năng này"); return
		 * "redirect:index.htm"; }else { String hql = "FROM NhaTro " +
		 * "WHERE tinhtrang = 1 " + "ORDER BY diem DESC"; List <Object> nhatros =
		 * getList(hql); int dem = 0; while (true) { for (Object temp:nhatros) { NhaTro
		 * nhatro = (NhaTro) temp;
		 * 
		 * } } } } catch (Exception e) { re.addFlashAttribute("message", "Lỗi! " + e); }
		 */
		return "redirect:index.htm";
	}
	@RequestMapping("datlich/{id}")
	public String datlich(ModelMap model, @PathVariable("id") int id, 
			@ModelAttribute("lichhen") LichHen lichhen, 
			HttpSession session, RedirectAttributes re) {
		if(lichhen.getThoigian()==null||lichhen.getThoigian().before(new Date())||lichhen.getThoigian().before(new Date())) {
			re.addFlashAttribute("datlich", "Vui lòng chọn ngày phù hợp !");
			return "redirect:../nhatro/" + id + ".htm";
		}
		Session session2 = factory.getCurrentSession();
		NhaTro nhatro = (NhaTro) session2.get(NhaTro.class, id);
		Account account = (Account) session2.get(Account.class, (String) session.getAttribute("username"));
		lichhen.setNhaTro(nhatro);
		lichhen.setKhachThue(account.getKhachThue());
		lichhen.setDongy(false);
		lichhen.setThanhcong(false);
		lichhen.setThoigian(new Date());
		Session session3 = factory.openSession();
		Transaction t = session3.beginTransaction();
		try {
			session3.save(lichhen);
			t.commit();
			re.addFlashAttribute("datlich", "Thêm lịch hẹn thành công!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("datlich", "Thêm lịch hẹn thất bại!");
		} finally {
			session3.close();
		}
		return "redirect:../nhatro/" + id + ".htm";
	}
	@RequestMapping(value="lichhen", method=RequestMethod.GET)
	public String lichhen(ModelMap model, HttpSession session) {
		Session session2 = factory.getCurrentSession();
		Account account = (Account) session2.get(Account.class, (String) session.getAttribute("username"));
		model.addAttribute("lichens", account.getKhachThue().getLichHen());
		return "khachthue/lichhen";
	}
	@RequestMapping("lichhen/huy/${id}")
	public String huy(ModelMap model, HttpSession session, RedirectAttributes re,@PathVariable("id") int id) {
		Session session2 = factory.getCurrentSession();
		LichHen lichhen = (LichHen) session2.get(LichHen.class, id);
		Session session3 = factory.openSession();
		Transaction t = session3.beginTransaction();
		try {
			session3.delete(lichhen);
			t.commit();
			re.addFlashAttribute("message", "Đã xóa thành công!");
		} catch (Exception e) {
			t.rollback();
			re.addFlashAttribute("message", "Không thể xóa lịch hẹn này! " + e);
		}
		return "redirect:../../lichhen.htm";
	}
	@RequestMapping(value="thongtinthem", method = RequestMethod.GET)
	public String thongtinthem(ModelMap model, HttpSession session) {
		Session session2 = factory.getCurrentSession();
		Account account = (Account) session2.get(Account.class, (String) session.getAttribute("username"));
		model.addAttribute("khachthue", account.getKhachThue());
		return "khachthue/thongtinthem";
	}
	@RequestMapping(value="thongtinthem", method = RequestMethod.POST)
	public String themthongtin(ModelMap model, HttpSession session, RedirectAttributes re,
			@RequestParam("idtruong") String idtruong,
			@RequestParam("province") String idprovince,
			@RequestParam("que") String que,
			@RequestParam("namsinh") String namsinh,
			@RequestParam("gioitinh") String gioitinh,
			@RequestParam("avata") MultipartFile avata) {
		Session session2 = factory.getCurrentSession();
		Account account = (Account) session2.get(Account.class, (String) session.getAttribute("username"));
		try {
			if(!avata.isEmpty()) {
				String avataPath = context.getRealPath("/resources/images/avata/" + session.getAttribute("username") + ".png");
				avata.transferTo(new File(avataPath));
				System.out.println("Thêm file thành công");
			}
			if(!idtruong.isBlank()&&!idprovince.isBlank()) {
				Province province = (Province) session2.get(Province.class, Integer.parseInt(idprovince));
				List<Truong> truongs = (List<Truong>) province.getTruongs();
				for (Truong truong:truongs) {
					if(truong.getId()==Integer.parseInt(idtruong)) {
						account.getKhachThue().setTruong(truong);
						break;
					}
					if(truongs.get(truongs.size()-1)!=truong) {
						re.addFlashAttribute("error", "Không tìm thấy trường bạn chọn! ");
						return "redirect:thongtinthem.htm";
					}
				}
			}
			account.getKhachThue().setQueQuan(que);
			account.getKhachThue().setNamSinh(Integer.parseInt(namsinh));
			account.getKhachThue().setGioiTinh(gioitinh.equals("1")?true:false);
			ThongBao thongbao = new ThongBao();
			thongbao.setAccount(account);
			thongbao.setThoigian(new Date());
			thongbao.setThongbao("Đã cập nhật thành công thông tin thêm của bạn");
			thongbao.setLink("/khachthue/thongtinthem.htm");
			Session session3 = factory.openSession();
			Transaction t = session3.beginTransaction();
			try {
				session3.save(thongbao);
				t.commit();
			} catch (Exception e){
				t.rollback();
				re.addFlashAttribute("error", "Lỗi! " + e );
				return "redirect:thongtinthem.htm";
			}
		} catch (Exception e) {
			re.addFlashAttribute("error", "Lỗi! " + e );
			return "redirect:thongtinthem.htm";
		}
		return "redirect:index";
	}
}
