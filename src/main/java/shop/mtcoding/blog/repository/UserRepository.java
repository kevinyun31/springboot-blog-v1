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

    @Autowired
    private EntityManager em;
    
    public User findByUsernameAndPassword(LoginDTO loginDTO){
     Query query = em.createNativeQuery("select * from user_tb where username=:username and password=:password",User.class);
     query.setParameter("username", loginDTO.getUsername());
     query.setParameter("password", loginDTO.getPassword());
     return (User) query.getSingleResult();
    }
    
    @Transactional  // 인서트,딜리트,업데이트 등을 할때 트레젝션이 롤백과 커밋을 자동으로 해준다.
    public void save(JoinDTO joinDTO) {

        Query query = em.createNativeQuery(
                "insert into user_tb(username, password, email) values(:username, :password, :email)");
        query.setParameter("username", joinDTO.getUsername());
        query.setParameter("password", joinDTO.getPassword());
        query.setParameter("email", joinDTO.getEmail());
        query.executeUpdate();
    }

}
