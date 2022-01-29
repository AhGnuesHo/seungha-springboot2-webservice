package com.seungha.study.web;

import com.seungha.study.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
        // RequestParam 외부에서 넘긴 파라메터를 가져오는 어노테이션.
        return new HelloResponseDto(name, amount);
    }
}
