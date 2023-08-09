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
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.BoardDetailDTO;
import shop.mtcoding.blog.dto.BoardDetailDTOV2;
import shop.mtcoding.blog.dto.UpdateDTO;
import shop.mtcoding.blog.dto.WriteDTO;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.Reply;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.repository.BoardRepository;
import shop.mtcoding.blog.repository.ReplyRepository;

@Controller
public class BoardController {

    @Autowired
    private HttpSession session;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @ResponseBody
    @GetMapping("/test/reply")
    public List<Reply> test2(){
    List<Reply> replys = replyRepository.findByBoardId(1);
     return replys;
    }

     // 오브젝트를 리턴하면 json 데이터로 리턴해준다.
    @ResponseBody
    @GetMapping("/test/board/1")
    public Board test() {
        Board board = boardRepository.findById(1);
        return board;
    }

    // 수정요청 응답
    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id, UpdateDTO updateDTO) { // 1.PathVarible 값 받기

        // 1.인증검사
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm";
        }

        // 2.권한체크
        Board board = boardRepository.findById(id);
        if (board.getUser().getId() != sessionUser.getId()) {
            return "redirect:/40x";
        }

        // 3.핵심로직 (모델한테 전가한거 받아오고 리턴할 뷰작성)
        // update board_tb set title = :title, content = :content where id = :id
        boardRepository.update(updateDTO, id);

        return "redirect:/board/" + id;
    }

    // 수정화면 호출
    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable Integer id, HttpServletRequest request) {
        // 1. 인증 검사 (아이디에 대한 세션값이 필요한가 ? post맨으로 우회접근을 하면 필요하지 않을까 ? )
        User sessionUser = (User) session.getAttribute("sessionUser"); // 권한체크를 위한 세션 접근
        if (sessionUser == null) {
            return "redirect:/loginForm"; // 401
        }

        // 2. 권한 체크 (로그인한 아이디의 세션값과 게시글의 적힌 세션의 값이 동일한지 파악해야 하는가 ? )
        Board board = boardRepository.findById(id);
        if (board.getUser().getId() != sessionUser.getId()) {
            return "redirect:/40x";
        }

        // 3. 핵심 로직
        request.setAttribute("board", board);
        return "board/updateForm";
    }

       // 글 삭제버튼 관련 작동 컨트롤
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id) { // 1.PathVarible 값 받기

        // 2.인증검사 (로그인 페이지 보내기)
        // session에 접근해서 sessionUser 키값을 가져오세요
        // null 이면, 로그인페이지로 보내고
        // null 아니면, 3번을 실행하세요
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm"; // 401 미승인
        }
        // 3.권한검사
        Board board = boardRepository.findById(id);
        if (board.getUser().getId() != sessionUser.getId()) {
            return "redirect:/40x"; // 403 권한없음
        }
        // 4.핵심로직(모델에 접근해서 삭제)
        // boardRepository.deleteById(id); 호출하세요 -> 리턴을 받지 마세요
        // delete from board_tb where id = :id
        boardRepository.deleteById(id);;
        return "redirect:/";
    }

      
    // localhost:8080 <= 널값 (왜 널값인데 ? Integer 클래스는 null 값이 허용되니깐)
    // 그래서 RequestParam(defaultValue = "0") 이거를 넣어서 기본값으로 만들어서
    // 널값이 안들어 가게 만든다.
    // 홈화면 보기
    @GetMapping({ "/", "/board" })
    public String index(@RequestParam(defaultValue = "0") Integer page,
            HttpServletRequest request) {
        // 1. 유효성 검사 X
        // 2. 인증검사 X
        // 3. 조회 쿼리만 있으면 됨.

        List<Board> boardList = boardRepository.findAll(page); // page = 1 일때
        int totalCount = boardRepository.count(); // totalCount = 5

        // System.out.println("테스트 : totalCount : " + totalCount);
        int totalPage = totalCount / 3; // totalPage = 1
        if (totalCount % 3 > 0) {
            totalPage = totalPage + 1; // totalPage = 2
        }
        boolean last = totalPage - 1 == page;

        // System.out.println("테스트 : " + boardList.size());
        // System.out.println("테스트 : " + boardList.get(0).getTitle());

        request.setAttribute("boardList", boardList);
        request.setAttribute("prevPage", page - 1);
        request.setAttribute("nextPage", page + 1);
        request.setAttribute("first", page == 0 ? true : false);
        request.setAttribute("last", last);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("totalCount", totalCount);

        return "index";
    }

    // 글쓰는 화면에서 글을 입력하기
    @PostMapping("/board/save")
    public String save(WriteDTO writeDTO) {
        // validation check (유효성 검사) null값인지 확인
        if (writeDTO.getTitle() == null || writeDTO.getTitle().isEmpty()) {
            return "redirect:/40x";
        }

        if (writeDTO.getContent() == null || writeDTO.getContent().isEmpty()) {
            return "redirect:/40x";
        }
        // 인증 검사 - 글쓴 유저가 로그인한 유저인지 아님 로그인 안했는지 걸러냄
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm";
        }

        // 글쓰기
        boardRepository.save(writeDTO, sessionUser.getId());
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
    @ResponseBody
    @GetMapping("/v1/board/{id}")
    public List<BoardDetailDTO> detailV1(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser"); // 세션접근
        List<BoardDetailDTO> dtos = null;
        if (sessionUser == null) {
            dtos = boardRepository.findByIdJoinReply(id, null);
        } else {
            dtos = boardRepository.findByIdJoinReply(id, sessionUser.getId());
        }
        return dtos;
    }

    @ResponseBody
    @GetMapping("/v2/board/{id}")
    public BoardDetailDTOV2 detailV2(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser"); // 세션접근
        List<BoardDetailDTO> dtos = null;
        BoardDetailDTOV2 dtoV2 = null;
        if (sessionUser == null) {
            dtos = boardRepository.findByIdJoinReply(id, null);
            dtoV2 = new BoardDetailDTOV2(dtos, null);
        } else {
            dtos = boardRepository.findByIdJoinReply(id, sessionUser.getId());
            dtoV2 = new BoardDetailDTOV2(dtos, sessionUser.getId());
        }

        return dtoV2;
    }
    // localhost:8080/board/1
    // localhost:8080/board/50
    // 상세보기 화면에서 아이디를 찾아서 응답
    @GetMapping("/board/{id}") 
    public String detail(@PathVariable Integer id, HttpServletRequest request) { // C(Controller) = 웹에서 받은 요청을 응답한다.
        User sessionUser = (User) session.getAttribute("sessionUser"); // 세션접근 권한을 체크하기 위해
        List<BoardDetailDTO> dtos = null;

        if (sessionUser == null) {
            dtos = boardRepository.findByIdJoinReply(id, null);
        }else{
            dtos = boardRepository.findByIdJoinReply(id, sessionUser.getId());
        }
            // Board board = boardRepository.findById(id); // M(Model) = 모델역할을 하는 BoardRepository 클래스를 불러 온다

        boolean pageOwner = false;
        if (sessionUser != null) {
            // System.out.println("테스트 세션 ID :" + sessionUser.getId());
            // System.out.println("테스트 세션  board.getUser().getId() :" + board.getUser().getId());
            pageOwner = sessionUser.getId() == dtos.get(0).getBoardUserId();
            // pageOwner = sessionUser.getId() == board.getUser().getId();
            // System.out.println("테스트 : pageOwner : " + pageOwner);
        }
 
        request.setAttribute("dtos", dtos);
        request.setAttribute("pageOwner", pageOwner);
        return "board/detail"; // V(View) = 웹에 뷰화면을 뿌릴 detail.mustache 클래스를 불러온다.
    }

}
