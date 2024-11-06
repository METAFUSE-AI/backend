package com.mefafuse.backend.Entity;


import jakarta.persistence.*;
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId; // 숫자로 된 고유 ID

    @Column(unique = true, nullable = false)
    private String username; // 사용자가 입력하는 문자열 ID, 변경됨

    @Column(nullable = false)
    private String password; // 패스워드

    @Column(nullable = false)
    private String name; // 사용자 이름

    // 생성자, Getter, Setter 추가
    public Member() {}

    public Member(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getUsername() { // getter 이름도 변경됨
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
