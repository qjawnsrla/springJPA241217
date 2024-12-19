package com.kh.springJPA241217.controller;

import com.kh.springJPA241217.dto.LoginReqDto;
import com.kh.springJPA241217.dto.MemberReqDto;
import com.kh.springJPA241217.dto.MemberResDto;
import com.kh.springJPA241217.entity.Member;
import com.kh.springJPA241217.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

// 1. POST MAN 으로 회원 존재여부 확인, 회원가입, 로그인
// 2. 회원 전체 조회 및 회원 이메일 조회 만들기 (POST MAN)
// 3. 스웨거 등록 후 회원 존재여부 확인, 회원가입, 로그인, 회원 전체조회, 개별 회원 조회, 회원 삭제
// 4. MemberController : 회원 전체 조회, 개별 회원 조회, 회원 정보 수정, 회원 삭제

public class AuthController {
    private final AuthService authService;  // 의존성 주입

    // 회원 가입 여부 확인
    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> isMember(@PathVariable("email") String email) {
        boolean isTrue = authService.isMember(email);
        return ResponseEntity.ok(isTrue);
    }
    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<Boolean> signUp(@RequestBody MemberReqDto memberReqDto) {
        boolean isSuccess = authService.signUp(memberReqDto);
        return ResponseEntity.ok(isSuccess);
    }
    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody LoginReqDto loginReqDto) {
        boolean isSuccess = authService.login(loginReqDto);
        return ResponseEntity.ok(isSuccess);
    }

    // 회원 전체 조회
    @GetMapping("/members")
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = authService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    // 회원 이메일로 조회
    @GetMapping("/members/{email}")
    public ResponseEntity<Member> getMemberByEmail(@PathVariable("email") String email) {
        Member member = authService.getMemberByEmail(email);
        return ResponseEntity.ok(member);
    }



}
