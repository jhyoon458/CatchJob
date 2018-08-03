package com.CatchJob.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.CatchJob.service.EnterpriseService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/enterprise")
public class EnterpriseController {

	@Autowired
	private EnterpriseService entService;

	@RequestMapping(value = "/EnterpriseService", method = RequestMethod.GET)
	public String entListForm() {
		// 기업 리스트 출력화면
		return null;
	}

	@RequestMapping(value = "/search")
	public String getEntList(String keyword, Model model) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("keyword", keyword);
		model.addAttribute("entList", entService.getEntList(data));

		// 기업 리스트 출력
		return "enterprise-list";
	}

	 @RequestMapping(value = "/view")
	 public String entDetailsForm(int ent_idx, Model model) {
		List<Map<String, String>> viewData = entService.empCountGraph(ent_idx);
		System.out.println(viewData);
//		 model.addAttribute("viewDataSize", entService.empCountGraph(ent_idx).size());
		 model.addAttribute("viewData", viewData);
		 model.addAttribute("viewDataJson",new Gson().toJson(viewData));
		 
//		 System.out.println("==================="+new Gson().toJson(viewData));
		 
		 return "enterprise-view";
		 }

}
