package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardDetailDTO {
    private Integer boardId;
    private String boardContent;
    private String boardTitle;
    private Integer boardUserId;
    private Integer replyId;
    private String replyComment;
    private Integer replyUserId;
    private String replyUserUsername;

}
