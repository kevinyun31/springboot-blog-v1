package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.JoinDTO;
import shop.mtcoding.blog.dto.LoginDTO;
import shop.mtcoding.blog.dto.UserUpdateDTO;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.repository.UserRepository;

// ip주소 부여 : 10.5.9.200:8080 -> mtconding.com:8080  port번호 80은 생략 가능
// localhost, 127.0.0.1 <-- 두개는 운영체제가 라우터한테 가기도 전에 루프백 해서 집으로 되돌려 보낸다.
// a태그 = 하이퍼링크(무언가를 연결하여 요청)
// form태그 method=get

@Controller
public class UserController {

    @Autowired // DB에 접근 하는 것
    private UserRepository userRepository;

    @Autowired
    private HttpSession session; // request는 가방, session은 서랍으로 로그인 데이터가 남아있다.

    // localhost:8080/check?username=ssar
    // 회원가입시 중복체크 요청 버튼
    @GetMapping("/check")
    public ResponseEntity<String> check(String username) { // 데이터를 응답 @ResponseBody랑 같은 역할
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return new ResponseEntity<String>("유저네임이 중복 되었습니다", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("유저네임을 사용할 수 있습니다", HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/test/login")
    public String testLogin() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "로그인이 되지 않았습니다";
        } else {
            return "로그인 됨 : " + sessionUser.getUsername();
        }
    }

    // 웹에서의 로그인(login) 요청을 받고 응답(return "redirect:/" 인덱스 홈페이지로)
    @PostMapping("/login")
    public String login(LoginDTO loginDTO) {
        // validation check (유효성 검사)
        if (loginDTO.getUsername() == null || loginDTO.getUsername().isEmpty()) {
            return "redirect:/40x";
        }
        if (loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()) {
            return "redirect:/40x";
        }

        // 핵심 기능
        // System.out.println("테스트 : username : " + loginDTO.getUsername());
        // System.out.println("테스트 : password : " + loginDTO.getPassword());

        try {
            User user = userRepository.findByUsername(loginDTO.getUsername());
            boolean isValid = BCrypt.checkpw(loginDTO.getPassword(), user.getPassword());
            if (isValid) {
                session.setAttribute("sessionUser", user);
                return "redirect:/";
            } else {
                return "redirect:/loginForm";
            }
        } catch (Exception e) {
            return "redirect:/exLogin";
        }

    }

    // 실무
    // 웹에서의 회원가입(join) 요청을 받고 응답(return "redirect:/loginForm"; 로그인 페이지로)
    @PostMapping("/join")
    public String join(JoinDTO joinDTO) {

        // validation check (유효성 검사)
        if (joinDTO.getUsername() == null || joinDTO.getUsername().isEmpty()) {
            return "redirect:/40x";
        }

        if (joinDTO.getPassword() == null || joinDTO.getPassword().isEmpty()) {
            return "redirect:/40x";
        }
        if (joinDTO.getEmail() == null || joinDTO.getEmail().isEmpty()) {
            return "redirect:/40x";
        }

        // DB에 해당 username이 있는지 체크해보기
        User user = userRepository.findByUsername(joinDTO.getUsername());
        if (user != null) {
            return "redirect:/50x";
        }

        // 비밀번호 암호화
        String encdPassword = BCrypt.hashpw(joinDTO.getPassword(), BCrypt.gensalt());
        joinDTO.setPassword(encdPassword); // 암호화된 비밀번호로 변경
        System.out.println("테스트 encdPassword : " + encdPassword);
        System.out.println(encdPassword.length());

        userRepository.save(joinDTO); // 핵심 기능
        return "redirect:/loginForm";
    }

    // 실무
    // 웹에서의 회원가입(join) 요청을 받고 응답(return "redirect:/loginForm"; 로그인 페이지로)
    // @PostMapping("/join")
    // public String join(JoinDTO joinDTO) {

    // // validation check (유효성 검사)
    // if (joinDTO.getUsername() == null || joinDTO.getUsername().isEmpty()) {
    // return "redirect:/40x";
    // }

    // if (joinDTO.getPassword() == null || joinDTO.getPassword().isEmpty()) {
    // return "redirect:/40x";
    // }
    // if (joinDTO.getEmail() == null || joinDTO.getEmail().isEmpty()) {
    // return "redirect:/40x";
    // }

    // // DB에 해당 username이 있는지 체크해보기
    // User user = userRepository.findByUsername(joinDTO.getUsername());
    // if (user != null) {
    // return "redirect:/50x";
    // }
    // userRepository.save(joinDTO); // 핵심 기능
    // return "redirect:/loginForm";
    // }

    // 정상인
    // @PostMapping("/join")
    // public String join(String username, String password, String email) {
    // // username=ssar&password=1234&email=ssar@nate.com
    // System.out.println("username : " + username);
    // System.out.println("password : " + password);
    // System.out.println("email : " + email);
    // return "redirect:/loginForm";
    // }

    // 비정상
    // @PostMapping("/join")
    // public String join(HttpServletRequest request) throws IOException {
    // // username=ssar&password=1234&email=ssar@nate.com 버퍼리더가 바디를 통으로 가져온다.
    // BufferedReader br = request.getReader();

    // // 버퍼가 소비됨, 버퍼는 읽을 때 소비가 된다.
    // String body = br.readLine();

    // // 위에서 버퍼가 소비되어서 비었기에 request.getParameter는 불러 올수가 없다.
    // String username = request.getParameter("username");

    // System.out.println("body : " + body);
    // System.out.println("username : " + username);

    // return "redirect:/loginForm";
    // }

    // 약간 정상 되기는 하는데 좀;;;
    // DS(컨트롤러 메서드 찾기, 바디데이터 파싱)
    // DS가 바디데이터를 파싱안하고 컨트롤러 메서드만 찾은 상황
    // @PostMapping("/join")
    // public String join(HttpServletRequest request) {
    // String username = request.getParameter("username");
    // String password = request.getParameter("password");
    // String email = request.getParameter("email");
    // System.out.println("username " + username);
    // System.out.println("password " + password);
    // System.out.println("email " + email);
    // return "redirect:/loginForm";
    // }

    // ip주소 부여 : 10.5.9.200:8080 -> mtcoding.com:8080
    // localhost, 127.0.0.1
    // a태그 form태그 method=get

    // 회원가입
    @GetMapping("/joinForm") // 내가 내안에 있어서 앞은 생략하고 엔드포인트만 적어도 된다.
    public String joinForm() { // 밖에서 들어올려면 mtconding.com:8080 이렇게 풀주소 적어야한다.
        // templates/
        // .mustache
        // templates//user/joinForm.mustach
        return "user/joinForm"; // ViewResolver
    }

    // 로그인
    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    // 회원수정 화면호출
    @GetMapping("/user/updateForm")
    public String updateForm(HttpServletRequest request) {
        // 1.인증검사
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm"; // 401
        }

        // 왜 findById로 할까?
        // id는 PK이기 때문에 인덱스를 타기 때문에 username도 PK라서 가능함.
        // 유니크는 인덱스를 탄다.

        User user = userRepository.findByUsername(sessionUser.getUsername());
        request.setAttribute("user", user);
      
        return "user/updateForm";
    }

    // 회원수정 요청응답
    @PostMapping("/user/{id}/update")
    public String update(@PathVariable Integer id, UserUpdateDTO userUpdateDTO) {
        // 1.인증검사
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm";
        }

        // 2.권한체크
        User user = userRepository.findById(id);
        if (sessionUser.getId() != user.getId()) {
            return "redirect:/40x";
        }

        // 3.핵심로직 (모델한테 전가한거 받아오고 리턴할 뷰작성)
        // update board_tb set title = :title, content = :content where id = :id
        userRepository.update(id, userUpdateDTO);
        return "redirect:/user/" + id;
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout() {
        session.invalidate(); // 세션 무효화 (내 서랍을 비우는 것)
        return "redirect:/";
    }

}
