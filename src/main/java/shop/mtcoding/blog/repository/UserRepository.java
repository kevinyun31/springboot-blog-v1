package shop.mtcoding.blog.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.JoinDTO;

// BoardController, UserController, UserRepository
// EntityManger, HttpSesseion -- 스프링이 해준거 IOC
// 디비와 연결 메모리에 띄우다
@Repository
public class UserRepository {

    @Autowired
    private EntityManager em;

    @Transactional
    public void save(JoinDTO joinDTO){

    Query query = em.createNativeQuery("insert into user_tb(username, password, email) values(:username, :password, :email)");
    query.setParameter("username", joinDTO.getUsername());
    query.setParameter("password", joinDTO.getPassword());
    query.setParameter("email", joinDTO.getEmail());
    query.executeUpdate();
    }

}
