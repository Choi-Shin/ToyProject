package com.choi.board.controller;

import java.util.Base64;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.choi.board.common.AuthUser;
import com.choi.board.common.Member;
import com.choi.board.service.IMemberService;

@Controller
@RequestMapping("/member/*")
public class MemberController {
	@Autowired
	IMemberService ms;

	@GetMapping(value = "/login")
	public String 로그인팝업창띄우다(Device device) {
		if (device.isMobile()) {
			return "/m/member/로그인";
		} else {
			return "/member/로그인팝업";
		}
	}

	@GetMapping(value = "/admin/login")
	public String 관리자로그인팝업띄우다(AuthUser user, Device device) {
		if (device.isMobile()) {
			return "/m/member/관리자로그인";
		} else {
			return "/member/관리자로그인팝업";
		}
	}

	@PostMapping(value = "/login")
	public ModelAndView 로그인시도하다(AuthUser user, Device device) throws Exception {
		ModelAndView mv = new ModelAndView();
		String welcome;
		Member m = ms.로그인하다(user);
		mv.setViewName("/member/로그인결과");
		if (m == null) {
			mv.addObject("msg", "존재하지 않는 아이디이거나 비밀번호가 틀립니다.");
			if (device.isMobile()) {
				mv.setViewName("/m/member/로그인");
			}
		} else if (m.getState() == 1){
			if (user.getId().equals("admin")) {
				welcome = "관리자 로그인에 성공하였습니다.";
				mv.addObject("admin", user);
			} else {
				welcome = "환영합니다. " + user.getId() + "님";
			}
			if (device.isMobile()) {
				mv.setViewName("/m/home");
			}
			mv.addObject("msg", welcome);
			mv.addObject("loginUser", user);
		}
		return mv;
	}

	@PostMapping(value = "/admin/login")
	public ModelAndView 관리자로그인시도하다(AuthUser user, Device device) {
		ModelAndView mv = new ModelAndView();
		String welcome;
		if (user.getId().equals("admin")) {
			try {
				Member m = ms.로그인하다(user);
				if (m == null) {
					welcome = "관리자 로그인에 실패하였습니다.";
					mv.addObject("msg", welcome);
				} else {
					welcome = "관리자 로그인에 성공하였습니다.";
					mv.addObject("msg", welcome);
					mv.addObject("admin", user);
					mv.addObject("loginUser", user);
				}
			} catch (Exception e) {
			}

		} else {
			welcome = "관리자 로그인에 실패하였습니다.";
			mv.addObject("msg", welcome);
		}
		if (device.isMobile()) {
			mv.setViewName("/m/notice/list?page=1");
		} else {
			mv.setViewName("/member/로그인결과");
		}
		return mv;
	}

	@GetMapping(value = "/register")
	public String registerPopup(Device device) {
		if (device.isMobile()) {
			return "/m/member/회원가입창";
		}
		return "/member/회원가입창";
	}

	@PostMapping(value = "/register")
	public ModelAndView 회원가입하다(Member member, Device device, MultipartFile profileFile) {
		ModelAndView mv = new ModelAndView();
		int result = ms.회원가입하다(member);
		if (device.isMobile()) {
			mv.setViewName("/m/board/list");
		} else {
			mv.setViewName("/member/회원가입결과");
		}
		if (result == -1) {
			mv.addObject("msg", "탈퇴한 계정과 같거나 휴면계정입니다.");
		} else if (result == 0) {
			mv.addObject("msg", "이미 존재하는 아이디입니다.");
		} else if (result > 0) {
			mv.addObject("msg", "가입되었습니다.");
		}
		return mv;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String 로그아웃하다(Device device) {
		if (device.isMobile()) {
			return "m/member/로그아웃";
		}
		return "member/로그아웃";
	}

	@GetMapping(value = "/modify")
	public ModelAndView 정보수정창을띄우다(HttpSession session, Device device) {
		ModelAndView mv = new ModelAndView();
		AuthUser user = (AuthUser) session.getAttribute("loginUser");
		Member m = ms.찾는다ById(user.getId());
		mv.addObject("member", m);
		String profile = Base64.getEncoder().encodeToString(m.getProfile());
		mv.addObject("profile", profile);
		if (device.isMobile()) {
			mv.setViewName("/m/member/내정보보기");
		} else {
			mv.setViewName("/member/내정보보기");
		}
		return mv;
	}

	@PostMapping(value = "/modify")
	public ModelAndView 회원정보수정을요청하다(HttpSession session, String newPassword, Device device) {
		ModelAndView mv = new ModelAndView();
		AuthUser user = (AuthUser) session.getAttribute("loginUser");
		Member m = ms.찾는다ById(user.getId());
		m.setPassword(newPassword);
		int result = ms.비밀번호변경하다(m);
		if (result > 0) {
			mv.addObject("msg", "비밀번호를 변경하였습니다.");
			user.setPassword(newPassword);
			session.setAttribute("loginUser", user);
		} else {
			mv.addObject("msg", "수정에 실패하였습니다.");
		}
		if (device.isMobile()) {
			mv.setViewName("/m/home");
		} else {
			mv.setViewName("/home");
		}
		return mv;
	}
	
	@PostMapping(value = "/profile")
	public ModelAndView 프로필사진변경하다(HttpSession session, MultipartFile profileFile) {
		ModelAndView mv = new ModelAndView();
		AuthUser authUser = (AuthUser)session.getAttribute("loginUser");
		Member 회원 = ms.찾는다ById(authUser.getId());
		회원.setProfileFile(profileFile);
		int result = ms.프로필사진변경하다(회원);
		String msg;
		if(result > 0) {
			msg = 회원.getId() + "님의 프로필 사진이 변경되었습니다.";
			mv.addObject("msg", msg);
		} else {
			msg = "프로필 사진 변경에 실패하였습니다. 이용에 불편을 드려 죄송합니다.";
			mv.addObject("msg", msg);
		}
		mv.addObject("url", "/");
		mv.setViewName("redirect");
		return mv;
	}

	@GetMapping(value = "/withdraw")
	public ModelAndView 회원탈퇴하다(String id) {
		ModelAndView mv = new ModelAndView();
		int result = ms.회원탈퇴하다(id);
		String msg;
		if (result > 0) {
			msg = id + "님의 탈퇴가 정상처리 되었습니다.";
			mv.addObject("msg", msg);
		} else {
			msg = "탈퇴에 실패하였습니다. 관리자에게 문의바랍니다.";
			mv.addObject("msg", msg);
		}
		mv.addObject("url", "/member/logout");
		mv.setViewName("redirect");
		return mv;
	}
	
	
}
