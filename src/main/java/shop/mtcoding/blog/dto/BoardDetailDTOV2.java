package shop.mtcoding.blog.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDetailDTOV2 {
    private Integer boardId;
    private String title;
    private String content;
    private Integer userId;
    private boolean pageOwner;

    private List<ReplyDTO> replys = new ArrayList<>();

    public BoardDetailDTOV2(List<BoardDetailDTO> dtos, Integer sessionUserId) {
        this.boardId = dtos.get(0).getBoardId();
        this.title = dtos.get(0).getBoardTitle();
        this.content = dtos.get(0).getBoardContent();
        this.userId = dtos.get(0).getBoardUserId();
        this.pageOwner = userId == sessionUserId;
        this.replys = dtos.stream().map(t -> new ReplyDTO(t, sessionUserId)).collect(Collectors.toList());
    }

    @Getter
    @Setter
    public class ReplyDTO {
        private Integer replyId;
        private String comment;
        private Integer userId;
        private String username;
        private boolean replyOwner;

        public ReplyDTO(BoardDetailDTO dto, Integer sessionUserId) {
            this.replyId = dto.getReplyId();
            this.comment = dto.getReplyComment();
            this.userId = dto.getReplyUserId();
            this.username = dto.getReplyUserUsername();
            this.replyOwner = userId == sessionUserId;
        }
    }

}