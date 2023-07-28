package shop.mtcoding.blog.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jni.Buffer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {

 // ip주소 부여 : 10.5.9.200:8080 -> mtconding.com:8080  port번호 80은 생략 가능
 // localhost, 127.0.0.1 <-- 두개는 운영체제가 라우터한테 가기도 전에 루프백 해서 집으로 되돌려 보낸다.
 // a태그 = 하이퍼링크(무언가를 연결하여 요청)
 // form태그 method=get


    // 정상인
    @PostMapping("/join")
    public String join(String username, String password, String email) {
        // username=ssar&password=1234&email=ssar@nate.com
        System.out.println("username : " + username);
        System.out.println("password : " + password);
        System.out.println("email : " + email);
        return "redirect:/loginForm";
    }

 // 비정상
// @PostMapping("/join")
//     public String join(HttpServletRequest request) throws IOException {
//         // username=ssar&password=1234&email=ssar@nate.com 버퍼리더가 바디를 통으로 가져온다.
//         BufferedReader br = request.getReader();

//         // 버퍼가 소비됨, 버퍼는 읽을 때 소비가 된다.
//         String body = br.readLine();

//         // 위에서 버퍼가 소비되어서 비었기에  request.getParameter는 불러 올수가 없다.
//         String username = request.getParameter("username");
        
//         System.out.println("body : " + body);
//         System.out.println("username : " + username);   
          
//         return "redirect:/loginForm";
//     }

 // 약간 정상 되기는 하는데 좀;;; 
 // DS(컨트롤러 메서드 찾기, 바디데이터 파싱)
 // DS가 바디데이터를 파싱안하고 컨트롤러 메서드만 찾은 상황
//  @PostMapping("/join")
//     public String join(HttpServletRequest request) {
//         String username = request.getParameter("username");     
//         String password = request.getParameter("password");    
//         String email = request.getParameter("email");   
//         System.out.println("username " + username);   
//         System.out.println("password " + password);   
//         System.out.println("email " + email);   
//         return "redirect:/loginForm";
//     }

 // ip주소 부여 : 10.5.9.200:8080 -> mtcoding.com:8080
    // localhost, 127.0.0.1
    // a태그 form태그 method=get
   
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
