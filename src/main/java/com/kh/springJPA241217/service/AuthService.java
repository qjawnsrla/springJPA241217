package com.kh.springJPA241217.service;

import com.kh.springJPA241217.dto.LoginReqDto;
import com.kh.springJPA241217.dto.MemberReqDto;
import com.kh.springJPA241217.entity.Member;
import com.kh.springJPA241217.repository.MemberRepository;
//import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j      // 로그 정보를 출력하기 위함
@Service    // 스프링 컨테이너에 빈(객체) 등록
@RequiredArgsConstructor    // 생성자 생성
@Transactional              // 여러개의 작업을 하나의 논리적인 단위로 묶어줌

public class AuthService {
    // 생성자를 통한 의존성 주입, 생성자를 통해서 의존성 주입을 받는 경우 Autowired 생략
    private final MemberRepository memberRepository;

    // 회원 가입 여부
    public boolean isMember(String email) {
        return memberRepository.existsByEmail(email);
    }
    // 회원 가입
    public boolean signUp(MemberReqDto memberReqDto) {
        try{
            Optional<Member> existingMember = memberRepository.findByEmail(memberReqDto.getEmail());
            if (existingMember.isPresent()) {
                // 이미 존재하는 이메일 처리
                return false; // 또는 예외 처리
            }
            Member member = convertDtoToEntity(memberReqDto);
            memberRepository.save(member);  // 회원 가입, save() insert, update 역할
            return true;
        } catch (Exception e) {
            log.error("회원 가입 실패 : {}", e.getMessage());
            return false;
        }
    }

    // 로그인
    public boolean login(LoginReqDto loginReqDto) {
        try {
            Optional<Member> member = memberRepository
                    .findByEmailAndPwd(loginReqDto.getEmail(), loginReqDto.getPwd());
            return member.isPresent();      // 해당 객체가 있음을 의미, 존재하면 True 없으면 False 반환
        } catch (Exception e) {
            log.error("로그인 실패 : {}", e.getMessage());
            return false;
        }
    }

    // 회원가입 DTO -> Entity로 변환
    private Member convertDtoToEntity(MemberReqDto memberReqDto) {
        Member member = new Member();
        member.setEmail(memberReqDto.getEmail());
        member.setName(memberReqDto.getName());
        member.setPwd(memberReqDto.getPwd());
        return member;
    }

    // 회원 전체 조회
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // 특정 이메일로 회원 조회
    public Member getMemberByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new IllegalArgumentException("해당 이메일을 가진 회원이 존재하지 않습니다.");
        }
    }

}
