package com.seungha.study.web;

import com.seungha.study.domain.posts.Posts;
import com.seungha.study.domain.posts.PostsRepository;
import com.seungha.study.web.dto.PostsSaveRequestDto;
import com.seungha.study.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 랜덤포트실행
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate; // HTTP를 사용하여 통신하는 범용 라이브러리

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void Posts가_등록된다() throws Exception {
        //given
        String title = "테스트 제목입니다.";
        String content = "테스트 내용입니다.";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .content(content)
                .title(title)
                .author("작성자")
                .build();

        String url = "http://localhost:"+ port + "/api/v1/posts";

        //when HTTP 응답데이터, RestTemplate을 통해 통신 후 받은 데이터
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void Posts가_수정된다() throws Exception {
        //given 수정할 게시글 작성
        Posts savePosts = postsRepository.save(Posts.builder()
                .title("수정테스트 제목")
                .content("수정테스트 내용")
                .author("수정테스트 작성자")
                .build());

        Long updateId = savePosts.getId();// 작성한 게시글에서 아이디 가져오기
        String expectTitle = "새 제목"; // 수정할 제목
        String expectContent = "새 내용"; // 수정할 내용

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectTitle)
                .content(expectContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectContent);
    }

}