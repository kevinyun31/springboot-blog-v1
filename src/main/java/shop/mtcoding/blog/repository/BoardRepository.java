package shop.mtcoding.blog.repository;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.WriteDTO;
import shop.mtcoding.blog.model.Board;

@Repository
public class BoardRepository {

	@Autowired // EntityManager 객체를 자동으로 주입(inject), DB와 상호작용
	private EntityManager em;

	// select id, title from board_tb
	// resultClass 안붙이고 직접 파싱하려면!!
	// Object[]로 리턴됨.
	// Object[0] = 1
	// Object[1] = 제목1
	public int count() {
		// Entity(Board, User) 타입이 아니어도, 기본 자료형은 안된다
		Query query = em.createNativeQuery("select count(*) from board_tb");
        // 원래는 Object 배열로 리턴 받는다, Object 배열은 칼럼의 연속이다.
		// 그룹함수를 써서, 하나의 칼럼을 조회하면 Object로 리턴된다.
		BigInteger count = (BigInteger) query.getSingleResult();
		return count.intValue();
	}

	// localhost:8080?page=0
	public List<Board> findAll(int page) {
		// 페이징커리 변하지 않는
		final int SIZE = 3;
		Query query = em.createNativeQuery("select * from board_tb order by id desc limit :page, :size", Board.class);
		query.setParameter("page", page * SIZE);
		query.setParameter("size", SIZE);
		return query.getResultList();

	}

	@Transactional // 인서트,딜리트,업데이트 등을 할때 트레젝션이 롤백과 커밋을 자동으로 해준다.
	public void save(WriteDTO writeDTO, Integer userId) {

		Query query = em.createNativeQuery(
				"insert into board_tb(title, content, user_id, created_at) values(:title, :content, :userId, :now())");

		query.setParameter("title", writeDTO.getTitle());
		query.setParameter("content", writeDTO.getContent());
		query.setParameter("userId", userId);
		query.executeUpdate();
	}

	public Board findById(Integer id) {
		Query query = em.createNativeQuery("select * from board_tb where id = :id", Board.class);
		query.setParameter("id", id);
		Board board = (Board) query.getSingleResult();
		return board;
	}

}
