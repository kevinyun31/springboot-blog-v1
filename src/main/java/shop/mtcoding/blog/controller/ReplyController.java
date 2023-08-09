package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blog.dto.ReplyWriteDTO;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.Reply;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.repository.ReplyRepository;

@Controller
public class ReplyController {

    @Autowired
    private HttpSession session;

    @Autowired
    private ReplyRepository replyRepository;

     @PostMapping("/reply/{id}/delete")
    public String delete(@PathVariable Integer id, Integer boardId) {
        // 유효성 검사
        if (boardId == null) {
            return "redirect:/40x";
        }

        // 인증체크
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm";
        }

        // 권한체크
        Reply reply = replyRepository.findById(id);
        if (reply.getUser().getId() != sessionUser.getId()) {
            return "redirect:/40x"; // 403
        }

        // 핵심로직
        replyRepository.deleteById(id);

        return "redirect:/board/" + boardId;
    }

    @PostMapping("/reply/save")
    public String save(ReplyWriteDTO replyWriteDTO) {

        // comment 유효성 검사 - 댓글을 썼는지 확인
        if (replyWriteDTO.getBoardId() == null) {
            return "redirect:/40x";
        }
        if (replyWriteDTO.getComment() == null || replyWriteDTO.getComment().isEmpty()) {
            return "redirect:/40x";
        }
     
    
        // 인증 검사 - 정상 ID로 로그인 했는지 확인
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm"; // 401 미승인
        }

        // 댓글 쓰기 - 리파지토리 클래스를 사용하여 쿼리에 댓글을 입력
        replyRepository.save(replyWriteDTO, sessionUser.getId());

        // return 상세보기 화면
        return "redirect:/board/" + replyWriteDTO.getBoardId();

    }

}
