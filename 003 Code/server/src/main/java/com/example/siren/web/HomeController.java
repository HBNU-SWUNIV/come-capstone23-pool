package com.example.siren.web;

import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.repository.MemberRepository;
import com.example.siren.web.login.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;
    @GetMapping("/")
    public String homeLogin(@SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) Member loginMember, Model model){
        //세션에 회원 데이터가 없으면 home
        if(loginMember == null){
            //db에 해당 쿠키id가 없을때
            return "home";
        }
        //세션이 유지되면 성공
        model.addAttribute("member",loginMember);
        return "loginHome";

    }
}
