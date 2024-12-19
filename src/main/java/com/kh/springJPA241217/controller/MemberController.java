package com.kh.springJPA241217.controller;

import com.kh.springJPA241217.entity.Member;
import com.kh.springJPA241217.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 1. 이메일로 회원 조회 API
    @GetMapping("/email")
    public Optional<Member> getMemberByEmail(@RequestParam String email) {
        return memberService.getMemberByEmail(email);
    }

    // 2. 비밀번호로 회원 조회 API
    @GetMapping("/pwd")
    public Optional<Member> getMemberByPwd(@RequestParam String pwd) {
        return memberService.getMemberByPwd(pwd);
    }

    // 3. 이메일과 비밀번호로 회원 조회 API (로그인 기능)
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String pwd) {
        Optional<Member> member = memberService.getMemberByEmailAndPwd(email, pwd);
        if (member.isPresent()) {
            return "로그인 성공! 환영합니다: " + member.get().getEmail();
        }
        return "로그인 실패! 이메일 또는 비밀번호가 올바르지 않습니다.";
    }

    // 4. 이메일 존재 여부 확인 API (중복 체크)
    @GetMapping("/exists")
    public String checkEmailExists(@RequestParam String email) {
        boolean exists = memberService.isEmailExist(email);
        if (exists) {
            return "이메일이 이미 존재합니다.";
        }
        return "사용 가능한 이메일입니다.";
    }
}
