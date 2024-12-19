package com.kh.springJPA241217.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity     // 해당 클래스가 Entity 임을 나타냄 , // JPA 어노테이션: 이 클래스가 데이터베이스 테이블과 매핑됨을 나타냄
@Table(name="member")   // 테이블 이름 지정, 테이블 이름은 소문자, 카멜표기법은 스네이크표기법으로 바뀜
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString   // 오버라이딩
public class Member {
    @jakarta.persistence.Id
    @Id         // 해당 필드를 기본키로 지정
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)     // 기본키 생성 전략, JPA가 자동으로 생성 전략을 정함
    private Long id;    // Primary Key
    @Column(nullable=false, length = 50) // null 값이 올 수 없다는 제약조건, 길이
    private String email;
    @Column(nullable=false, length = 50)
    private String pwd;
    @Column(length = 50)
    private String name;
    @Column(name="reg_date")
    private LocalDateTime regDate;
    @Column(name = "image_path")
    private String imgPath;
    @PrePersist     // JPA의 콜백 메서드로 엔티티가 저장되기 전에 실행, DB 데이터가 삽입되기전에 자동 설정
    protected void onCreate() {
        this.regDate = LocalDateTime.now();
    }
}
