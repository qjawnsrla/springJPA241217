package com.kh.springJPA241217.dto;

import lombok.*;

// DTO : 다른 레이어간의 데이터를 교환할 때 사용, 주로 FrontEnd와 BackEnd 사이에 데이터를 주고 받는 용도
// 회원가입 하는 용도
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberReqDto {
    private String email;
    private String pwd;
    private String name;
    private String imgPath;
}
