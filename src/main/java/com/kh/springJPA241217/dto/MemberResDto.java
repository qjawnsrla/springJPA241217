package com.kh.springJPA241217.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberResDto {
    private String email;
    private String name;
    private String imagePath;
    private LocalDateTime regDate;

}
