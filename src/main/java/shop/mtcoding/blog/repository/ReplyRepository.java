package shop.mtcoding.blog.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.ReplyWriteDTO;
import shop.mtcoding.blog.model.Reply;

// UserController, BoardController, ReplyController, ErrorController -- 내가 띄운 것
// UserRepository, BoardRepositroy, ReplyRepository                  -- 내가 띄운 것
// EntityManger, HttpSesseion                                  -- 스프링이 해준거 IOC
// 디비와 연결하여 메모리에 띄우다
@Repository
public class ReplyRepository {

	@Autowired
	private EntityManager em;

	// 댓글을 아이디별로 구분
	public Reply findById(int id) {
        Query query = em.createNativeQuery("select * from reply_tb where id = :id", Reply.class);
        query.setParameter("id", id);
        return (Reply) query.getSingleResult();
    }

    // 댓글의 목록을 아이디로 관리하는 
	public List<Reply> findByBoardId(Integer boardId) {
		Query query = em.createNativeQuery("select * from reply_tb where board_id = :boardId", Reply.class);
		query.setParameter("boardId", boardId);
		return query.getResultList();
	}

	// userId로 로그인을 해야지 댓글을 쓸수 있고 = 댓글을 쓴 ID
	// boardId로 몇번째 글에 댓글을 달 수 있다 = 댓글의 번호 ID
	// 댓글을 쓰는 쿼리이다. 댓글의 목록==ID와 댓글을 쓴유저==ID로 구분된다.
	@Transactional
	public void save(ReplyWriteDTO replyWriteDTO, Integer userId) {

		Query query = em.createNativeQuery(
				"insert into reply_tb(board_id, comment, user_id) values(:boardId, :comment, :userId)");

		query.setParameter("boardId", replyWriteDTO.getBoardId()); // 게시글의 ID
		query.setParameter("comment", replyWriteDTO.getComment());
		query.setParameter("userId", userId); // 댓글 쓰는 사람의 ID
		query.executeUpdate(); // 쿼리를 전송
	}

	// 댓글 삭제 기능
	@Transactional
    public void deleteById(Integer id) {
        Query query = em
                .createNativeQuery(
                        "delete from reply_tb where id = :id");

        query.setParameter("id", id);
        query.executeUpdate();
    }

}
