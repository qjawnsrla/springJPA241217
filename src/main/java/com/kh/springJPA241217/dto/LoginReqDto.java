package com.kh.springJPA241217.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Data
public class LoginReqDto {
    private String email;
    private String pwd;
}

