package com.seungha.study.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @After // 단위테스트가 끝날때마다 수행되는 메소드, 데이터 간 데이터침범을 막기위해사용
    public void cleanUp(){
        postRepository.deleteAll();
    }

    @Test
    public void 게시글_저장불러오기(){
        //given
        String title = "테스트 제목";
        String content = "테스트 내용";

        postRepository.save(Posts.builder() //post에 insert,update쿼리를 실행 (id가 있으면 update, 없으면 insert)
                                .title(title)
                                .content(content)
                                .author("me")
                                .build());
        // when
        List<Posts> postsList = postRepository.findAll(); // 테이블의 모든 데이터 조회

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);

    }
}