package shop.mtcoding.blog.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.mtcoding.blog.dto.WriteDTO;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.repository.BoardRepository;

@Controller
public class BoardController {

    @Autowired
    private HttpSession session;

    @Autowired
    private BoardRepository boardRepository;

    // 홈화면 보기
    @GetMapping({ "/", "/board" })
    public String index(@RequestParam(defaultValue = "0") Integer page,
     HttpServletRequest request) {
        // 1. 유효성 검사 X
        // 2. 인증검사 X
        // 3. 조회 쿼리만 있으면 됨.

        List<Board> boardList = boardRepository.findAll(page);
        System.out.println("테스트 : " + boardList.size());
        System.out.println("테스트 : " + boardList.get(0).getTitle());
        
        request.setAttribute("boardList", boardList);
        request.setAttribute("prevPage", page-1);
        request.setAttribute("nextPage", page+1);
        request.setAttribute("first", page == 0 ? true : false);
        request.setAttribute("last", false);

        return "index";
    }

    // 글을 입력하기
    @PostMapping("/board/save")
    public String save(WriteDTO writeDTO) {
        // validation check (유효성 검사)
        if (writeDTO.getTitle() == null || writeDTO.getTitle().isEmpty()) {
            return "redirect:/40x";
        }

        if (writeDTO.getContent() == null || writeDTO.getContent().isEmpty()) {
            return "redirect:/40x";
        }
        // 인증 체크
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm";
        }
        return "redirect:/";
    }

    // 글쓰기 화면호출
    @GetMapping({ "/board/saveForm" })
    public String saveForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm";
        }
        return "board/saveForm";
    }

    // localhost:8080/board/1
    // localhost:8080/board/50
    // 상세보기
    @GetMapping({ "/board/{id}" })
    public String detail(@PathVariable Integer id) {
        return "board/detail";
    }

}
