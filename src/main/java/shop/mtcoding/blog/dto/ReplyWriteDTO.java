package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReplyWriteDTO {
   private Integer boardId;  // Integer 해야 null체크도 가능하다
   private String comment;    
}
