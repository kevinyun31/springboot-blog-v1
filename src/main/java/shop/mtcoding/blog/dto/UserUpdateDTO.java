package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;

/*
 * 글수정 API 
 * 1. URL : http://localhost:8080/user/{id}/update
 * 2. method : POST 
 * 3. 요청 body (리퀘스트바디) : username=값(String)&password=값(String)&email=값(String)
 * 4. MIME타입 : x-www-from-urlencoded
 * 5. 응답 : view(mustache가 해석되어 html로)를 응답함. updateForm페이지
 */

@Getter
@Setter
public class UserUpdateDTO {
    private String username;
    private String password;
    private String email;
}
