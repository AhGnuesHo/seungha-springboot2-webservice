package com.seungha.study.web.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
// assertj의 assertThat을 사용
// Junit의  assertThat을쓰면 is()와 같이 coreMatchers라이브러리가 필요함

public class HelloResponseDtoTest {

    @Test
    public void  롬복_테스트(){
        //given
        String name = "test";
        int amount = 1000;

        //wheㄹ
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);

    }
}