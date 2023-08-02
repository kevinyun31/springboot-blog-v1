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

        List<Board> boardList = boardRepository.findAll(page); // page = 1 일때
        int totalCount = boardRepository.count(); // totalCount = 5

        int totalPage = totalCount / 3; // totalPage = 1
        if (totalCount % 3 > 0) {
            totalPage = totalPage + 1; // totalPage = 2
        }
        boolean last = totalPage - 1 == page;

        System.out.println("테스트 : " + boardList.size());
        System.out.println("테스트 : " + boardList.get(0).getTitle());

        request.setAttribute("boardList", boardList);
        request.setAttribute("prevPage", page - 1);
        request.setAttribute("nextPage", page + 1);
        request.setAttribute("first", page == 0 ? true : false);
        request.setAttribute("last", last);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("totalCount", totalCount);

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
    // 상세보기 - 안에 바디로직은 원래 따로 서비스 클래스로 작성해야 하는 것이다.
    @GetMapping({ "/board/{id}" })
    public String detail(@PathVariable Integer id, HttpServletRequest request) { // C
        User sessionUser = (User) session.getAttribute("sessionUser"); // 세션접근 권한을 체크하기 위해
        Board board = boardRepository.findById(id); // M

        boolean pageOwner = false;
        if (sessionUser != null) {
            System.out.println("테스트 세션 ID :" + sessionUser.getId());
            System.out.println("테스트 세션  board.getUser().getId() :" + board.getUser().getId());
            pageOwner = sessionUser.getId() == board.getUser().getId();
        }

        request.setAttribute("board", board);
        request.setAttribute("pageOwner", false);
        return "board/detail"; // V
    }

}
