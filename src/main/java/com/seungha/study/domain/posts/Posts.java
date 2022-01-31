package com.seungha.study.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // Getter 메소드 생성
@NoArgsConstructor // 기본생성자 추가
@Entity // 테이블과 링크 될 클래스 임을 나타냄
public class Posts extends BaseTimeEntity{
    @Id // 해당 테이블의  PK 입니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // PK생성규칙입니다. GenerationType.IDENTITY옵션을 추가해야 자동 증가를 사용할 수 있습니다.
    private Long id;

    @Column(length = 500, nullable = false) // 테이블의 컬럼을 나타냅니다.
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder // 클래스의 빌더패턴 클래스 생성 , 생성자 상단에 선언하면 생성자에 포함된 필드 (title, content, author)만 빌더에포함
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update (String title, String content){
        this.title = title;
        this.content = content;
    }
}
