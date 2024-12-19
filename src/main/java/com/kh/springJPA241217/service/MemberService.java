package com.kh.springJPA241217.service;

import com.kh.springJPA241217.dto.MemberReqDto;
import com.kh.springJPA241217.dto.MemberResDto;
import com.kh.springJPA241217.entity.Member;
import com.kh.springJPA241217.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor     // 생성자를 통한 의존성 주입을 받기 위해서 생성자를 lombok을 통해 생성
public class MemberService {

    private final MemberRepository memberRepository;

//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    // 1. 이메일로 회원 조회
    public Optional<Member> getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    // 2. 비밀번호로 회원 조회
    public Optional<Member> getMemberByPwd(String pwd) {
        return memberRepository.findByPwd(pwd);
    }

    // 3. 이메일과 비밀번호로 회원 조회 (로그인 시 사용)
    public Optional<Member> getMemberByEmailAndPwd(String email, String pwd) {
        return memberRepository.findByEmailAndPwd(email, pwd);
    }

    // 4. 이메일 존재 여부 확인 (중복 체크)
    public boolean isEmailExist(String email) {
        return memberRepository.existsByEmail(email);
    }


    // 답안
    // 회원 전체 조회
    public List<MemberResDto> getMemberList() {
        // DB 로부터 모든 회원정보를 Member Entity 객체로 읽어 옴
        List<Member> members = memberRepository.findAll();
        // FrontEnd에 정보를 전달하기 위해서는 DTO List를 생성
        List<MemberResDto> memberResDtoList = new ArrayList<>();
        for(Member member : members) {  // Member Entity로 구성된 리스트를 순회
            memberResDtoList.add(convertEntityToDto(member));
        }
        return memberResDtoList;
    }
    // 회원 상세 조회
    public MemberResDto getMemberDetail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("해당 회원이 존재하지 않습니다."));
        return convertEntityToDto(member);
    }
    // 회원 정보 수정
    public boolean modifyMember(MemberReqDto memberReqDto) {
        try{
            Member member = memberRepository.findByEmail(memberReqDto.getEmail())
                    .orElseThrow(()->new RuntimeException("해당 회원이 존재하지 않습니다."));
            member.setName(memberReqDto.getName());
            member.setImgPath(memberReqDto.getImgPath());
            memberRepository.save(member);
            return true;
        } catch (Exception e) {
            log.error("회원 정보 수정 : {}", e.getMessage());
            return false;
        }
    }
    // 회원 정보 삭제
    public boolean deleteMember(String email) {
        try{
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(()->new RuntimeException("해당 회원이 존재하지 않습니다."));
            memberRepository.delete(member);
            return true;
        } catch (Exception e) {
            log.error("회원 삭제에 실패했습니다.: {}", e.getMessage());
        }
        return false;
    }

    // Member Entity => MemberResDto 변환
    private MemberResDto convertEntityToDto(Member member) {
        MemberResDto memberResDto = new MemberResDto();
        memberResDto.setEmail(member.getEmail());
        memberResDto.setName(member.getName());
        memberResDto.setRegDate(member.getRegDate());
        memberResDto.setImagePath(member.getImgPath());
        return memberResDto;
    }
}
