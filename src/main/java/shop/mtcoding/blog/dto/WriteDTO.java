package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;


/*
 * 글쓰기 API 
 * 1. URL : http://localhost:8080/board/seve
 * 2. method : POST 
 * 3. 요청 body (리퀘스트바디) : title=값(String)&content=값(String)
 * 4. MIME타입 : x-www-from-urlencoded
 * 5. 응답 : view(mustache가 해석되어 html로)를 응답함. index 페이지
 */

@Getter @Setter
public class WriteDTO {
    private String title;
    private String content;
    public Object getCreatedAt() {
        return null;
    }

}
