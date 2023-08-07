package shop.mtcoding.blog.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.JoinDTO;
import shop.mtcoding.blog.dto.LoginDTO;
import shop.mtcoding.blog.model.User;

// BoardController, UserController, UserRepository
// EntityManger, HttpSesseion -- 스프링이 해준거 IOC
// 디비와 연결하여 메모리에 띄우다
@Repository
public class UserRepository {

    @Autowired // DB와 연계하여 쿼리를 만들고 조작가능 하게한다.
    private EntityManager em;

    // 로그인시 중복아이디 걸러내기
    public User findByUsername(String username) {
        try {
            Query query = em.createNativeQuery("select * from user_tb where username=:username", User.class);
            query.setParameter("username", username);
            return (User) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    // 로그인시 필요한 아이피, 패스워드 불러오기
    public User findByUsernameAndPassword(LoginDTO loginDTO) {
        Query query = em.createNativeQuery("select * from user_tb where username=:username and password=:password",
                User.class);
        query.setParameter("username", loginDTO.getUsername());
        query.setParameter("password", loginDTO.getPassword());
        return (User) query.getSingleResult();
    }

    // 회원가입시 필요한 아이피, 패스워드, 이메일 불러오기
    @Transactional // 인서트,딜리트,업데이트 등을 할때 트레젝션이 롤백과 커밋을 자동으로 해준다.
    public void save(JoinDTO joinDTO) {
        System.out.println("테스트 : " + 1);
        Query query = em.createNativeQuery(
                "insert into user_tb(username, password, email) values(:username, :password, :email)");
        System.out.println("테스트 : " + 2);
        query.setParameter("username", joinDTO.getUsername());
        query.setParameter("password", joinDTO.getPassword());
        query.setParameter("email", joinDTO.getEmail());
        System.out.println("테스트 : " + 3);
        query.executeUpdate(); // 쿼리를 전송 (DBMS)
        System.out.println("테스트 : " + 4);
    }

}
