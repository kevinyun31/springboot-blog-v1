package shop.mtcoding.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

 // ip주소 부여 : 10.5.9.200:8080 -> mtconding.com:8080  port번호 80은 생략 가능
 // localhost, 127.0.0.1 <-- 두개는 운영체제가 라우터한테 가기도 전에 루프백 해서 집으로 되돌려 보낸다.
 // a태그 = 하이퍼링크(무언가를 연결하여 요청)
 // form태그 method=get

    // 회원가입  
    @GetMapping("/joinForm")    // 내가 내안에 있어서 앞은 생략하고 엔드포인트만 적어도 된다.
    public String joinForm(){   // 밖에서 들어올려면  mtconding.com:8080 이렇게 풀주소 적어야한다.
        // templates/
        // .mustache
        // templates//user/joinForm.mustach
        return "user/joinForm";  // ViewResolver
    }
    // 로그인
      @GetMapping("/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }
    // 회원수정
        @GetMapping("/user/updateForm")
    public String updateForm(){
        return "user/updateForm";
    }
    // 로그아웃
      @GetMapping("/logout")
    public String logout(){
 return "user/logout";
        // return "redirect:/";
    }
}
