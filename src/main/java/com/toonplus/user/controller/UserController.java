package com.toonplus.user.controller;

import com.toonplus.user.dto.User;
import com.toonplus.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


@Controller
public class UserController {

	@Autowired
	private UserServiceImpl userService;
	
	@PostMapping("/user/checkId")  //아이디 체크
	@ResponseBody
	public String checkId(User user) {
		String RESULT;
		
		int cnt = userService.checkId(user);
		if (cnt > 0) {
			RESULT = "false";
		} else {
			RESULT = "true";
		}
		System.out.println("RESULT : " + RESULT);
		return RESULT;
	}
	
	@PostMapping("/user/checkEmail")	//이메일 체크
	@ResponseBody
	public String checkEmail(User user, HttpSession session) {
		String RESULT; 
		User sessionUser = (User) session.getAttribute("user");
		if (sessionUser != null) {
			user.setUSER_ID(sessionUser.getUSER_ID());
		}
		int cnt = userService.checkEmail(user);
		if (cnt > 0) {
			RESULT = "false";
		} else {
			RESULT = "true";
		}
		System.out.println("RESULT : " + RESULT);
		return RESULT;
	}
	
	@PostMapping("/user/checkNickName")	//닉네임 체크
	@ResponseBody
	public String checkNickName(User user, HttpSession session) {
		String RESULT;
		User sessionUser = (User) session.getAttribute("user");
		if (sessionUser != null) {
			user.setUSER_ID(sessionUser.getUSER_ID());
		}
		int cnt = userService.checkNickName(user);
		if (cnt > 0) {
			RESULT = "false";
		} else {
			RESULT = "true";
		}
		System.out.println("RESULT : " + RESULT);
		return RESULT;
	}
	
	@PostMapping("/user/signup")	//회원가입하면 홈으로 다시 전송
	public String signupPost(User user) {
		System.out.println(user.toString());
		userService.insertUser(user);
		return "redirect:/home";
	}
	
	@GetMapping("/user/signup")  //회원가입 화면 띄우기
	public String signup() {
		
		return "/user/signupForm";
	}
	

	@PostMapping("/user/update")  //회원정보 수정하면 메인으로 전송
	public String updatePost(User user, HttpSession session) {
		System.out.println(user.toString());
		userService.updateUser(user);
		User rs = userService.login(user);
		session.setAttribute("user", rs);
		return "redirect:/main";
	}
	
	@GetMapping("/user/update")  //회원정보 수정 화면 띄우기
	public String update() {
		
		return "/user/updateForm";
	}
	
	
	@PostMapping("/user/login")
	@ResponseBody
	public String loginPost(User user, HttpSession session) {
		String RESULT;
		User rs = userService.login(user);
		if (rs != null) {
			session.setAttribute("user", rs);
			RESULT = "true";
		} else {
			RESULT = "false";
		}
		System.out.println("RESULT : " + RESULT);
		return RESULT;
	}
	
	
	
	@GetMapping("/user/login")  //로그인 화면 띄우기
	public String login() {
		
		return "/user/loginForm";
	}

	@GetMapping("/main")
	public String main(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user != null) {
			// 로그인된 사용자의 경우 메인으로 이동
			return "/main";
		} else {
			// 로그인되지 않은 사용자의 경우 로그인 페이지로 이동
			return "redirect:/user/login";
		}
	}
}
