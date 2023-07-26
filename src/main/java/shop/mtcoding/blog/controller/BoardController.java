package shop.mtcoding.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class BoardController {

// 홈화면
@GetMapping({"/","/board"})
public String index(){
    return "index";
}
// 글쓰기
@GetMapping({"/board/saveForm"})
public String saveForm(){
    return "board/saveForm";
}
// 상세보기
    @GetMapping({"/board/1"})
public String detail(){
    return "board/detail";
}

}

