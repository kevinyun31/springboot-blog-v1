package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;

/*
 * 로그인 API 
 * 1. URL : http://localhost:8080/login
 * 2. method : POST (로그인은 select 이지만, post로 한다) 정보보호를 위해.
 * 3. 요청 body (리퀘스트바디) : username=값(String)&password=값(String)
 * 4. MIME타입 : x-www-from-urlencoded
 * 5. 응답 : view(mustache가 해석되어 html로)를 응답함. index 페이지
 */

@Setter @Getter
public class LoginDTO {
    private String username;
    private String password;
   
}
