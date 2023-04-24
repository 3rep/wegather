package com.gather.we.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gather.we.dto.RegisterDTO;
import com.gather.we.service.RegisterService;


@Controller
public class RegisterController {
	@Autowired
	RegisterService service;
	//로그인 선택 창
	@GetMapping("/loginChoose")
	public String loginChoose() {
		return "user/register/loginChoose";	
	}
	
	//로그인폼
	@GetMapping("/login")
	public String login() {
		return "user/register/login";	//	/WEB-INF/views/register/loginForm.jsp
	}
	
	//로그인(DB)
		@PostMapping("/loginOk")
		public ModelAndView loginOk(String userid, String password, HttpServletRequest request, HttpSession session) {
			// Session 객체 얻어오기
			// 매개변수로 HttpServletRequest request -> Session 구하기
			// 매개변수로 HttpSession session
			System.out.println("userid->"+userid);
			RegisterDTO dto = service.loginOk(userid, password);
			// dto->null인 경우 선택레코드가 없다. -로그인실패
			// 		null이 아닌 경우 선택레코드 있다. - 로그인 성공
			ModelAndView mav = new ModelAndView();
			if(dto!=null) {//로그인 성공
				session.setAttribute("logId", dto.getUserid());
				session.setAttribute("logName", dto.getUsername());
				session.setAttribute("logStatus", "Y");
				mav.setViewName("redirect:/");
			}else{//로그인 실패
				mav.setViewName("redirect:login");	
			}
			return mav;
		}
	
	//회원가입 선택 창
	@GetMapping("/registerChoose")
	public String registerChoose() {
		return "user/register/registerChoose";	
	}
	//회원가입 폼
	@GetMapping("/register")
	public String register() {
		return "user/register/register";
	}
	
	@RequestMapping(value="/registerOk", method=RequestMethod.POST)
	public ModelAndView registerOk(RegisterDTO dto) {
		System.out.println(dto.toString());
		
		ModelAndView mav = new ModelAndView();
		//회원가입
		int result = service.registerInsert(dto);
		
		if(result>0) {//회원가입 성공시 - 로그인폼 이동
			mav.setViewName("redirect:login");
		}else {//회원가입 실패시
			mav.addObject("msg", "회원등록실패하였습니다.");
			mav.setViewName("user/register/registerOkResult");
		}
		return mav;		
	}
	//아이디 중복검사 폼
		@GetMapping("/idCheck")
		public String idCheck(String userid, Model model) {
			//조회
			//아이디의 갯수 구하기 - 0,1
			int result = service.idCheckCount(userid);
			
			//뷰에서 사용하기 위해서 모델에 세팅
			model.addAttribute("userid", userid);
			model.addAttribute("result", result);
			
			return "user/register/idCheck";
		}
	
	//회원정보 수정(db)
	@PostMapping("/userEditOk")
	public ModelAndView loginEditOk(RegisterDTO dto) {
		System.out.println(dto.toString());
		int cnt = service.registerEditOk(dto);
		ModelAndView mav = new ModelAndView();
		if(cnt>0) {
			mav.setViewName("redirect:admin/userList");
		}else {
			mav.addObject("msg", "회원정보수정 실패하였습니다.");
			mav.setViewName("user/register/registerOkResult");
		}
		return mav;
	}
	
	//로그아웃 - 세션제거
		@RequestMapping("/logout")
		public ModelAndView logout(HttpSession session) {
			session.invalidate();
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/");
			return mav;
		}
}
