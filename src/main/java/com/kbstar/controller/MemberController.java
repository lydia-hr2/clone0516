package com.kbstar.controller;

import com.kbstar.dto.Member;
import com.kbstar.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Valid
    @RequestMapping("/signinimpl")
    public String signinimpl(@Valid Member member, Model model, HttpSession session) throws Exception {
        try {
            member.setPassword(encoder.encode(member.getPassword()));
            memberService.register(member);
            session.setMaxInactiveInterval(100000);
            session.setAttribute("loginmember",member);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("가입 오류");
        }
        model.addAttribute("rmember", member);
        return "redirect:/";
    }

    @GetMapping("/login?logout")
    public String logoutSenior(HttpSession session){
        session.invalidate();
        if (session != null) {

            log.info("===================로그아웃 세션 제거 완료 ===================");
        }
        return "redirect:/login";
    }

    @RequestMapping("/loginimpl")
    public String loginimpl(Model model, String memberId, String password, HttpSession session) {
        String nextPage = "loginfail";
        Member member = null;
        try {
            member = memberService.get(memberId);
            log.info("==============user==============" + member.toString());
            if (member != null && encoder.matches(password, member.getPassword())) {
                nextPage = "loginok";
                session.setMaxInactiveInterval(100000);// 한 session의 제한시간
                session.setAttribute("loginmember", member); //session에 logincust라는 이름으로 cust를 넣어줌 --> login을 메모리에 제한시간만큼 유지
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("시스템 장애 잠시 후 다시 로그인 하세요.");
        }
        System.out.println("로그인 세션 확인 = " + session.getAttribute("loginmember"));
        model.addAttribute("center", nextPage);
        return "index";
    }

}
