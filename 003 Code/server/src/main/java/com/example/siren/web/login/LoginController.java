package com.example.siren.web.login;

import com.example.siren.domain.login.LoginService;
import com.example.siren.domain.member.Member;
import com.example.siren.web.login.LoginForm;
import com.example.siren.web.login.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @ResponseBody
    @PostMapping("/login-json")
    public Member requestBodyJson(@RequestBody LoginForm form, HttpServletRequest request){
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if(loginMember == null){
            return null;
        }

        //로그인 성공처리
        //세션이 있으면 있는세션 반환, 없으면 새로생성
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember);

        return loginMember;
    }
    @ResponseBody
    @PostMapping("/logout-json")
    public String logout(HttpServletRequest request){
       HttpSession session = request.getSession(false);
       if(session != null){
           session.invalidate();
       }
        return "redirect:/";
    }

}
