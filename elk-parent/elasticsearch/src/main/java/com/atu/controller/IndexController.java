package com.atu.controller;

import com.atu.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController extends BaseController{
	/**
	 * 主页
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/home")
	public String index(Model model) throws Exception {
		return "jsp/home";
	}

}
