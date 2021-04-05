package ptithcm.controller;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import ptithcm.service.DistrictService;
import ptithcm.service.ProvinceService;
import ptithcm.service.WardService;

@Controller
@RequestMapping(value = "demo")
public class DemoController {
	@Autowired
	SessionFactory factory;

	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		modelMap.put("provinces", ProvinceService.findAll(factory));
//		modelMap.put("d", DistrictService.findByProvince(20, factory));
//		modelMap.put("w", WardService.findByDistrict(02, factory));

		return "demo/index";
	}

	@RequestMapping("test")
	public String test(ModelMap modelMap) {
		modelMap.put("provinces", ProvinceService.findAll(factory));
		return "demo/test";
	}

	@ResponseBody
	@RequestMapping(value = "loadStatesByCountry/{id}", method = RequestMethod.GET)
	public String loadStatesByCountry(@PathVariable("id") int id, ModelMap model) {
		Gson gson = new Gson();
		model.put("test", gson.toJson(DistrictService.findByProvince(id, factory)));
		return "demo/index";
		// return gson.toJson(DistrictService.findByProvince(id, factory));
	}

	@ResponseBody
	@RequestMapping(value = "loadCitiesByState/{id}", method = RequestMethod.GET)
	public String loadCitiesByState(@PathVariable("id") int id) {
		Gson gson = new Gson();
		return gson.toJson(WardService.findByDistrict(id, factory));
	}

}