package ptithcm.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ptithcm.entity.Account;
import ptithcm.entity.District;
import ptithcm.entity.LichHen;
import ptithcm.entity.NhaTro;
import ptithcm.entity.Province;
import ptithcm.entity.Ward;
import ptithcm.service.ProvinceService;

@Transactional
@Controller
public class HomeController {
	@Autowired
	SessionFactory factory;
	@Autowired
	ServletContext context;
	
	List<Object> nhatros;
	Province province;
	District district;
	Ward ward;
	int page;
	
	@RequestMapping("")
	public String welcome() {
		return "redirect:index.htm";
	}
	
	@ModelAttribute("provinces")
	public List<Province> getProvinces(){
		return ProvinceService.findAll(factory);
	}
	@ModelAttribute("ques")
	public List<Province> getQues(){
		return ProvinceService.findAll(factory);
	}
		
	List<Object> getList(String hql) {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery(hql);
		List<Object> list = query.list();
		return list;
	}
	public void sort(List<Object> nhatros){
		for(int i=0; i<nhatros.size()-1;i++) {
			for(int j=0; j<nhatros.size()-1-i;j++) {
				NhaTro nhatroj = (NhaTro) nhatros.get(j);
				NhaTro nhatrojj = (NhaTro) nhatros.get(j+1);
				if(nhatroj.getDiem()<nhatrojj.getDiem()) {
					NhaTro temp = nhatroj;
					nhatros.set(j, nhatrojj);
					nhatros.set(j+1, temp);		
				}
			}
		}
	}

	@RequestMapping("index")
	public String index(ModelMap model, HttpSession httpSession) {
		String hql="FROM NhaTro "
				+ "WHERE tinhtrang=1 ";
		List<Object> listNhaTro = getList(hql);
		sort(listNhaTro);
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
		return "index";
	}
	@RequestMapping(value="timkiem", params = {"province","district","ward"})
	public String timkiem(ModelMap model, 
			@RequestParam("province") String p, 
			@RequestParam("district") String d, 
			@RequestParam("ward") String w) {
		String hql = "FROM NhaTro "
				+ "WHERE tinhtrang = 1 ";
		List<Object> listNhaTro = getList(hql);
		sort(listNhaTro);
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
		return "index";
	}
		
	
	@RequestMapping(value="timkiem", params = {"province","district"})
	public String timkiem(ModelMap model,
			@RequestParam("province") String p, 
			@RequestParam("district") String d) {
		String hql = "FROM NhaTro "
				+ "WHERE tinhtrang = 1 ";
		List<Object> listNhaTro = getList(hql);
		sort(listNhaTro);
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
		return "index";
	}
		
	
	@RequestMapping(value="timkiem", params = {"province"})
	public String timkiem(ModelMap model,
			@RequestParam("province") String p) {
		String hql = "FROM NhaTro "
				+ "WHERE tinhtrang = 1 ";
		List<Object> listNhaTro = getList(hql);
		sort(listNhaTro);
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
		return "index";
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
		return "nhatro";
	}
	@RequestMapping(value="timkiem",params={"page"})
	public String page(ModelMap model, @RequestParam("page") int page) {
		model.addAttribute("nhatros", this.nhatros);
		model.addAttribute("page", page);
		model.addAttribute("end", this.nhatros.size()%10!=0?this.nhatros.size()/10+1:this.nhatros.size()/10);
		return "index";
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
		return "index";
	}
}
