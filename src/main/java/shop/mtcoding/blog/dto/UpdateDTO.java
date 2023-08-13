package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;


/*
 * 글수정 API 
 * 1. URL : http://localhost:8080/board/{id}/update
 * 2. method : POST 
 * 3. 요청 body (리퀘스트바디) : title=값(String)&content=값(String)
 * 4. MIME타입 : x-www-from-urlencoded
 * 5. 응답 : view(mustache가 해석되어 html로)를 응답함. detail 페이지
 */

@Getter @Setter
public class UpdateDTO {
    private int id;
    private String title;
    private String content;

} 
